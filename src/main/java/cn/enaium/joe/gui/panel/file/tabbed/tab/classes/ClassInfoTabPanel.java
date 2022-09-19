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
import net.miginfocom.swing.MigLayout;
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
        setLayout(new MigLayout("fillx", "[fill][fill]"));
        add(new JLabel(LangUtil.i18n("class.info.name")));
        JTextField name = new JTextField(classNode.name);
        add(name, "wrap");
        add(new JLabel(LangUtil.i18n("class.info.sourceFile")));
        JTextField sourceFile = new JTextField(classNode.sourceFile);
        add(sourceFile, "wrap");
        add(new JLabel(LangUtil.i18n("class.info.debugFile")));
        JTextField sourceDebug = new JTextField(classNode.sourceDebug);
        add(sourceDebug, "wrap");
        add(new JLabel(LangUtil.i18n("class.info.access")));
        JTextField access = new JTextField(String.valueOf(classNode.access));
        add(access, "wrap");
        add(new JLabel(LangUtil.i18n("class.info.version")));
        JTextField version = new JTextField(String.valueOf(classNode.version));
        add(version, "wrap");
        add(new JLabel(LangUtil.i18n("class.info.signature")));
        JTextField signature = new JTextField(classNode.signature);
        add(signature, "wrap");
        add(new JLabel(LangUtil.i18n("class.info.superName")));
        JTextField superName = new JTextField(classNode.superName);
        add(superName, "wrap");
        add(new JLabel(LangUtil.i18n("class.info.interfaces")));
        JTextField interfaces = new JTextField(StringUtils.join(classNode.interfaces, ";"));
        add(interfaces, "wrap");
        add(new JLabel(LangUtil.i18n("class.info.outerClass")));
        JTextField outerClass = new JTextField(classNode.outerClass);
        add(outerClass, "wrap");
        add(new JLabel(LangUtil.i18n("class.info.outerMethod")));
        JTextField outerMethod = new JTextField(classNode.outerMethod);
        add(outerMethod, "wrap");
        add(new JLabel(LangUtil.i18n("class.info.outerMethodDescription")));
        JTextField outerMethodDesc = new JTextField(classNode.outerMethodDesc);
        add(outerMethodDesc, "wrap");
        add(new JLabel("Visible Annotation:"));
        add(new JButton(LangUtil.i18n("button.edit")) {{
            addActionListener(e -> {
                if (classNode.visibleAnnotations != null) {
                    new AnnotationListDialog(classNode.visibleAnnotations).setVisible(true);
                }
            });
        }}, "wrap");
        add(new JLabel("Invisible Annotation:"));
        add(new JButton(LangUtil.i18n("button.edit")) {{
            addActionListener(e -> {
                if (classNode.invisibleAnnotations != null) {
                    new AnnotationListDialog(classNode.invisibleAnnotations).setVisible(true);
                }
            });
        }}, "wrap");
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

                JOptionPane.showMessageDialog(ClassInfoTabPanel.this, LangUtil.i18n("success"));
            });
        }}, "span 2");
    }
}
