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
import cn.enaium.joe.config.extend.ApplicationConfig;
import cn.enaium.joe.gui.panel.file.FileDropTarget;
import cn.enaium.joe.gui.panel.file.tabbed.tab.classes.ClassTabPanel;
import cn.enaium.joe.gui.panel.file.tabbed.tab.resources.FileTablePane;
import cn.enaium.joe.gui.panel.file.tree.FileTreeCellRenderer;
import cn.enaium.joe.gui.panel.file.tree.node.*;
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.task.InputJarTask;
import cn.enaium.joe.util.JMenuUtil;
import cn.enaium.joe.util.JTreeUtil;
import cn.enaium.joe.util.LangUtil;
import org.objectweb.asm.tree.ClassNode;

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
public class FileTree extends JTree {

    public static final DefaultTreeNode classesRoot = new DefaultTreeNode("classes");
    public static final DefaultTreeNode resourceRoot = new DefaultTreeNode("resources");

    public FileTree() {
        super(new DefaultTreeNode("") {{
            add(classesRoot);
            add(resourceRoot);
        }});

        setRootVisible(false);
        setShowsRootHandles(true);
        setCellRenderer(new FileTreeCellRenderer());
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    addTab();
                }
            }
        });

        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, new FileDropTarget(".jar", files -> {
            if (!files.isEmpty()) {
                JavaOctetEditor.getInstance().task.submit(new InputJarTask(files.get(0)));
            }
        }), true);

        JPopupMenu jPopupMenu = new JPopupMenu();


        jPopupMenu.add(new JMenuItem(LangUtil.i18n("popup.fileTree.expandAll")) {{
            addActionListener(e -> {
                JTreeUtil.setNodeExpandedState(FileTree.this, ((DefaultMutableTreeNode) Objects.requireNonNull(getSelectionPath()).getLastPathComponent()), true);
            });
        }});
        JMenuUtil.addPopupMenu(this, () -> jPopupMenu, () -> getSelectionPath() != null);
    }

    public void addTab() {
        if (getSelectionPath() == null) {
            return;
        }
        Object lastPathComponent = getSelectionPath().getLastPathComponent();
        SwingUtilities.invokeLater(() -> {
            if (lastPathComponent instanceof PackageTreeNode) {
                PackageTreeNode packageTreeNode = (PackageTreeNode) lastPathComponent;
                if (packageTreeNode instanceof ClassTreeNode) {
                    ClassNode classNode = ((ClassTreeNode) packageTreeNode).classNode;
                    JavaOctetEditor.getInstance().fileTabbedPanel.addTab(classNode.name.substring(classNode.name.lastIndexOf("/") + 1), new ClassTabPanel(classNode));
                }
            } else if (lastPathComponent instanceof FolderTreeNode) {
                FolderTreeNode folderTreeNode = (FolderTreeNode) lastPathComponent;
                if (folderTreeNode instanceof FileTreeNode) {
                    FileTreeNode fileTreeNode = (FileTreeNode) folderTreeNode;
                    JavaOctetEditor.getInstance().fileTabbedPanel.addTab(fileTreeNode.toString().substring(fileTreeNode.toString().lastIndexOf("/") + 1), new FileTablePane(fileTreeNode));
                }
            }
            JavaOctetEditor.getInstance().fileTabbedPanel.setSelectedIndex(JavaOctetEditor.getInstance().fileTabbedPanel.getTabCount() - 1);
        });
    }

    public void refresh(Jar jar) {
        DefaultTreeModel model = (DefaultTreeModel) getModel();
        model.reload();
        classesRoot.removeAllChildren();
        resourceRoot.removeAllChildren();

        ApplicationConfig config = JavaOctetEditor.getInstance().config.getByClass(ApplicationConfig.class);


        String packagePresentationValue = config.packagePresentation.getValue();

        if (packagePresentationValue.equals("Hierarchical")) {
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
            compact(model, classesRoot);
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
                        folderTreeNode = new FileTreeNode(s,stringEntry.getKey());
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
            compact(model, classesRoot);
            sort(model, resourceRoot);
        } else if (packagePresentationValue.equals("Flat")) {
            Map<String, DefaultTreeNode> hasMap = new HashMap<>();
            for (ClassNode value : jar.classes.values()) {
                String packageName = "";
                if (value.name.contains("/")) {
                    packageName = value.name.substring(0, value.name.lastIndexOf("/")).replace("/", ".");
                }

                ClassTreeNode classTreeNode = new ClassTreeNode(value);

                if (packageName.isEmpty()) {
                    classesRoot.add(classTreeNode);
                } else {
                    DefaultTreeNode defaultTreeNode;
                    if (hasMap.containsKey(packageName)) {
                        defaultTreeNode = hasMap.get(packageName);
                    } else {
                        defaultTreeNode = new PackageTreeNode(packageName);
                        hasMap.put(packageName, defaultTreeNode);
                    }
                    defaultTreeNode.add(classTreeNode);
                    classesRoot.add(defaultTreeNode);
                }
            }
            hasMap.clear();

            for (Map.Entry<String, byte[]> stringEntry : jar.resources.entrySet()) {
                String folderName = "";
                String name = stringEntry.getKey();
                if (stringEntry.getKey().contains("/")) {
                    folderName = stringEntry.getKey().substring(0, stringEntry.getKey().lastIndexOf("/")).replace("/", ".");
                    name = name.substring(name.lastIndexOf("/") + 1);
                }

                FileTreeNode classTreeNode = new FileTreeNode(name, stringEntry.getKey());

                if (folderName.isEmpty()) {
                    resourceRoot.add(classTreeNode);
                } else {
                    DefaultTreeNode defaultTreeNode;
                    if (hasMap.containsKey(folderName)) {
                        defaultTreeNode = hasMap.get(folderName);
                    } else {
                        defaultTreeNode = new FolderTreeNode(folderName);
                        hasMap.put(folderName, defaultTreeNode);
                    }
                    defaultTreeNode.add(classTreeNode);
                    resourceRoot.add(defaultTreeNode);
                }
            }
            sort(model, resourceRoot);
        }

        JavaOctetEditor.getInstance().fileTabbedPanel.removeAll();

        repaint();
    }

    public void compact(DefaultTreeModel defaultTreeModel, DefaultTreeNode defaultTreeNode) {

        if (!JavaOctetEditor.getInstance().config.getByClass(ApplicationConfig.class).compactMiddlePackage.getValue()) {
            return;
        }

        if (!defaultTreeNode.isLeaf()) {
            DefaultTreeNode parent = (DefaultTreeNode) defaultTreeNode.getParent();
            if (parent.getChildren().size() == 1 && !(parent.equals(classesRoot) || parent.equals(resourceRoot))) {
                parent.setUserObject(parent.getUserObject() + "." + defaultTreeNode.getUserObject());
                parent.getChildren().clear();
                for (DefaultTreeNode child : defaultTreeNode.getChildren()) {
                    child.setParent(parent);
                    parent.getChildren().add(child);
                }
            }


            for (int i = 0; i < defaultTreeModel.getChildCount(defaultTreeNode); i++) {
                DefaultTreeNode child = ((DefaultTreeNode) defaultTreeModel.getChild(defaultTreeNode, i));
                compact(defaultTreeModel, child);
            }
        }
    }


    public void sort(DefaultTreeModel defaultTreeModel, DefaultTreeNode defaultTreeNode) {
        if (!defaultTreeNode.isLeaf()) {
            defaultTreeNode.getChildren().sort((o1, o2) -> {
                boolean class1 = o1 instanceof ClassTreeNode;
                boolean class2 = o2 instanceof ClassTreeNode;

                boolean file1 = o1 instanceof FileTreeNode;
                boolean file2 = o2 instanceof FileTreeNode;

                if (class1 && !class2) {
                    return 1;
                }
                if (!class1 && class2) {
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
