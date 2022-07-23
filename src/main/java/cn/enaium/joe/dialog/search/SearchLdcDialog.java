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
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * @author Enaium
 */
public class SearchLdcDialog extends SearchDialog {
    public SearchLdcDialog() {
        setTitle("Search LDC");
        add(new JPanel(new FlowLayout()) {{
            JTextField text = new JTextField(15);
            add(text);
            add(new JButton("Search") {{
                addActionListener(e -> {
                    if (!text.getText().replace(" ", "").isEmpty()) {
                        Jar jar = JavaOctetEditor.getInstance().jar;
                        ASyncUtil.execute(() -> {
                            float loaded = 0;
                            float total = 0;
                            for (Map.Entry<String, ClassNode> stringClassNodeEntry : jar.classes.entrySet()) {
                                for (MethodNode method : stringClassNodeEntry.getValue().methods) {
                                    total += method.instructions.size();
                                }
                            }

                            for (Map.Entry<String, ClassNode> stringClassNodeEntry : jar.classes.entrySet()) {
                                for (MethodNode method : stringClassNodeEntry.getValue().methods) {

                                    for (AbstractInsnNode instruction : method.instructions) {
                                        if (instruction instanceof LdcInsnNode) {
                                            String ldc = ((LdcInsnNode) instruction).cst.toString();
                                            if (ldc.contains(text.getText())) {
                                                ((DefaultListModel<ResultNode>) result.getList().getModel()).addElement(new ResultNode(stringClassNodeEntry.getValue(), ldc));
                                            }
                                        }
                                        JavaOctetEditor.getInstance().bottomPanel.setProcess((int) ((loaded++ / total) * 100f));
                                    }
                                }
                            }
                            JavaOctetEditor.getInstance().bottomPanel.setProcess(0);
                        });
                    }
                });
            }});
        }}, BorderLayout.SOUTH);
    }
}
