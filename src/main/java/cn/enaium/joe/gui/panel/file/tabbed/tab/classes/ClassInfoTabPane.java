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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
public class ClassInfoTabPane extends ClassNodeTabPane {
    public ClassInfoTabPane(ClassNode classNode) {
        super(classNode);
        GridPane labels = new GridPane();
        GridPane rights = new GridPane();
        setLeft(labels);
        setRight(rights);
        labels.addRow(0, new Label(LangUtil.i18n("class.info.name")));
        TextField name = new TextField(classNode.name);
        rights.addRow(0, name);
        labels.addRow(1, new Label(LangUtil.i18n("class.info.sourceFile")));
        TextField sourceFile = new TextField(classNode.sourceFile);
        rights.addRow(1, sourceFile);
        labels.addRow(2, new Label(LangUtil.i18n("class.info.debugFile")));
        TextField sourceDebug = new TextField(classNode.sourceDebug);
        rights.addRow(2, sourceDebug);
        labels.addRow(3, new Label(LangUtil.i18n("class.info.access")));
        TextField access = new TextField(String.valueOf(classNode.access));
        rights.addRow(3, access);
        labels.addRow(4, new Label(LangUtil.i18n("class.info.version")));
        TextField version = new TextField(String.valueOf(classNode.version));
        rights.addRow(4, version);
        labels.addRow(5, new Label(LangUtil.i18n("class.info.signature")));
        TextField signature = new TextField(classNode.signature);
        rights.addRow(5, signature);
        labels.addRow(6, new Label(LangUtil.i18n("class.info.superName")));
        TextField superName = new TextField(classNode.superName);
        rights.addRow(6, superName);
        labels.addRow(7, new Label(LangUtil.i18n("class.info.interfaces")));
        TextField interfaces = new TextField(StringUtils.join(classNode.interfaces, ";"));
        rights.addRow(7, interfaces);
        labels.addRow(8, new Label(LangUtil.i18n("class.info.outerClass")));
        TextField outerClass = new TextField(classNode.outerClass);
        rights.addRow(8, outerClass);
        labels.addRow(9, new Label(LangUtil.i18n("class.info.outerMethod")));
        TextField outerMethod = new TextField(classNode.outerMethod);
        rights.addRow(9, outerMethod);
        labels.addRow(10, new Label(LangUtil.i18n("class.info.outerMethodDescription")));
        TextField outerMethodDesc = new TextField(classNode.outerMethodDesc);
        rights.addRow(10, outerMethodDesc);
        setBottom(new Button(LangUtil.i18n("button.save")) {{
            setOnAction(e -> {

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

//                JOptionPane.showMessageDialog(ClassInfoTabPane.this,  LangUtil.i18n("success"));
            });
        }});
        labels.addRow(11, new Label("Visible Annotation:"));
        rights.addRow(11, new Button(LangUtil.i18n("button.edit")) {{
            setOnAction(e -> {
                if (classNode.visibleAnnotations != null) {
                    new AnnotationListDialog(classNode.visibleAnnotations).setVisible(true);
                }
            });
        }});
        labels.addRow(12, new Label("Invisible Annotation:"));
        rights.addRow(12, new Button(LangUtil.i18n("button.edit")) {{
            setOnAction(e -> {
                if (classNode.invisibleAnnotations != null) {
                    new AnnotationListDialog(classNode.invisibleAnnotations).setVisible(true);
                }
            });
        }});
    }
}
