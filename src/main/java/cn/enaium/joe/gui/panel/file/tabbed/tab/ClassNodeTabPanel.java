package cn.enaium.joe.gui.panel.file.tabbed.tab;

import org.objectweb.asm.tree.ClassNode;

import javax.swing.*;

/**
 * @author Enaium
 */
public class ClassNodeTabPanel extends JPanel {
    private final ClassNode classNode;

    public ClassNodeTabPanel(ClassNode classNode) {
        this.classNode = classNode;
    }

    public ClassNode getClassNode() {
        return classNode;
    }
}
