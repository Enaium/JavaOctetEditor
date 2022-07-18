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
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.ASyncUtil;
import cn.enaium.joe.util.CfrUtil;
import cn.enaium.joe.util.JFileChooserUtil;
import org.objectweb.asm.tree.ClassNode;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Enaium
 */
public class SaveAllSourceMenuItem extends JMenuItem {
    public SaveAllSourceMenuItem() {
        super("Save All Sources");
        addActionListener(e -> {
            Jar jar = JavaOctetEditor.getInstance().jar;
            if (jar == null) {
                return;
            }

            File show = JFileChooserUtil.show(JFileChooserUtil.Type.SAVE);
            if (show != null) {
                ASyncUtil.execute(() -> {
                    float loaded = 0;
                    float files = jar.classes.size() + jar.resources.size();
                    try {
                        ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(show.toPath()));

                        for (Map.Entry<String, ClassNode> stringClassNodeEntry : jar.classes.entrySet()) {
                            String name = stringClassNodeEntry.getKey().substring(0, stringClassNodeEntry.getKey().lastIndexOf(".")) + ".java";
                            name = "src/main/java/" + name;
                            zipOutputStream.putNextEntry(new ZipEntry(name));
                            zipOutputStream.write(CfrUtil.getSource(stringClassNodeEntry.getValue()).getBytes(StandardCharsets.UTF_8));
                            JavaOctetEditor.getInstance().bottomPanel.setProcess((int) ((loaded++ / files) * 100f));
                        }

                        for (Map.Entry<String, byte[]> stringEntry : jar.resources.entrySet()) {
                            String name = stringEntry.getKey();
                            name = "src/main/resources/" + name;
                            zipOutputStream.putNextEntry(new JarEntry(name));
                            zipOutputStream.write(stringEntry.getValue());
                            JavaOctetEditor.getInstance().bottomPanel.setProcess((int) ((loaded++ / files) * 100f));
                        }
                        zipOutputStream.closeEntry();
                        zipOutputStream.close();
                        JavaOctetEditor.getInstance().bottomPanel.setProcess(0);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

            }
        });
    }
}