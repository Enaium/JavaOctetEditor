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
import cn.enaium.joe.wrapper.Wrapper;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class BootstrapMethodArgumentEditPanel extends ConfirmPanel {
    public BootstrapMethodArgumentEditPanel(Wrapper<Object[]> wrapper) {
        setLayout(new BorderLayout());
        DefaultListModel<Object> objectDefaultListModel = new DefaultListModel<>();
        JList<Object> objectJList = new JList<>(objectDefaultListModel);
        for (Object bsmArg : wrapper.getWrapper()) {
            objectDefaultListModel.addElement(bsmArg);
        }
        add(new JScrollPane(objectJList), BorderLayout.CENTER);
        add(new JPanel() {{
            add(new JButton(LangUtil.i18n("button.add")) {{
                addActionListener(e -> {
                    MessageUtil.confirm(new ConfirmPanel() {{
                        setLayout(new BorderLayout());
                        JPanel left = new JPanel(new GridLayout(0, 1));
                        JPanel right = new JPanel(new GridLayout(0, 1));
                        add(left, BorderLayout.WEST);
                        add(right, BorderLayout.CENTER);
                        JComboBox<String> jComboBox = new JComboBox<>(new String[]{"String", "float", "double", "int", "long", "Class"});
                        left.add(new JLabel(LangUtil.i18n("instruction.type")));
                        right.add(jComboBox);
                        left.add(new JLabel(LangUtil.i18n("instruction.var")));
                        JTextField ldc = new JTextField();
                        right.add(ldc);
                        setConfirm(() -> {
                            Object value;
                            if (jComboBox.getSelectedItem() != null) {
                                switch (jComboBox.getSelectedItem().toString()) {
                                    case "float":
                                        value = Float.parseFloat(ldc.getText());
                                        break;
                                    case "double":
                                        value = Double.parseDouble(ldc.getText());
                                        break;
                                    case "int":
                                        value = Integer.parseInt(ldc.getText());
                                        break;
                                    case "long":
                                        value = Long.parseLong(ldc.getText());
                                        break;
                                    case "Class":
                                        value = Type.getType(ldc.getText());
                                        break;
                                    default:
                                        value = ldc.getText();
                                }
                                objectDefaultListModel.addElement(value);
                            }
                        });
                    }}, LangUtil.i18n("button.add"));
                });
            }});
            add(new JButton("Add Handle") {{
                addActionListener(e -> {
                    Wrapper<Handle> handleWrapper = new Wrapper<>(new Handle(1, "", "", "", false));
                    HandleEditPanel confirmPanel = new HandleEditPanel(handleWrapper);
                    MessageUtil.confirm(confirmPanel, "Add Handle", () -> {
                        confirmPanel.getConfirm().run();
                        objectDefaultListModel.addElement(handleWrapper.getWrapper());
                    }, () -> {
                    });
                });
            }});
            add(new JButton(LangUtil.i18n("button.remove")) {{
                addActionListener(e -> {
                    if (objectJList.getSelectedIndex() != -1) {
                        objectDefaultListModel.remove(objectJList.getSelectedIndex());
                    }
                });
            }});
        }}, BorderLayout.SOUTH);
        setConfirm(() -> {
            List<Object> objects = new ArrayList<>();
            for (int i = 0; i < objectDefaultListModel.size(); i++) {
                objects.add(objectDefaultListModel.get(i));
            }
            wrapper.setWrapper(objects.toArray(new Object[0]));
        });
    }
}
