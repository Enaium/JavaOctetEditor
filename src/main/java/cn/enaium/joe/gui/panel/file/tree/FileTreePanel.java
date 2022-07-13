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

import cn.enaium.joe.gui.JavaOctetEditor;
import cn.enaium.joe.gui.panel.file.FileDropTarget;
import cn.enaium.joe.gui.panel.file.tabbed.tab.FileTabPanel;
import cn.enaium.joe.gui.panel.file.tree.node.ClassTreeNode;
import cn.enaium.joe.gui.panel.file.tree.node.FieldTreeNode;
import cn.enaium.joe.gui.panel.file.tree.node.MethodTreeNode;
import cn.enaium.joe.gui.panel.file.tree.node.PackageTreeNode;
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.ASyncUtil;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.nio.file.Files;
import java.util.*;

/**
 * @author Enaium
 */
public class FileTreePanel extends JTree {

    public FileTreePanel() {
        super(new PackageTreeNode(""));
        setRootVisible(false);
        setShowsRootHandles(true);
        setCellRenderer(new FileTreeCellRenderer());
        addTreeSelectionListener(e -> {
            PackageTreeNode lastPathComponent = (PackageTreeNode) e.getPath().getLastPathComponent();
            if (lastPathComponent instanceof ClassTreeNode) {
                JavaOctetEditor.getInstance().fileTabbedPanel.addTab(lastPathComponent.toString(), new FileTabPanel(((ClassTreeNode) lastPathComponent).classNode));
                JavaOctetEditor.getInstance().fileTabbedPanel.setSelectedIndex(JavaOctetEditor.getInstance().fileTabbedPanel.getTabCount() - 1);
            }
        });

        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, new FileDropTarget(".jar", files -> {
            if (!files.isEmpty()) {
                File file = files.get(0);
                ASyncUtil.execute(() -> refresh(new Jar(file)));
            }
        }), true);
    }

    public void refresh(Jar jar) {
        DefaultTreeModel model = (DefaultTreeModel) getModel();
        model.reload();
        PackageTreeNode root = (PackageTreeNode) model.getRoot();
        root.removeAllChildren();

        Map<String, PackageTreeNode> hasMap = new HashMap<>();


        for (ClassNode classNode : jar.classes.values()) {
            String[] split = classNode.name.split("/");
            PackageTreeNode prev = null;
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            for (String s : split) {
                stringBuilder.append(s);
                PackageTreeNode fileTreeNode = new PackageTreeNode(s);

                if (split.length == i + 1) {
                    fileTreeNode = new ClassTreeNode(classNode);
                    for (MethodNode methodNode : classNode.methods) {
                        fileTreeNode.add(new MethodTreeNode(classNode, methodNode));
                    }
                    for (FieldNode field : classNode.fields) {
                        fileTreeNode.add(new FieldTreeNode(classNode, field));
                    }
                }

                if (prev == null) {
                    if (!hasMap.containsKey(stringBuilder.toString())) {
                        root.add(fileTreeNode);
                        hasMap.put(stringBuilder.toString(), fileTreeNode);
                        prev = fileTreeNode;
                    } else {
                        prev = hasMap.get(stringBuilder.toString());
                    }
                } else {
                    if (!hasMap.containsKey(stringBuilder.toString())) {
                        prev.add(fileTreeNode);
                        hasMap.put(stringBuilder.toString(), fileTreeNode);
                        prev = fileTreeNode;
                    } else {
                        prev = hasMap.get(stringBuilder.toString());
                    }
                }
                i++;
            }
        }
        sort(model, root);

        super.expandPath(new TreePath(root.getPath()));
    }


    public void sort(DefaultTreeModel defaultTreeModel, PackageTreeNode fileTreeNode) {
        if (!fileTreeNode.isLeaf()) {
            fileTreeNode.getChildren().sort((o1, o2) -> {
                boolean class1 = o1 instanceof ClassTreeNode;
                boolean class2 = o2 instanceof ClassTreeNode;

                boolean method1 = o1 instanceof MethodTreeNode;
                boolean method2 = o2 instanceof MethodTreeNode;

                if (class1 && !class2) {
                    return 1;
                }
                if (!class1 && class2) {
                    return -1;
                }


                if (method1 && !method2) {
                    return 1;
                }
                if (!method1 && method2) {
                    return -1;
                }
                return o1.toString().compareTo(o2.toString());
            });
            for (int i = 0; i < defaultTreeModel.getChildCount(fileTreeNode); i++) {
                PackageTreeNode child = ((PackageTreeNode) defaultTreeModel.getChild(fileTreeNode, i));
                sort(defaultTreeModel, child);
            }
        }
    }
}
