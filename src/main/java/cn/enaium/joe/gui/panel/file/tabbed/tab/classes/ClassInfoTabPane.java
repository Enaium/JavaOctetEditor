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
import cn.enaium.joe.gui.panel.ColumnPane;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.StringUtil;
import javafx.geometry.Pos;
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
        setCenter(new ColumnPane() {{
            int row = 0;
            TextField name = new TextField(classNode.name);
            add(new Label(LangUtil.i18n("class.info.name")), name);
            TextField sourceFile = new TextField(classNode.sourceFile);
            add(new Label(LangUtil.i18n("class.info.sourceFile")), sourceFile);
            TextField sourceDebug = new TextField(classNode.sourceDebug);
            add(new Label(LangUtil.i18n("class.info.debugFile")), sourceDebug);
            TextField access = new TextField(String.valueOf(classNode.access));
            add(new Label(LangUtil.i18n("class.info.access")), access);
            TextField version = new TextField(String.valueOf(classNode.version));
            add(new Label(LangUtil.i18n("class.info.version")), version);
            TextField signature = new TextField(classNode.signature);
            add(new Label(LangUtil.i18n("class.info.signature")), signature);
            TextField superName = new TextField(classNode.superName);
            add(new Label(LangUtil.i18n("class.info.superName")), superName);
            TextField interfaces = new TextField(StringUtils.join(classNode.interfaces, ";"));
            add(new Label(LangUtil.i18n("class.info.interfaces")), interfaces);
            TextField outerClass = new TextField(classNode.outerClass);
            add(new Label(LangUtil.i18n("class.info.outerClass")), outerClass);
            TextField outerMethod = new TextField(classNode.outerMethod);
            add(new Label(LangUtil.i18n("class.info.outerMethod")), outerMethod);
            TextField outerMethodDesc = new TextField(classNode.outerMethodDesc);
            add(new Label(LangUtil.i18n("class.info.outerMethodDescription")), outerMethodDesc);
            add(new Label("Visible Annotation:"), new Button(LangUtil.i18n("button.edit")) {{
                setOnAction(e -> {
                    if (classNode.visibleAnnotations != null) {
                        new AnnotationListDialog(classNode.visibleAnnotations).setVisible(true);
                    }
                });
            }});
            add(new Label("Invisible Annotation:"), new Button(LangUtil.i18n("button.edit")) {{
                setOnAction(e -> {
                    if (classNode.invisibleAnnotations != null) {
                        new AnnotationListDialog(classNode.invisibleAnnotations).setVisible(true);
                    }
                });
            }});
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
        }});
    }
}
