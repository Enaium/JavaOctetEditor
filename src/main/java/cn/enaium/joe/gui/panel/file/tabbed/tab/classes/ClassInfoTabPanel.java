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


import cn.enaium.joe.dialog.AnnotationListDialog;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.StringUtil;
import org.benf.cfr.reader.util.StringUtils;
import org.objectweb.asm.tree.ClassNode;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Enaium
 * @since 0.6.0
 */
public class ClassInfoTabPanel extends ClassNodeTabPanel {
    public ClassInfoTabPanel(ClassNode classNode) {
        super(classNode);
        setLayout(new BorderLayout());
        JPanel labels = new JPanel(new GridLayout(0, 1));
        JPanel rights = new JPanel(new GridLayout(0, 1));
        add(labels, BorderLayout.WEST);
        add(rights, BorderLayout.CENTER);
        labels.add(new JLabel(LangUtil.i18n("class.info.name")));
        JTextField name = new JTextField(classNode.name);
        rights.add(name);
        labels.add(new JLabel(LangUtil.i18n("class.info.sourceFile")));
        JTextField sourceFile = new JTextField(classNode.sourceFile);
        rights.add(sourceFile);
        labels.add(new JLabel(LangUtil.i18n("class.info.debugFile")));
        JTextField sourceDebug = new JTextField(classNode.sourceDebug);
        rights.add(sourceDebug);
        labels.add(new JLabel(LangUtil.i18n("class.info.access")));
        JTextField access = new JTextField(String.valueOf(classNode.access));
        rights.add(access);
        labels.add(new JLabel(LangUtil.i18n("class.info.version")));
        JTextField version = new JTextField(String.valueOf(classNode.version));
        rights.add(version);
        labels.add(new JLabel(LangUtil.i18n("class.info.signature")));
        JTextField signature = new JTextField(classNode.signature);
        rights.add(signature);
        labels.add(new JLabel(LangUtil.i18n("class.info.superName")));
        JTextField superName = new JTextField(classNode.superName);
        rights.add(superName);
        labels.add(new JLabel(LangUtil.i18n("class.info.interfaces")));
        JTextField interfaces = new JTextField(StringUtils.join(classNode.interfaces, ";"));
        rights.add(interfaces);
        labels.add(new JLabel(LangUtil.i18n("class.info.outerClass")));
        JTextField outerClass = new JTextField(classNode.outerClass);
        rights.add(outerClass);
        labels.add(new JLabel(LangUtil.i18n("class.info.outerMethod")));
        JTextField outerMethod = new JTextField(classNode.outerMethod);
        rights.add(outerMethod);
        labels.add(new JLabel(LangUtil.i18n("class.info.outerMethodDescription")));
        JTextField outerMethodDesc = new JTextField(classNode.outerMethodDesc);
        rights.add(outerMethodDesc);
        add(new JButton(LangUtil.i18n("button.save")) {{
            addActionListener(e -> {

                if (!StringUtil.isBlank(name.getText())) {
                    classNode.name = name.getText();
                }

                if (!StringUtil.isBlank(sourceFile.getText())) {
                    classNode.sourceFile = sourceFile.getText();
                } else {
                    classNode.sourceFile = null;
                }

                if (!StringUtil.isBlank(sourceDebug.getText())) {
                    classNode.sourceDebug = sourceDebug.getText();
                } else {
                    classNode.sourceDebug = null;
                }

                if (!StringUtil.isBlank(access.getText())) {
                    classNode.access = Integer.parseInt(access.getText());
                }

                if (!StringUtil.isBlank(version.getText())) {
                    classNode.version = Integer.parseInt(version.getText());
                }

                if (!StringUtil.isBlank(signature.getText())) {
                    classNode.signature = signature.getText();
                } else {
                    classNode.signature = null;
                }

                if (!StringUtil.isBlank(interfaces.getText())) {
                    classNode.interfaces = Arrays.asList(superName.getText().split(";"));
                } else {
                    classNode.interfaces = new ArrayList<>();
                }

                if (!StringUtil.isBlank(outerClass.getText())) {
                    classNode.outerClass = outerClass.getText();
                } else {
                    classNode.outerClass = null;
                }

                if (!StringUtil.isBlank(outerMethod.getText())) {
                    classNode.outerMethod = outerMethod.getText();
                } else {
                    classNode.outerClass = null;
                }

                if (!StringUtil.isBlank(outerMethodDesc.getText())) {
                    classNode.outerMethodDesc = outerMethodDesc.getText();
                } else {
                    classNode.outerClass = null;
                }

                JOptionPane.showMessageDialog(ClassInfoTabPanel.this,  LangUtil.i18n("success"));
            });
        }}, BorderLayout.SOUTH);
        labels.add(new JLabel("Visible Annotation:"));
        rights.add(new JButton(LangUtil.i18n("button.edit")) {{
            addActionListener(e -> {
                if (classNode.visibleAnnotations != null) {
                    new AnnotationListDialog(classNode.visibleAnnotations).setVisible(true);
                }
            });
        }});
        labels.add(new JLabel("Invisible Annotation:"));
        rights.add(new JButton(LangUtil.i18n("button.edit")) {{
            addActionListener(e -> {
                if (classNode.invisibleAnnotations != null) {
                    new AnnotationListDialog(classNode.invisibleAnnotations).setVisible(true);
                }
            });
        }});
    }
}
