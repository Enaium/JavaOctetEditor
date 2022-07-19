package cn.enaium.joe.gui.panel.file.tabbed.tab;

import cn.enaium.joe.gui.panel.CodeAreaPanel;
import cn.enaium.joe.util.ASyncUtil;
import cn.enaium.joe.util.CfrUtil;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.objectweb.asm.tree.ClassNode;

import java.awt.*;

/**
 * @author Enaium
 */
public class DecompileTabPanel extends ClassNodeTabPanel {
    public DecompileTabPanel(ClassNode classNode) {
        super(classNode);
        setLayout(new BorderLayout());
        CodeAreaPanel codeAreaPanel = new CodeAreaPanel();
        codeAreaPanel.getTextArea().setSyntaxEditingStyle("text/java");
        ASyncUtil.execute(() -> {
            codeAreaPanel.getTextArea().setText(CfrUtil.getSource(classNode));
        });
        codeAreaPanel.getTextArea().setCaretPosition(0);
        add(codeAreaPanel);
    }
}
