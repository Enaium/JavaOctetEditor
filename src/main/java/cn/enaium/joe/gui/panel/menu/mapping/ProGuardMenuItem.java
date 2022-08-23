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

package cn.enaium.joe.gui.panel.menu.mapping;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.task.RemappingTask;
import cn.enaium.joe.util.JFileChooserUtil;
import net.fabricmc.mappingio.format.MappingFormat;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @author Enaium
 * @since 1.0.0
 */
public class ProGuardMenuItem extends JMenuItem {
    public ProGuardMenuItem() {
        super("ProGuard");
        addActionListener(e -> {
            File show = JFileChooserUtil.show(JFileChooserUtil.Type.OPEN, new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().endsWith(".txt") || f.isDirectory();
                }

                @Override
                public String getDescription() {
                    return "Text file(*.text)";
                }
            });
            if (JavaOctetEditor.getInstance().getJar() != null) {
                JavaOctetEditor.getInstance().task.submit(new RemappingTask(show, MappingFormat.PROGUARD));
            }
        });
    }
}
