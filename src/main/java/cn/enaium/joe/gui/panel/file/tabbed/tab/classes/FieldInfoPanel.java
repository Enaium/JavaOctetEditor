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

package cn.enaium.joe.gui.panel.file.tabbed.tab.classes;

import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.StringUtil;
import org.objectweb.asm.tree.FieldNode;

import javax.swing.*;
import java.awt.*;

/**
 * @author Enaium
 * @since 0.6.0
 */
public class FieldInfoPanel extends JPanel {
    public FieldInfoPanel(FieldNode fieldNode) {
        setLayout(new BorderLayout());
        JPanel labels = new JPanel(new GridLayout(0, 1));
        JPanel rights = new JPanel(new GridLayout(0, 1));
        add(labels, BorderLayout.WEST);
        add(rights, BorderLayout.CENTER);
        labels.add(new JLabel(LangUtil.i18n("class.info.name")));
        JTextField name = new JTextField(fieldNode.name);
        rights.add(name);
        labels.add(new JLabel(LangUtil.i18n("class.info.description")));
        JTextField description = new JTextField(fieldNode.desc);
        rights.add(description);
        labels.add(new JLabel(LangUtil.i18n("class.info.access")));
        JTextField access = new JTextField(String.valueOf(fieldNode.access));
        rights.add(access);
        labels.add(new JLabel(LangUtil.i18n("class.info.signature")));
        JTextField signature = new JTextField(fieldNode.signature);
        rights.add(signature);
        add(new JButton(LangUtil.i18n("button.save")) {{
            addActionListener(e -> {

                if (!StringUtil.isBlank(name.getText())) {
                    fieldNode.name = name.getText();
                }


                if (!StringUtil.isBlank(description.getText())) {
                    fieldNode.desc = description.getText();
                } else {
                    fieldNode.desc = null;
                }

                if (!StringUtil.isBlank(access.getText())) {
                    fieldNode.access = Integer.parseInt(access.getText());
                }

                if (!StringUtil.isBlank(signature.getText())) {
                    fieldNode.signature = signature.getName();
                } else {
                    fieldNode.signature = null;
                }

                JOptionPane.showMessageDialog(FieldInfoPanel.this,  LangUtil.i18n("success"));
            });
        }}, BorderLayout.SOUTH);
    }
}
