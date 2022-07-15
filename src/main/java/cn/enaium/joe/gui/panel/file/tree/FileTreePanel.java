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
import cn.enaium.joe.gui.panel.file.tree.node.*;
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.ASyncUtil;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Loader;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * @author Enaium
 */
public class FileTreePanel extends JTree {

    private static final DefaultTreeNode classesRoot = new DefaultTreeNode("classes");
    private static final DefaultTreeNode resourceRoot = new DefaultTreeNode("resources");

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
        classesRoot.removeAllChildren();
        resourceRoot.removeAllChildren();

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
