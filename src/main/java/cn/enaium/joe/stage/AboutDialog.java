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

package cn.enaium.joe.stage;

import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.ReflectUtil;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import javax.tools.ToolProvider;

/**
 * @author Enaium
 * @since 1.3.0
 */
public class AboutDialog extends Dialog {
    public AboutDialog() {
        super(LangUtil.i18n("menu.help.about"), new GridPane() {{
            setAlignment(Pos.CENTER);

            int row = 0;
            addRow(row++, new Label(LangUtil.i18n("about.system")));
            addRow(row++, new Label(LangUtil.i18n("about.system.description")));
            addRow(row++, new Label(LangUtil.i18n("about.system.name")), new Label(System.getProperty("os.name")));
            addRow(row++, new Label(LangUtil.i18n("about.system.architecture")), new Label(System.getProperty("os.arch")));
            addRow(row++, new Separator());
            addRow(row++, new Label(LangUtil.i18n("about.java")));
            addRow(row++, new Label(LangUtil.i18n("about.java.description")));
            addRow(row++, new Label(LangUtil.i18n("about.java.version")), new Label(System.getProperty("java.version")));
            addRow(row++, new Label(LangUtil.i18n("about.java.vm.name")), new Label(System.getProperty("java.vm.name")));
            addRow(row++, new Label(LangUtil.i18n("about.java.vm.vendor")), new Label(System.getProperty("java.vm.vendor")));
            addRow(row++, new Label(LangUtil.i18n("about.java.home")), new Label(System.getProperty("java.home")));
            addRow(row++, new Label(LangUtil.i18n("about.java.supportCompiler")), new Label(String.valueOf(ToolProvider.getSystemJavaCompiler() != null)));
            addRow(row++, new Label(LangUtil.i18n("about.java.supportAttach")), new Label(String.valueOf(ReflectUtil.classHas("com.sun.tools.attach.VirtualMachine"))));
        }});
    }
}
