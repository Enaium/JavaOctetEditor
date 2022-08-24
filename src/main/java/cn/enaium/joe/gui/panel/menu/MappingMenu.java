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

package cn.enaium.joe.gui.panel.menu;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.task.RemappingTask;
import cn.enaium.joe.util.JFileChooserUtil;
import cn.enaium.joe.util.LangUtil;
import net.fabricmc.mappingio.format.MappingFormat;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @author Enaium
 * @since 1.0.0
 */
public class MappingMenu extends JMenu {
    public MappingMenu() {
        super(LangUtil.i18n("menu.mapping"));
        for (MappingFormat value : MappingFormat.values()) {
            add(new JMenuItem(value.name) {{
                addActionListener(e -> {
                    File show = JFileChooserUtil.show(JFileChooserUtil.Type.OPEN, new FileFilter() {
                        @Override
                        public boolean accept(File f) {
                            return f.getName().matches(".+(txt|map|mapping|pro|srg|tsrg|tiny|tiyv2)") || f.isDirectory();
                        }

                        @Override
                        public String getDescription() {
                            return "Mapping file(*.txt,*.map,*.mapping,*.pro,*.srg)";
                        }
                    });
                    if (JavaOctetEditor.getInstance().getJar() != null && show != null) {
                        JavaOctetEditor.getInstance().task.submit(new RemappingTask(show, value));
                    }
                });
            }});
        }
    }
}
