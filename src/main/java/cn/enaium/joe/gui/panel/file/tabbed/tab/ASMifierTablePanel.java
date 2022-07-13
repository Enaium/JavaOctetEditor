package cn.enaium.joe.gui.panel.file.tabbed.tab;

import cn.enaium.joe.gui.JavaOctetEditor;
import cn.enaium.joe.gui.panel.CodeArea;
import cn.enaium.joe.util.ASyncUtil;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Loader;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;

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
        add(new RTextScrollPane(codeArea));
        add(BorderLayout.SOUTH, new JPanel() {{
            add(new JButton("Save") {{
                addActionListener(e -> {
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
                    } catch (Throwable ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }});
        }});
    }

    public String getMiddle(String s) {
        return s.substring(s.indexOf("{") + 1, s.lastIndexOf("}"));
    }
}
