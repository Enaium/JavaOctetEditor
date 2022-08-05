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
import org.benf.cfr.reader.util.StringUtils;
import org.objectweb.asm.tree.MethodNode;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Enaium
 * @since 0.6.0
 */
public class MethodInfoTabPanel extends JPanel {
    public MethodInfoTabPanel(MethodNode methodNode) {
        setLayout(new BorderLayout());
        JPanel labels = new JPanel(new GridLayout(0, 1));
        JPanel rights = new JPanel(new GridLayout(0, 1));
        add(labels, BorderLayout.WEST);
        add(rights, BorderLayout.CENTER);
        labels.add(new JLabel(LangUtil.i18n("class.info.name")));
        JTextField name = new JTextField(methodNode.name);
        rights.add(name);
        labels.add(new JLabel(LangUtil.i18n("class.info.description")));
        JTextField description = new JTextField(methodNode.desc);
        rights.add(description);
        labels.add(new JLabel(LangUtil.i18n("class.info.access")));
        JTextField access = new JTextField(String.valueOf(methodNode.access));
        rights.add(access);
        labels.add(new JLabel(LangUtil.i18n("class.info.signature")));
        JTextField signature = new JTextField(methodNode.signature);
        rights.add(signature);
        labels.add(new JLabel(LangUtil.i18n("class.info.exceptions")));
        JTextField exceptions = new JTextField(StringUtils.join(methodNode.exceptions, ";"));
        rights.add(exceptions);
        add(new JButton(LangUtil.i18n("button.save")) {{
            addActionListener(e -> {

                if (!StringUtil.isBlank(name.getText())) {
                    methodNode.name = name.getText();
                }


                if (!StringUtil.isBlank(description.getText())) {
                    methodNode.desc = description.getText();
                } else {
                    methodNode.desc = null;
                }

                if (!StringUtil.isBlank(access.getText())) {
                    methodNode.access = Integer.parseInt(access.getText());
                }

                if (!StringUtil.isBlank(signature.getText())) {
                    methodNode.signature = signature.getName();
                } else {
                    methodNode.signature = null;
                }

                if (!StringUtil.isBlank(signature.getText())) {
                    methodNode.exceptions = Arrays.asList(signature.getText().split(";"));
                } else {
                    methodNode.exceptions = new ArrayList<>();
                }

                JOptionPane.showMessageDialog(MethodInfoTabPanel.this, "Save success");
            });
        }}, BorderLayout.SOUTH);
    }
}
