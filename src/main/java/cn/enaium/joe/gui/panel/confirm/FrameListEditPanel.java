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

package cn.enaium.joe.gui.panel.confirm;

import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.MessageUtil;
import cn.enaium.joe.util.OpcodeUtil;
import cn.enaium.joe.util.StringUtil;
import org.objectweb.asm.tree.FrameNode;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class FrameListEditPanel extends ConfirmPanel {

    public FrameListEditPanel(FrameNode frameNode) {
        setLayout(new BorderLayout(10, 10));
        JPanel left = new JPanel(new BorderLayout());
        JPanel right = new JPanel(new BorderLayout());
        left.add(new JLabel("Local", JLabel.CENTER), BorderLayout.NORTH);
        ObjectList localObjectList = new ObjectList(frameNode.local);
        left.add(localObjectList, BorderLayout.CENTER);
        right.add(new JLabel("Stack", JLabel.CENTER), BorderLayout.NORTH);
        ObjectList stackObjectList = new ObjectList(frameNode.stack);
        right.add(stackObjectList, BorderLayout.CENTER);
        add(left, BorderLayout.WEST);
        add(new JSeparator(JSeparator.VERTICAL), BorderLayout.CENTER);
        add(right, BorderLayout.EAST);
        setConfirm(() -> {
            frameNode.local = localObjectList.getList().stream().map(it -> {
                Map<String, Integer> reverse = OpcodeUtil.reverse(OpcodeUtil.FRAME_ELEMENT);
                if (reverse.containsKey(it.toString())) {
                    return reverse.get(it.toString());
                } else {
                    return it;
                }
            }).collect(Collectors.toList());

            frameNode.stack = stackObjectList.getList().stream().map(it -> {
                Map<String, Integer> reverse = OpcodeUtil.reverse(OpcodeUtil.FRAME_ELEMENT);
                if (reverse.containsKey(it.toString())) {
                    return reverse.get(it.toString());
                } else {
                    return it;
                }
            }).collect(Collectors.toList());
        });
    }

    private static class ObjectList extends JPanel {
        DefaultListModel<String> stringDefaultListModel = new DefaultListModel<>();

        public ObjectList(List<Object> list) {
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(5, 5, 5, 5));
            JList<String> jList = new JList<>(stringDefaultListModel);
            for (Object o : list) {
                if (o instanceof String) {
                    stringDefaultListModel.addElement(o.toString());
                } else if (o instanceof Integer) {
                    stringDefaultListModel.addElement(OpcodeUtil.FRAME_ELEMENT.get(Integer.parseInt(o.toString())));
                }
            }
            add(new JScrollPane(jList), BorderLayout.CENTER);
            add(new JPanel(new GridLayout(1, 3)) {{
                add(new JButton("Add String") {{
                    addActionListener(e -> {
                        String s = JOptionPane.showInputDialog(ObjectList.this, "Input:");
                        if (s != null && !StringUtil.isBlank(s)) {
                            stringDefaultListModel.addElement(s);
                        }
                    });
                }});
                add(new JButton("Add Type") {{
                    addActionListener(e -> {
                        JComboBox<String> message = new JComboBox<>(OpcodeUtil.FRAME_ELEMENT.values().toArray(new String[0]));
                        MessageUtil.confirm(message, "Type", () -> {
                            Object selectedItem = message.getSelectedItem();
                            if (selectedItem != null) {
                                stringDefaultListModel.addElement(selectedItem.toString());
                            }
                        }, () -> {

                        });
                    });
                }});
                add(new JButton(LangUtil.i18n("button.remove")) {{
                    addActionListener(e -> {
                        if (jList.getSelectedIndex() != -1) {
                            stringDefaultListModel.remove(jList.getSelectedIndex());
                        }
                    });
                }});
            }}, BorderLayout.SOUTH);
        }

        public List<Object> getList() {
            return Arrays.asList(stringDefaultListModel.toArray());
        }
    }
}
