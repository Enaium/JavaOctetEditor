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

package cn.enaium.joe.gui.component;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.gui.panel.file.tree.node.*;
import cn.enaium.joe.gui.panel.search.ResultNode;
import cn.enaium.joe.util.ASyncUtil;
import cn.enaium.joe.util.JMenuUtil;
import org.objectweb.asm.tree.ClassNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Enaium
 */
public class ResultList extends JList<ResultNode> {

    public ResultList() {
        super(new DefaultListModel<>());
        //avoid repeat updateFixedCellSize
        setPrototypeCellValue(new ResultNode());
        //fix auto wrap
        setCellRenderer((list, value, index, isSelected, cellHasFocus) -> new JPanel(new BorderLayout()) {{
            if (isSelected) {
                setBackground(list.getSelectionBackground());
            } else {
                setBackground(list.getBackground());
            }
            JLabel comp = new JLabel(value.toString());
            add(comp, BorderLayout.CENTER);
        }});
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem jMenuItem = new JMenuItem("Jump to Node");
        jMenuItem.addActionListener(e -> {
            if (getSelectedValue() != null) {
                SwingUtilities.invokeLater(() -> {
                    FileTree fileTree = JavaOctetEditor.getInstance().fileTree;
                    DefaultTreeModel model = (DefaultTreeModel) fileTree.getModel();
                    if (selectEntry(fileTree, getSelectedValue().getClassNode(), model, FileTree.classesRoot)) {
                        fileTree.addTab();
                        fileTree.repaint();
                    }
                });
            }
        });
        jPopupMenu.add(jMenuItem);
        JMenuUtil.addPopupMenu(this, jPopupMenu, () -> this.getSelectedValue() != null);
    }

    public boolean selectEntry(JTree jTree, ClassNode classNode, DefaultTreeModel defaultTreeModel, DefaultTreeNode defaultTreeNode) {
        for (int i = 0; i < defaultTreeModel.getChildCount(defaultTreeNode); i++) {
            DefaultTreeNode child = ((DefaultTreeNode) defaultTreeModel.getChild(defaultTreeNode, i));
            if (child instanceof PackageTreeNode) {
                PackageTreeNode packageTreeNode = (PackageTreeNode) child;
                if (packageTreeNode instanceof ClassTreeNode) {
                    ClassTreeNode classTreeNode = (ClassTreeNode) packageTreeNode;
                    if (classNode.equals(classTreeNode.classNode)) {
                        TreePath treePath = new TreePath(defaultTreeModel.getPathToRoot(classTreeNode));
                        jTree.setSelectionPath(treePath);
                        jTree.scrollPathToVisible(treePath);
                        return true;
                    }
                }
            }
            if (!defaultTreeNode.isLeaf()) {
                if (selectEntry(jTree, classNode, defaultTreeModel, child)) {
                    return true;
                }
            }
        }
        return false;
    }
}
