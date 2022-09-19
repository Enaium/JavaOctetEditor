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

import cn.enaium.joe.dialog.AnnotationListDialog;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.StringUtil;
import net.miginfocom.swing.MigLayout;
import org.objectweb.asm.tree.FieldNode;

import javax.swing.*;
import java.awt.*;

/**
 * @author Enaium
 * @since 0.6.0
 */
public class FieldInfoPanel extends JPanel {
    public FieldInfoPanel(FieldNode fieldNode) {
        setLayout(new MigLayout("fillx", "[fill][fill]"));
        add(new JLabel(LangUtil.i18n("class.info.name")));
        JTextField name = new JTextField(fieldNode.name);
        add(name, "wrap");
        add(new JLabel(LangUtil.i18n("class.info.description")));
        JTextField description = new JTextField(fieldNode.desc);
        add(description, "wrap");
        add(new JLabel(LangUtil.i18n("class.info.access")));
        JTextField access = new JTextField(String.valueOf(fieldNode.access));
        add(access, "wrap");
        add(new JLabel(LangUtil.i18n("class.info.signature")));
        JTextField signature = new JTextField(fieldNode.signature);
        add(signature, "wrap");
        add(new JLabel("Visible Annotation:"));
        add(new JButton(LangUtil.i18n("button.edit")) {{
            addActionListener(e -> {
                if (fieldNode.visibleAnnotations != null) {
                    new AnnotationListDialog(fieldNode.visibleAnnotations).setVisible(true);
                }
            });
        }}, "wrap");
        add(new JLabel("Invisible Annotation:"));
        add(new JButton(LangUtil.i18n("button.edit")) {{
            addActionListener(e -> {
                if (fieldNode.invisibleAnnotations != null) {
                    new AnnotationListDialog(fieldNode.invisibleAnnotations).setVisible(true);
                }
            });
        }}, "wrap");
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

                JOptionPane.showMessageDialog(FieldInfoPanel.this, LangUtil.i18n("success"));
            });
        }}, "span 2");
    }
}
