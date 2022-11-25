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
import cn.enaium.joe.annotation.NoUI;
import cn.enaium.joe.config.Config;
import cn.enaium.joe.config.value.*;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.MessageUtil;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;

/**
 * @author Enaium
 * @since 0.7.0
 */
public class ConfigDialog extends Dialog {
    public ConfigDialog(Config config) {
        super(LangUtil.i18n("menu.config"));

        setContentPane(new JScrollPane(new JPanel(new MigLayout("fillx", "[fill][fill]")) {{
            try {
                for (Field declaredField : config.getClass().getDeclaredFields()) {
                    declaredField.setAccessible(true);

                    if (declaredField.isAnnotationPresent(NoUI.class)) {
                        continue;
                    }

                    Object o = declaredField.get(config);

                    if (o instanceof Value) {
                        Value<?> value = (Value<?>) o;

                        add(new JLabel(value.getName()) {{
                            setToolTipText(value.getDescription());
                        }});
                    }

                    if (o instanceof StringValue) {
                        StringValue stringValue = (StringValue) o;
                        add(new JTextField(25) {{
                            JTextField jTextField = this;
                            jTextField.setText(stringValue.getValue());
                            getDocument().addDocumentListener(new DocumentListener() {
                                @Override
                                public void insertUpdate(DocumentEvent e) {
                                    stringValue.setValue(jTextField.getText());
                                }

                                @Override
                                public void removeUpdate(DocumentEvent e) {
                                    stringValue.setValue(jTextField.getText());
                                }

                                @Override
                                public void changedUpdate(DocumentEvent e) {
                                    stringValue.setValue(jTextField.getText());
                                }
                            });
                        }}, "wrap");
                    } else if (o instanceof IntegerValue) {
                        IntegerValue integerValue = (IntegerValue) o;
                        add(new JSpinner() {{
                            setValue(integerValue.getValue());
                            addChangeListener(e -> integerValue.setValue(Integer.parseInt(getValue().toString())));
                        }}, "wrap");
                    } else if (o instanceof EnableValue) {
                        EnableValue enableValue = (EnableValue) o;
                        add(new JCheckBox() {{
                            JCheckBox jCheckBox = this;
                            setHorizontalAlignment(JCheckBox.RIGHT);
                            setSelected(enableValue.getValue());
                            addActionListener(e -> {
                                enableValue.setValue(jCheckBox.isSelected());
                            });
                        }}, "wrap");
                    } else if (o instanceof ModeValue) {
                        add(new JComboBox<String>(new DefaultComboBoxModel<>()) {{
                            JComboBox<String> jComboBox = this;
                            ModeValue modeValue = (ModeValue) o;
                            for (String s : modeValue.getMode()) {
                                DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) getModel();
                                model.addElement(s);
                                model.setSelectedItem(modeValue.getValue());
                                jComboBox.addActionListener(e -> {
                                    modeValue.setValue(model.getSelectedItem().toString());
                                });
                            }
                        }}, "wrap");
                    } else if (o instanceof KeyValue) {
                        KeyValue keyValue = (KeyValue) o;
                        add(new JButton(keyValue.getValue().toString()) {{
                            addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyPressed(KeyEvent e) {
                                    if (e.getKeyChar() != 65535) {
                                        KeyStroke newKey = KeyStroke.getKeyStroke(e.getKeyCode(), e.getModifiers());
                                        keyValue.setValue(newKey);
                                        setText(newKey.toString());
                                    }
                                }
                            });
                        }}, "wrap");
                    }
                }
            } catch (IllegalAccessException e) {
                MessageUtil.error(e);
            }
        }}));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JavaOctetEditor.getInstance().config.save();
            }
        });
    }
}
