package cn.enaium.joe.gui.panel.file.tabbed.tab;

import cn.enaium.joe.gui.panel.CodeArea;
import cn.enaium.joe.util.ASyncUtil;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.TraceClassVisitor;

import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author Enaium
 */
public class TraceBytecodeTabPanel extends ClassNodeTabPanel {
    public TraceBytecodeTabPanel(ClassNode classNode) {
        super(classNode);
        setLayout(new BorderLayout());
        CodeArea codeArea = new CodeArea();
        codeArea.setSyntaxEditingStyle("text/custom");
        final StringWriter stringWriter = new StringWriter();
        ASyncUtil.execute(() -> {
            classNode.accept(new TraceClassVisitor(new PrintWriter(stringWriter)));
        }, () -> {
            codeArea.setText(new String(stringWriter.toString().getBytes(StandardCharsets.UTF_8)));
            codeArea.setCaretPosition(0);
        });
        add(new RTextScrollPane(codeArea));
    }
}
