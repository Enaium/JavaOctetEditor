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

package cn.enaium.joe.gui;

import cn.enaium.joe.BytecodeTokenMaker;
import cn.enaium.joe.gui.panel.BottomPanel;
import cn.enaium.joe.gui.panel.file.tabbed.FileTabbedPanel;
import cn.enaium.joe.gui.panel.file.tree.FileTreePanel;
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.ASyncUtil;
import cn.enaium.joe.util.JFileChooserUtil;
import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Enaium
 */
public class JavaOctetEditor {
    private static JavaOctetEditor instance;

    public static final String TITLE = "JavaOctetEditor";

    public JFrame window = new JFrame(TITLE);

    public Jar jar;

    public FileTabbedPanel fileTabbedPanel = new FileTabbedPanel();

    public FileTreePanel fileTreePanel = new FileTreePanel();

    public BottomPanel bottomPanel = new BottomPanel();

    public JavaOctetEditor() {
        instance = this;
    }

    public void run() {
        AbstractTokenMakerFactory abstractTokenMakerFactory = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
        abstractTokenMakerFactory.putMapping("text/custom", BytecodeTokenMaker.class.getName());

        window.setJMenuBar(new JMenuBar() {{
            add(new JMenu("File") {
                {
                    add(new JMenuItem("Load...") {{
                        addActionListener(e -> {
                            File show = JFileChooserUtil.show(JFileChooserUtil.Type.OPEN);
                            if (show != null) {
                                ASyncUtil.execute(() -> fileTreePanel.refresh(new Jar(show)));
                            }
                        });
                    }});

                    add(new JMenuItem("Save...") {{
                        addActionListener(e -> {
                            File show = JFileChooserUtil.show(JFileChooserUtil.Type.SAVE);
                            if (show != null) {
                                ASyncUtil.execute(() -> {
                                    if (jar == null) {
                                        return;
                                    }

                                    float loaded = 0;
                                    float files = jar.classes.size() + jar.resources.size();

                                    try {
                                        ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(show.toPath()));
                                        for (Map.Entry<String, ClassNode> stringClassNodeEntry : jar.classes.entrySet()) {
                                            zipOutputStream.putNextEntry(new JarEntry(stringClassNodeEntry.getKey()));
                                            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                                            stringClassNodeEntry.getValue().accept(classWriter);
                                            zipOutputStream.write(classWriter.toByteArray());
                                            JavaOctetEditor.getInstance().bottomPanel.setProcess((int) ((loaded++ / files) * 100f));
                                        }

                                        for (Map.Entry<String, byte[]> stringEntry : jar.resources.entrySet()) {
                                            zipOutputStream.putNextEntry(new JarEntry(stringEntry.getKey()));
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
                    }});
                }
            });
        }});

        window.setContentPane(new JPanel(new BorderLayout()) {
            {
                add(new JSplitPane() {{
                    setDividerLocation(150);
                    setLeftComponent(new JScrollPane(fileTreePanel));
                    setRightComponent(fileTabbedPanel);
                }}, BorderLayout.CENTER);
                add(bottomPanel, BorderLayout.SOUTH);
            }
        });
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(700, 500);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static JavaOctetEditor getInstance() {
        return instance;
    }
}
