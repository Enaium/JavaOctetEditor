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
import cn.enaium.joe.event.events.FileTabbedSelectEvent;
import cn.enaium.joe.gui.panel.file.tabbed.tab.classes.ClassTabPanel;
import cn.enaium.joe.gui.panel.file.tree.FileTreeCellRenderer;
import cn.enaium.joe.gui.panel.file.tree.node.ClassTreeNode;
import cn.enaium.joe.util.*;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Enaium
 * @since 1.3.0
 */
public class InheritanceTree extends JTree {
    public InheritanceTree() {
        setModel(new DefaultTreeModel(null));
        setCellRenderer(new FileTreeCellRenderer());
        JavaOctetEditor.getInstance().event.register(FileTabbedSelectEvent.class, (Consumer<FileTabbedSelectEvent>) event -> {
            if (event.getSelect() instanceof ClassTabPanel) {
                ClassNode classNode = ((ClassTabPanel) event.getSelect()).classNode;
                ClassTreeNode root = new ClassTreeNode(classNode) {{
                    recursion(this);
                }};
                setModel(new DefaultTreeModel(root));
                JTreeUtil.setTreeExpandedState(this, true);
            }
        });
    }

    private void recursion(ClassTreeNode classTreeNode) {
        ClassNode classNode = classTreeNode.classNode;
        Set<String> superName = new HashSet<>();
        if (classNode.superName != null && !classNode.superName.equals(Object.class.getName().replace(".", "/"))) {
            superName.add(classNode.superName);
        }
        superName.addAll(classNode.interfaces);
        for (String s : superName) {
            Map<String, ClassNode> classes = JavaOctetEditor.getInstance().getJar().classes;
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
                recursion(newChild);
            }
        }
    }
}
