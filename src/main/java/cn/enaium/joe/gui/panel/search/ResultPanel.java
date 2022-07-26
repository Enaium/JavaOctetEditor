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

package cn.enaium.joe.gui.panel.search;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.gui.panel.file.tree.FileTreePanel;
import cn.enaium.joe.gui.panel.file.tree.node.*;
import cn.enaium.joe.util.ASyncUtil;
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
public class ResultPanel extends JPanel {

    private final JList<ResultNode> list;

    public ResultPanel() {
        super(new BorderLayout());
        list = new JList<>(new DefaultListModel<>());
        add(new JScrollPane(list), BorderLayout.CENTER);

        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem jMenuItem = new JMenuItem("Jump to Node");
        jMenuItem.addActionListener(e -> {
            if (list.getSelectedValue() != null) {
                ASyncUtil.execute(() -> {
                    SwingUtilities.invokeLater(() -> {
                        FileTreePanel fileTreePanel = JavaOctetEditor.getInstance().fileTreePanel;
                        DefaultTreeModel model = (DefaultTreeModel) fileTreePanel.getModel();
                        if (selectEntry(fileTreePanel, list.getSelectedValue().getClassNode(), model, FileTreePanel.classesRoot)) {
                            fileTreePanel.repaint();
                        }
                    });
                });
            }
        });
        jPopupMenu.add(jMenuItem);

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    if (list.getSelectedValue() != null) {
                        jPopupMenu.show(list, e.getX(), e.getY());
                    }
                }
            }
        });
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

    public JList<ResultNode> getList() {
        return list;
    }
}
