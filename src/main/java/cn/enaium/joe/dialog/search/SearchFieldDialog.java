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

package cn.enaium.joe.dialog.search;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.dialog.SearchDialog;
import cn.enaium.joe.gui.panel.search.ResultNode;
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.ASyncUtil;
import cn.enaium.joe.util.StringUtil;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * @author Enaium
 * @since 0.5.0
 */
public class SearchFieldDialog extends SearchDialog {
    public SearchFieldDialog() {
        setTitle("Search Field");
        add(new JPanel(new FlowLayout()) {{
            add(new JLabel("Owner:"));
            JTextField owner = new JTextField();
            add(owner);
            add(new JLabel("Name:"));
            JTextField name = new JTextField();
            add(name);
            add(new JLabel("Description:"));
            JTextField description = new JTextField();
            add(description);
            add(new JButton("Search") {{
                addActionListener(e -> {
                    ASyncUtil.execute(() -> {
                        float loaded = 0;
                        float total = 0;
                        Jar jar = JavaOctetEditor.getInstance().jar;
                        for (Map.Entry<String, ClassNode> stringClassNodeEntry : jar.classes.entrySet()) {
                            for (MethodNode method : stringClassNodeEntry.getValue().methods) {
                                total += method.instructions.size();
                            }
                        }

                        for (Map.Entry<String, ClassNode> stringClassNodeEntry : jar.classes.entrySet()) {
                            for (MethodNode method : stringClassNodeEntry.getValue().methods) {
                                for (AbstractInsnNode instruction : method.instructions) {
                                    if (instruction instanceof FieldInsnNode) {
                                        FieldInsnNode fieldInsnNode = (FieldInsnNode) instruction;
                                        if ((fieldInsnNode.owner.contains(owner.getText()) || StringUtil.isBlank(owner.getText())) &&
                                                (fieldInsnNode.name.contains(name.getText()) || StringUtil.isBlank(name.getText())) &&
                                                (fieldInsnNode.desc.contains(description.getText()) || StringUtil.isBlank(description.getText()))
                                        ) {
                                            ((DefaultListModel<ResultNode>) resultPanel.getList().getModel()).addElement(new ResultNode(stringClassNodeEntry.getValue(), fieldInsnNode.name + ":" + fieldInsnNode.desc));
                                        }
                                    }
                                    JavaOctetEditor.getInstance().bottomPanel.setProcess((int) ((loaded++ / total) * 100f));
                                }
                            }
                        }
                        JavaOctetEditor.getInstance().bottomPanel.setProcess(0);
                    });
                });
            }});
        }}, BorderLayout.SOUTH);
    }
}
