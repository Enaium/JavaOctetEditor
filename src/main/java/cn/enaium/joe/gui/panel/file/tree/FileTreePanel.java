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

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.gui.panel.file.FileDropTarget;
import cn.enaium.joe.gui.panel.file.tabbed.tab.classes.ClassTabPanel;
import cn.enaium.joe.gui.panel.file.tabbed.tab.classes.FieldInfoPanel;
import cn.enaium.joe.gui.panel.file.tabbed.tab.classes.method.MethodTabPanel;
import cn.enaium.joe.gui.panel.file.tabbed.tab.resources.HexTablePanel;
import cn.enaium.joe.gui.panel.file.tree.node.*;
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.task.InputJarTask;
import cn.enaium.joe.util.JTreeUtil;
import cn.enaium.joe.util.LangUtil;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * @author Enaium
 */
public class FileTreePanel extends JTree {

    public static final DefaultTreeNode classesRoot = new DefaultTreeNode("classes");
    public static final DefaultTreeNode resourceRoot = new DefaultTreeNode("resources");

    public FileTreePanel() {
        super(new DefaultTreeNode("") {{
            add(classesRoot);
            add(resourceRoot);
        }});

        setRootVisible(false);
        setShowsRootHandles(true);
        setCellRenderer(new FileTreeCellRenderer());

        addTreeSelectionListener(e -> {
            DefaultTreeNode lastPathComponent = (DefaultTreeNode) e.getPath().getLastPathComponent();
            SwingUtilities.invokeLater(() -> {
                if (lastPathComponent instanceof PackageTreeNode) {
                    PackageTreeNode packageTreeNode = (PackageTreeNode) lastPathComponent;
                    if (packageTreeNode instanceof ClassTreeNode) {
                        ClassNode classNode = ((ClassTreeNode) packageTreeNode).classNode;
                        JavaOctetEditor.getInstance().fileTabbedPanel.addTab(classNode.name.substring(classNode.name.lastIndexOf("/") + 1), new ClassTabPanel(classNode));
                    } else if (packageTreeNode instanceof MethodTreeNode) {
                        MethodTreeNode methodTreeNode = (MethodTreeNode) packageTreeNode;
                        MethodNode methodNode = methodTreeNode.methodNode;
                        JavaOctetEditor.getInstance().fileTabbedPanel.addTab(methodTreeNode.classNode.name.substring(methodTreeNode.classNode.name.lastIndexOf("/") + 1) + "#" + methodNode.name, new MethodTabPanel(methodNode));
                    } else if (packageTreeNode instanceof FieldTreeNode) {
                        FieldTreeNode fieldTreeNode = (FieldTreeNode) packageTreeNode;
                        FieldNode fieldNode = fieldTreeNode.fieldNode;
                        JavaOctetEditor.getInstance().fileTabbedPanel.addTab(fieldTreeNode.classNode.name.substring(fieldTreeNode.classNode.name.lastIndexOf("/") + 1) + "#" + fieldNode.name, new FieldInfoPanel(fieldNode));
                    }
                } else if (lastPathComponent instanceof FolderTreeNode) {
                    FolderTreeNode folderTreeNode = (FolderTreeNode) lastPathComponent;
                    if (folderTreeNode instanceof FileTreeNode) {
                        FileTreeNode fileTreeNode = (FileTreeNode) folderTreeNode;
                        JavaOctetEditor.getInstance().fileTabbedPanel.addTab(fileTreeNode.toString().substring(fileTreeNode.toString().lastIndexOf("/") + 1), new HexTablePanel(fileTreeNode));
                    }
                }
                JavaOctetEditor.getInstance().fileTabbedPanel.setSelectedIndex(JavaOctetEditor.getInstance().fileTabbedPanel.getTabCount() - 1);
            });
        });

        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, new FileDropTarget(".jar", files -> {
            if (!files.isEmpty()) {
                JavaOctetEditor.getInstance().task.submit(new InputJarTask(files.get(0)));
            }
        }), true);

        JPopupMenu jPopupMenu = new JPopupMenu();


        jPopupMenu.add(new JMenuItem(LangUtil.i18n("popup.expandAll")) {{
            addActionListener(e -> {
                JTreeUtil.setNodeExpandedState(FileTreePanel.this, ((DefaultMutableTreeNode) Objects.requireNonNull(getSelectionPath()).getLastPathComponent()), true);
            });
        }});

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && getSelectionPath() != null) {
                    jPopupMenu.show(FileTreePanel.this, e.getX(), e.getY());
                }
            }
        });

    }

    public void refresh(Jar jar) {
        DefaultTreeModel model = (DefaultTreeModel) getModel();
        model.reload();
        classesRoot.removeAllChildren();
        resourceRoot.removeAllChildren();

        JavaOctetEditor.getInstance().fileTabbedPanel.removeAll();

        Map<String, DefaultTreeNode> hasMap = new HashMap<>();


        for (ClassNode classNode : jar.classes.values()) {
            String[] split = classNode.name.split("/");
            DefaultTreeNode prev = null;
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            for (String s : split) {
                stringBuilder.append(s);
                PackageTreeNode packageTreeNode = new PackageTreeNode(s);

                if (split.length == i + 1) {
                    packageTreeNode = new ClassTreeNode(classNode);
                    for (MethodNode methodNode : classNode.methods) {
                        packageTreeNode.add(new MethodTreeNode(classNode, methodNode));
                    }
                    for (FieldNode field : classNode.fields) {
                        packageTreeNode.add(new FieldTreeNode(classNode, field));
                    }
                }

                if (prev == null) {
                    if (!hasMap.containsKey(stringBuilder.toString())) {
                        classesRoot.add(packageTreeNode);
                        hasMap.put(stringBuilder.toString(), packageTreeNode);
                        prev = packageTreeNode;
                    } else {
                        prev = hasMap.get(stringBuilder.toString());
                    }
                } else {
                    if (!hasMap.containsKey(stringBuilder.toString())) {
                        prev.add(packageTreeNode);
                        hasMap.put(stringBuilder.toString(), packageTreeNode);
                        prev = packageTreeNode;
                    } else {
                        prev = hasMap.get(stringBuilder.toString());
                    }
                }
                i++;
            }
        }
        sort(model, classesRoot);

        hasMap.clear();

        for (Map.Entry<String, byte[]> stringEntry : jar.resources.entrySet()) {
            String[] split = stringEntry.getKey().split("/");
            DefaultTreeNode prev = null;
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            for (String s : split) {
                stringBuilder.append(s);
                FolderTreeNode folderTreeNode = new FolderTreeNode(s);

                if (split.length == i + 1) {
                    folderTreeNode = new FileTreeNode(s, stringEntry.getValue());
                }

                if (prev == null) {
                    if (!hasMap.containsKey(stringBuilder.toString())) {
                        resourceRoot.add(folderTreeNode);
                        hasMap.put(stringBuilder.toString(), folderTreeNode);
                        prev = folderTreeNode;
                    } else {
                        prev = hasMap.get(stringBuilder.toString());
                    }
                } else {
                    if (!hasMap.containsKey(stringBuilder.toString())) {
                        prev.add(folderTreeNode);
                        hasMap.put(stringBuilder.toString(), folderTreeNode);
                        prev = folderTreeNode;
                    } else {
                        prev = hasMap.get(stringBuilder.toString());
                    }
                }
                i++;
            }
        }
        sort(model, resourceRoot);
        repaint();
    }


    public void sort(DefaultTreeModel defaultTreeModel, DefaultTreeNode defaultTreeNode) {
        if (!defaultTreeNode.isLeaf()) {
            defaultTreeNode.getChildren().sort((o1, o2) -> {
                boolean class1 = o1 instanceof ClassTreeNode;
                boolean class2 = o2 instanceof ClassTreeNode;

                boolean method1 = o1 instanceof MethodTreeNode;
                boolean method2 = o2 instanceof MethodTreeNode;

                boolean file1 = o1 instanceof FileTreeNode;
                boolean file2 = o2 instanceof FileTreeNode;

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

                if (file1 && !file2) {
                    return 1;
                }
                if (!file1 && file2) {
                    return -1;
                }
                return o1.toString().compareTo(o2.toString());
            });
            for (int i = 0; i < defaultTreeModel.getChildCount(defaultTreeNode); i++) {
                DefaultTreeNode child = ((DefaultTreeNode) defaultTreeModel.getChild(defaultTreeNode, i));
                sort(defaultTreeModel, child);
            }
        }
    }
}
