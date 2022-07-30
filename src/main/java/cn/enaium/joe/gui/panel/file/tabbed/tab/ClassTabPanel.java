package cn.enaium.joe.gui.panel.file.tabbed.tab;

import org.objectweb.asm.tree.ClassNode;

import javax.swing.*;
import java.awt.*;

/**
 * @author Enaium
 */
public class ClassTabPanel extends JPanel {
    public ClassTabPanel(ClassNode classNode) {
        super(new BorderLayout());
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        jTabbedPane.addTab("BytecodeView", new TraceBytecodeTabPanel(classNode));
        jTabbedPane.addTab("DecompileView", new DecompileTabPanel(classNode));
        jTabbedPane.addTab("VisitorEdit", new ASMifierTablePanel(classNode));
        jTabbedPane.addTab("InfoEdit", new ClassInfoTabPanel(classNode));
        add(jTabbedPane);
    }
}
