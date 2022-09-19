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

import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.ReflectUtil;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.tools.ToolProvider;

/**
 * @author Enaium
 */
public class AboutDialog extends Dialog {
    public AboutDialog() {
        super(LangUtil.i18n("menu.help.about"));
        setContentPane(new JPanel(new MigLayout("fillx", "[fill][fill]")) {{
            setBorder(new EmptyBorder(10, 10, 10, 10));
            add(new JLabel(LangUtil.i18n("about.system")), "wrap");
            add(new JLabel(LangUtil.i18n("about.system.description")), "wrap");

            add(new JLabel(LangUtil.i18n("about.system.name")));
            add(new JLabel(System.getProperty("os.name")), "wrap");
            add(new JLabel(LangUtil.i18n("about.system.architecture")));
            add(new JLabel(System.getProperty("os.arch")), "wrap");

            add(new JSeparator(), "span 2");
            add(new JLabel(), "wrap");

            add(new JLabel(LangUtil.i18n("about.java")), "wrap");
            add(new JLabel(LangUtil.i18n("about.java.description")), "wrap");


            add(new JLabel(LangUtil.i18n("about.java.version")));
            add(new JLabel(System.getProperty("java.version")), "wrap");
            add(new JLabel(LangUtil.i18n("about.java.vm.name")));
            add(new JLabel(System.getProperty("java.vm.name")), "wrap");
            add(new JLabel(LangUtil.i18n("about.java.vm.vendor")));
            add(new JLabel(System.getProperty("java.vm.vendor")), "wrap");
            add(new JLabel(LangUtil.i18n("about.java.home")));
            add(new JLabel(System.getProperty("java.home")), "wrap");
            add(new JLabel(LangUtil.i18n("about.java.supportCompiler")));
            add(new JLabel(String.valueOf(ToolProvider.getSystemJavaCompiler() != null)), "wrap");
            add(new JLabel(LangUtil.i18n("about.java.supportAttach")));
            add(new JLabel(String.valueOf(ReflectUtil.classHas("com.sun.tools.attach.VirtualMachine"))), "wrap");
        }});
        setResizable(false);
        pack();
    }
}
