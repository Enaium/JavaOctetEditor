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
import cn.enaium.joe.gui.panel.CodeAreaPanel;
import cn.enaium.joe.util.*;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Loader;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
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
            KeyStrokeUtil.register(getTextArea(), KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), () -> {
                try {
                    StringWriter stringWriter = new StringWriter();
                    ClassReader classReader = new ClassReader(this.getClass().getName());
                    classReader.accept(new TraceClassVisitor(null, new ASMifier(), new PrintWriter(stringWriter)), 0);
                    ClassPool classPool = new ClassPool();
                    classPool.appendSystemPath();
                    classPool.importPackage("org.objectweb.asm.AnnotationVisitor");
                    classPool.importPackage("org.objectweb.asm.Attribute");
                    classPool.importPackage("org.objectweb.asm.ClassReader");
                    classPool.importPackage("org.objectweb.asm.ClassWriter");
                    classPool.importPackage("org.objectweb.asm.ConstantDynamic");
                    classPool.importPackage("org.objectweb.asm.FieldVisitor");
                    classPool.importPackage("org.objectweb.asm.Handle");
                    classPool.importPackage("org.objectweb.asm.Label");
                    classPool.importPackage("org.objectweb.asm.MethodVisitor");
                    classPool.importPackage("org.objectweb.asm.Opcodes");
                    classPool.importPackage("org.objectweb.asm.RecordComponentVisitor");
                    classPool.importPackage("org.objectweb.asm.Type");
                    classPool.importPackage("org.objectweb.asm.TypePath");
                    CtClass ctClass = classPool.makeClass(ASMifier.class.getSimpleName());
                    ctClass.addInterface(classPool.get("org.objectweb.asm.Opcodes"));
                    ctClass.addMethod(CtMethod.make("public static byte[] dump() throws Exception {" + getTextArea().getText() + "return classWriter.toByteArray();}", ctClass));
                    byte[] dumps = (byte[]) new Loader(classPool).loadClass(ASMifier.class.getSimpleName()).getMethod("dump").invoke(null);
                    ClassNode newClassNode = new ClassNode();
                    new ClassReader(dumps).accept(newClassNode, ClassReader.EXPAND_FRAMES);
                    ReflectUtil.setAll(classNode, newClassNode);
                    JOptionPane.showMessageDialog(null, LangUtil.i18n("success"));
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
