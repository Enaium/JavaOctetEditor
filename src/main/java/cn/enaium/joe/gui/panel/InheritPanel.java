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

package cn.enaium.joe.gui.panel;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.event.events.FileTabbedSelectEvent;
import cn.enaium.joe.gui.panel.file.tabbed.tab.classes.ClassTabPanel;
import cn.enaium.joe.gui.panel.file.tree.FileTreeCellRenderer;
import cn.enaium.joe.gui.panel.file.tree.node.ClassTreeNode;
import cn.enaium.joe.gui.panel.file.tree.node.PackageTreeNode;
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.ASMUtil;
import cn.enaium.joe.util.JTreeUtil;
import cn.enaium.joe.util.ReflectUtil;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Enaium
 * @since 1.3.0
 */
public class InheritPanel extends BorderPanel {

    private ClassNode current;

    public InheritPanel() {
        JTree inheritance = new JTree() {{
            setModel(new DefaultTreeModel(null));
            setCellRenderer(new FileTreeCellRenderer());
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        if (getSelectionPath() != null) {
                            Object lastPathComponent = getSelectionPath().getLastPathComponent();
                            if (lastPathComponent instanceof PackageTreeNode) {
                                PackageTreeNode packageTreeNode = (PackageTreeNode) lastPathComponent;
                                if (packageTreeNode instanceof ClassTreeNode) {
                                    ClassNode classNode = ((ClassTreeNode) packageTreeNode).classNode;
                                    JavaOctetEditor.getInstance().fileTabbedPanel.addTab(classNode.name.substring(classNode.name.lastIndexOf("/") + 1), new ClassTabPanel(classNode));
                                }
                            }
                        }
                    }
                }
            });
        }};
        JavaOctetEditor.getInstance().event.register(FileTabbedSelectEvent.class, (Consumer<FileTabbedSelectEvent>) event -> {
            if (event.getSelect() instanceof ClassTabPanel) {
                current = ((ClassTabPanel) event.getSelect()).classNode;
                setModel(inheritance, true);
            } else {
                current = null;
                inheritance.setModel(new DefaultTreeModel(null));
            }
            repaint();
        });
        setCenter(new JScrollPane(inheritance));

        setBottom(new JToggleButton("Parent", true) {{
            addActionListener(e -> InheritPanel.this.setModel(inheritance, isSelected()));
        }});
    }

    private void setModel(JTree jTree, boolean p) {
        if (current != null) {
            jTree.setModel(new DefaultTreeModel(new ClassTreeNode(current) {{
                recursion(this, p);
            }}));
            JTreeUtil.setTreeExpandedState(jTree, true);
        }
    }

    private void recursion(ClassTreeNode classTreeNode, boolean parent) {
        ClassNode classNode = classTreeNode.classNode;
        Jar jar = JavaOctetEditor.getInstance().getJar();
        if (parent) {
            for (String s : ASMUtil.getParentClass(classNode)) {
                Map<String, ClassNode> classes = jar.classes;
                ClassTreeNode newChild = null;
                if (classes.containsKey(s + ".class")) {
                    newChild = new ClassTreeNode(classes.get(s + ".class"));
                } else if (ReflectUtil.classHas(s.replace("/", "."))) {
                    try {
                        newChild = new ClassTreeNode(ASMUtil.acceptClassNode(new ClassReader(s)));
                    } catch (IOException ignored) {

                    }
                }
                if (newChild != null) {
                    classTreeNode.add(newChild);
                    recursion(newChild, true);
                }
            }
        } else {
            for (ClassNode value : jar.classes.values()) {
                Set<String> parentClass = ASMUtil.getParentClass(value);
                if (parentClass.contains(classNode.name)) {
                    ClassTreeNode newChild = new ClassTreeNode(value);
                    classTreeNode.add(newChild);
                    recursion(newChild, false);
                }
            }
        }
    }
}
