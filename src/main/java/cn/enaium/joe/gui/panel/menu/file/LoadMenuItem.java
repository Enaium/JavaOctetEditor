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

package cn.enaium.joe.gui.panel.menu.file;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.task.InputJarTask;
import cn.enaium.joe.util.JFileChooserUtil;
import cn.enaium.joe.util.LangUtil;

import javax.swing.*;
import java.io.File;

/**
 * @author Enaium
 */
public class LoadMenuItem extends JMenuItem {
    public LoadMenuItem() {
        super(LangUtil.i18n("menu.file.load"));
        addActionListener(e -> {
            File show = JFileChooserUtil.show(JFileChooserUtil.Type.OPEN);
            if (show != null) {
                JavaOctetEditor.getInstance().task.submit(new InputJarTask(show));
            }
        });
    }
}
