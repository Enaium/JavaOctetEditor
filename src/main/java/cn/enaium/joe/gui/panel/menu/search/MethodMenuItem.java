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

package cn.enaium.joe.gui.panel.menu.search;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.dialog.search.SearchFieldDialog;
import cn.enaium.joe.dialog.search.SearchMethodDialog;
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.LangUtil;

import javax.swing.*;

/**
 * @author Enaium
 * @since 0.5.0
 */
public class MethodMenuItem extends JMenuItem {
    public MethodMenuItem() {
        super(LangUtil.i18n("menu.search.method"));
        addActionListener(e -> {
            Jar jar = JavaOctetEditor.getInstance().getJar();
            if (jar == null) {
                return;
            }
            new SearchMethodDialog().setVisible(true);
        });
    }
}
