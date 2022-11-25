/*
 * Copyright 2022 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.joe.gui.panel.file.tabbed.tab.classes;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.compiler.Compiler;
import cn.enaium.joe.config.extend.KeymapConfig;
import cn.enaium.joe.gui.panel.CodeAreaPanel;
import cn.enaium.joe.util.*;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Enaium
 */
public class ASMifierTablePanel extends ClassNodeTabPanel {
    public ASMifierTablePanel(ClassNode classNode) {
        super(classNode);
        setLayout(new BorderLayout());
        CodeAreaPanel codeAreaPanel = new CodeAreaPanel() {{
            KeyStrokeUtil.register(getTextArea(), JavaOctetEditor.getInstance().config.getByClass(KeymapConfig.class).save.getValue(), () -> {
                try {
                    String stringBuilder = "import org.objectweb.asm.AnnotationVisitor;" +
                            "import org.objectweb.asm.Attribute;" +
                            "import org.objectweb.asm.ClassReader;" +
                            "import org.objectweb.asm.ClassWriter;" +
                            "import org.objectweb.asm.ConstantDynamic;" +
                            "import org.objectweb.asm.FieldVisitor;" +
                            "import org.objectweb.asm.Handle;" +
                            "import org.objectweb.asm.Label;" +
                            "import org.objectweb.asm.MethodVisitor;" +
                            "import org.objectweb.asm.Opcodes;" +
                            "import org.objectweb.asm.RecordComponentVisitor;" +
                            "import org.objectweb.asm.ModuleVisitor;" +
                            "import org.objectweb.asm.Type;" +
                            "import org.objectweb.asm.TypePath;" +
                            "public class" + " " + ASMifier.class.getSimpleName() + " " + "implements Opcodes" +
                            "{" +
                            "public static byte[] dump() throws Exception {" +
                            getTextArea().getText() +
                            "return classWriter.toByteArray();" +
                            "}" +
                            "}";
                    Compiler compiler = new Compiler();
                    compiler.addSource(ASMifier.class.getSimpleName(), stringBuilder);
                    compiler.compile();

                    ClassLoader loader = new ClassLoader() {
                        @Override
                        protected Class<?> findClass(String name) {
                            byte[] bytes = compiler.getClasses().get(ASMifier.class.getSimpleName());
                            return defineClass(name, bytes, 0, bytes.length);
                        }
                    };

                    byte[] dumps = (byte[]) loader.loadClass(ASMifier.class.getSimpleName()).getMethod("dump").invoke(null);
                    ReflectUtil.copyAllMember(classNode, ASMUtil.acceptClassNode(new ClassReader(dumps)));
                    MessageUtil.info(LangUtil.i18n("success"));
                } catch (Throwable e) {
                    MessageUtil.error(e);
                }
            });
        }};
        codeAreaPanel.getTextArea().setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        codeAreaPanel.getTextArea().setEditable(true);
        StringWriter stringWriter = new StringWriter();

        ASyncUtil.execute(() -> {
            classNode.accept(new TraceClassVisitor(null, new ASMifier(), new PrintWriter(stringWriter)));
        }, () -> {
            String trim = getMiddle(getMiddle(stringWriter.toString())).trim();
            codeAreaPanel.getTextArea().setText(trim.substring(0, trim.lastIndexOf("\n")));
            codeAreaPanel.getTextArea().setCaretPosition(0);
        });
        add(codeAreaPanel);
    }

    public String getMiddle(String s) {
        return s.substring(s.indexOf("{") + 1, s.lastIndexOf("}"));
    }
}
