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

package cn.enaium.joe.dialog;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.gui.panel.BorderPanel;
import cn.enaium.joe.gui.panel.MemberListPanel;
import cn.enaium.joe.gui.panel.file.tree.node.MethodTreeNode;
import cn.enaium.joe.util.JMenuUtil;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.Pair;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.*;

/**
 * @author Enaium
 * @since 1.3.0
 */
public class CallTreeDialog extends Dialog {
    public CallTreeDialog(ClassNode classNode, MethodNode methodNode) {
        super(LangUtil.i18n("popup.member.callTree"));
        setContentPane(new BorderPanel() {{
            JTree call = new JTree() {{
                setModel(new DefaultTreeModel(new MethodTreeNode(classNode, methodNode) {{
                    recursion(this);
                }}));

                setCellRenderer(new DefaultTreeCellRenderer() {
                    @Override
                    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                        setIcon(new FlatSVGIcon("icons/method.svg"));
                        setText(value.toString());
                        return this;
                    }
                });
            }};
            JMenuUtil.addPopupMenu(call, () -> {
                MethodTreeNode lastPathComponent = (MethodTreeNode) Objects.requireNonNull(call.getSelectionPath()).getLastPathComponent();
                return MemberListPanel.getPopupMenu(new Pair<>(lastPathComponent.classNode, lastPathComponent.methodNode));
            }, () -> call.getSelectionPath() != null);
            setCenter(new JScrollPane(call));
        }});
    }

    private final Set<MethodNode> set = new HashSet<>();

    private void recursion(MethodTreeNode methodTreeNode) {
        MethodNode methodNode = methodTreeNode.methodNode;
        Map<String, Pair<ClassNode, MethodNode>> map = new HashMap<>();

        for (AbstractInsnNode instruction : methodNode.instructions) {
            if (instruction instanceof MethodInsnNode) {
                MethodInsnNode methodInsnNode = (MethodInsnNode) instruction;
                if (!(methodTreeNode.classNode.name + "." + methodNode.name + methodNode.desc).equals(methodInsnNode.owner + "." + methodInsnNode.name + methodInsnNode.desc)) {
                    Map<String, ClassNode> classes = JavaOctetEditor.getInstance().getJar().classes;
                    String key = methodInsnNode.owner + ".class";
                    if (classes.containsKey(key)) {
                        ClassNode classNode = classes.get(key);
                        //Find target method
                        for (MethodNode method : classNode.methods) {
                            if ((classNode.name + "." + method.name + method.desc).equals(methodInsnNode.owner + "." + methodInsnNode.name + methodInsnNode.desc)) {
                                //Deduplication
                                map.put(classNode.name + "." + method.name + method.desc, new Pair<>(classNode, method));
                                break;
                            }
                        }
                    }
                }
            }
        }

        for (Pair<ClassNode, MethodNode> value : map.values()) {
            MethodTreeNode newChild = new MethodTreeNode(value.getKey(), value.getValue());
            methodTreeNode.add(newChild);
            if (!set.contains(value.getValue())) {
                set.add(value.getValue());
                recursion(newChild);
            }
        }
    }
}
