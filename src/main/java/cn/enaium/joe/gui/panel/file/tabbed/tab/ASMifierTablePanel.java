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

package cn.enaium.joe.gui.panel.file.tabbed.tab;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.gui.panel.CodeArea;
import cn.enaium.joe.util.ASyncUtil;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Loader;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import javax.swing.*;
import java.awt.*;
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
        CodeArea codeArea = new CodeArea();
        codeArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        codeArea.setEditable(true);
        StringWriter stringWriter = new StringWriter();
        ASyncUtil.execute(() -> {
            classNode.accept(new TraceClassVisitor(null, new ASMifier(), new PrintWriter(stringWriter)));
        }, () -> {
            String trim = getMiddle(getMiddle(stringWriter.toString())).trim();
            codeArea.setText(trim.substring(0, trim.lastIndexOf("\n")));
            codeArea.setCaretPosition(0);
        });
        add(new RTextScrollPane(codeArea) {{
            getTextArea().addKeyListener(new KeyAdapter() {

                boolean control = false;

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                        control = true;
                    } else if (e.getKeyCode() == KeyEvent.VK_S) {
                        if (control) {
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
                                ctClass.addMethod(CtMethod.make("public static byte[] dump() throws Exception {" + codeArea.getText() + "return classWriter.toByteArray();}", ctClass));
                                byte[] dumps = (byte[]) new Loader(classPool).loadClass(ASMifier.class.getSimpleName()).getMethod("dump").invoke(null);
                                ClassNode newClassNode = new ClassNode();
                                new ClassReader(dumps).accept(newClassNode, ClassReader.EXPAND_FRAMES);
                                JavaOctetEditor.getInstance().jar.classes.put(newClassNode.name + ".class", newClassNode);
                                JOptionPane.showMessageDialog(null, "Save Success");
                            } catch (Throwable ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                        control = false;
                    }
                }
            });
        }});
    }

    public String getMiddle(String s) {
        return s.substring(s.indexOf("{") + 1, s.lastIndexOf("}"));
    }
}
