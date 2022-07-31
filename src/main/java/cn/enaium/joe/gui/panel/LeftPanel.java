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
import cn.enaium.joe.gui.panel.file.tree.FileTreePanel;
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.JTreeUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.Map;
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
                JTextField jTextField = this;
                jTextField.setText("Search...");
                jTextField.setForeground(Color.GRAY);
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        jTextField.setText("");
                        jTextField.setForeground(Color.WHITE);
                    }
                });
                addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            Jar jar = JavaOctetEditor.getInstance().jar;
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
        add(new JScrollPane(JavaOctetEditor.getInstance().fileTreePanel), BorderLayout.CENTER);
    }
}
