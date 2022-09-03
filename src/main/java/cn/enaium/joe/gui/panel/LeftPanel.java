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
import cn.enaium.joe.event.Listener;
import cn.enaium.joe.gui.layout.HalfLayout;
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.JTreeUtil;
import cn.enaium.joe.util.LangUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Enaium
 */
public class LeftPanel extends JPanel {
    public LeftPanel() {
        super(new BorderLayout());
        add(new JPanel(new BorderLayout()) {{
            setBorder(new EmptyBorder(5, 0, 5, 0));
            add(new JTextField() {{
                putClientProperty("JTextField.placeholderText", LangUtil.i18n("menu.search"));
                JTextField jTextField = this;
                addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            Jar jar = JavaOctetEditor.getInstance().getJar();
                            if (jar != null) {
                                if (!jTextField.getText().replace(" ", "").isEmpty()) {
                                    Jar searchedJar = jar.copy();

                                    searchedJar.classes = searchedJar.classes.entrySet().stream().filter(stringClassNodeEntry -> {
                                        String key = stringClassNodeEntry.getKey();

                                        if (!key.contains("/")) {
                                            key = key.substring(key.lastIndexOf("/") + 1);
                                        }

                                        return key.toLowerCase(Locale.ROOT).contains(jTextField.getText().toLowerCase(Locale.ROOT));
                                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                                    searchedJar.resources = searchedJar.resources.entrySet().stream().filter(stringEntry -> {
                                        String key = stringEntry.getKey();
                                        if (!key.contains("/")) {
                                            key = key.substring(key.lastIndexOf("/") + 1);
                                        }
                                        return key.toLowerCase(Locale.ROOT).contains(jTextField.getText().toLowerCase(Locale.ROOT));
                                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                                    JavaOctetEditor.getInstance().fileTreePanel.refresh(searchedJar);
                                    JTreeUtil.setTreeExpandedState(JavaOctetEditor.getInstance().fileTreePanel, true);
                                } else {
                                    JavaOctetEditor.getInstance().fileTreePanel.refresh(jar);
                                }
                            }
                        }
                    }
                });
            }}, BorderLayout.CENTER);
        }}, BorderLayout.NORTH);
        JPanel jPanel = new JPanel(new HalfLayout(HalfLayout.TOP_AND_BOTTOM));
        jPanel.add(new JScrollPane(JavaOctetEditor.getInstance().fileTreePanel), HalfLayout.TOP);
        jPanel.add(new JScrollPane(new JLabel("No Member", SwingConstants.CENTER)) {{
            JavaOctetEditor.getInstance().event.register(BottomToggleButton.class, (Consumer<BottomToggleButton>) listener -> {
                if (listener.type == BottomToggleButton.Type.MEMBER) {
                    setVisible(listener.select);
                    jPanel.validate();
                }
            });
            setVisible(false);
        }}, HalfLayout.BOTTOM);
        add(new JPanel(new BorderLayout()) {{
            add(new JPanel(new HalfLayout(HalfLayout.TOP_AND_BOTTOM)) {{
                add(new JPanel(), HalfLayout.TOP);
                add(new JPanel(new BorderLayout()) {{
                    add(new JToggleButton("M") {{
                        addActionListener(e -> {
                            JavaOctetEditor.getInstance().event.call(new BottomToggleButton(isSelected(), BottomToggleButton.Type.MEMBER));
                        });
                    }}, BorderLayout.SOUTH);
                }}, HalfLayout.BOTTOM);
            }}, BorderLayout.WEST);
            add(jPanel, BorderLayout.CENTER);
        }}, BorderLayout.CENTER);
    }

    private static class BottomToggleButton implements Listener {
        private final boolean select;

        private final Type type;

        public BottomToggleButton(boolean select, Type type) {
            this.select = select;
            this.type = type;
        }

        public boolean isSelect() {
            return select;
        }

        public Type getType() {
            return type;
        }

        enum Type {
            MEMBER
        }
    }
}
