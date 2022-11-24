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

import cn.enaium.joe.gui.panel.BorderPanel;
import cn.enaium.joe.gui.panel.confirm.ConfirmPanel;
import cn.enaium.joe.util.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author Enaium
 * @since 1.4.0
 */
public class OptionDialog extends Dialog {

    public OptionDialog(String title, Object message, int type, Runnable confirm, Runnable cancel) {
        super(title);
        setContentPane(new BorderPanel() {{
            setBorder(new EmptyBorder(10, 10, 10, 10));
            JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            if (message != null) {
                bottom.add(new JButton(UIManager.getString("OptionPane.okButton.textAndMnemonic")) {{
                    addActionListener(e -> {
                        if (confirm != null) {
                            confirm.run();
                        }
                        dispose();
                    });
                }});

                if (cancel != null) {
                    bottom.add(new JButton(UIManager.getString("OptionPane.cancelButton.textAndMnemonic")) {{
                        addActionListener(e -> {
                            cancel.run();
                            dispose();
                        });
                    }});
                }

                Component content;
                if (message instanceof String) {
                    content = (new JLabel(message.toString()));
                } else if (message instanceof ConfirmPanel) {
                    content = ((Component) message);
                } else {
                    content = ((Component) message);
                }
                setLeft(new JLabel(getIconForType(type)));
                setCenter(content);
            }
            setBottom(bottom);
        }});
        setModal(true);
        pack();
        setMinimumSize(getSize());
    }

    public OptionDialog(String title, Object message, int type) {
        this(title, message, type, null, null);
    }

    private Icon getIconForType(int messageType) {
        if (messageType < 0 || messageType > 3)
            return null;
        String propertyName = null;
        switch (messageType) {
            case 0:
                propertyName = "OptionPane.errorIcon";
                break;
            case 1:
                propertyName = "OptionPane.informationIcon";
                break;
            case 2:
                propertyName = "OptionPane.warningIcon";
                break;
            case 3:
                propertyName = "OptionPane.questionIcon";
                break;
        }
        return (Icon) UIManager.get(propertyName);
    }
}
