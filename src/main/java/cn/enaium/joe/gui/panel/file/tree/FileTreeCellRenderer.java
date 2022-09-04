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

package cn.enaium.joe.gui.panel.file.tree;

import cn.enaium.joe.gui.panel.file.tree.node.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * @author Enaium
 */
public class FileTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        FileTreeCellRenderer self = this;
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        final DefaultTreeNode defaultTreeNode = (DefaultTreeNode) value;

        if (defaultTreeNode.toString().equals("classes")) {
            setIcon(new FlatSVGIcon("icons/classesRoot.svg"));
        } else if (defaultTreeNode.toString().equals("resources")) {
            setIcon(new FlatSVGIcon("icons/resourceRoot.svg"));
        } else if (defaultTreeNode instanceof PackageTreeNode) {
            setIcon(new FlatSVGIcon("icons/package.svg"));
            PackageTreeNode packageTreeNode = (PackageTreeNode) defaultTreeNode;
            if (packageTreeNode instanceof ClassTreeNode) {
                ClassNode classNode = ((ClassTreeNode) packageTreeNode).classNode;
                if (classNode.access == (Opcodes.ACC_PUBLIC | Opcodes.ACC_ANNOTATION | Opcodes.ACC_ABSTRACT | Opcodes.ACC_INTERFACE)) {
                    setIcon(new FlatSVGIcon("icons/annotation.svg"));
                } else if (classNode.access == (Opcodes.ACC_PUBLIC | Opcodes.ACC_ABSTRACT | Opcodes.ACC_INTERFACE)) {
                    setIcon(new FlatSVGIcon("icons/interface.svg"));
                } else if (classNode.access == (Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER | Opcodes.ACC_ABSTRACT)) {
                    setIcon(new FlatSVGIcon("icons/abstractClass.svg"));
                } else if (classNode.access == (Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL | Opcodes.ACC_SUPER | Opcodes.ACC_ENUM)) {
                    setIcon(new FlatSVGIcon("icons/enum.svg"));
                } else if (classNode.access == (Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL | Opcodes.ACC_SUPER)) {
                    setIcon(new FlatSVGIcon("icons/finalClass.svg"));
                } else {
                    setIcon(new FlatSVGIcon("icons/class.svg"));
                }
            }
        } else if (defaultTreeNode instanceof FolderTreeNode) {
            setIcon(new FlatSVGIcon("icons/folder.svg"));

            FolderTreeNode folderTreeNode = (FolderTreeNode) defaultTreeNode;
            if (folderTreeNode instanceof FileTreeNode) {
                setIcon(new FlatSVGIcon("icons/file.svg"));
            }
        }
        return new JPanel(new BorderLayout()) {{
            if (sel) {
                setBackground(self.getBackgroundSelectionColor());
            } else {
                setBackground(self.getBackground());
            }
            add(new JLabel(value.toString()) {{
                setIcon(self.getIcon());
            }}, BorderLayout.CENTER);
        }};
    }
}
