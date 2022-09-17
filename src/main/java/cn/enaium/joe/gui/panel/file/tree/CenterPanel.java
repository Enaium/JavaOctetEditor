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

package cn.enaium.joe.gui.panel.file.tree;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.event.Event;
import cn.enaium.joe.event.events.FileTabbedSelectEvent;
import cn.enaium.joe.gui.component.MemberList;
import cn.enaium.joe.gui.component.TabbedPane;
import cn.enaium.joe.gui.layout.HalfLayout;
import cn.enaium.joe.gui.panel.LeftPanel;
import cn.enaium.joe.gui.panel.file.tabbed.tab.classes.ClassTabPanel;
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.JTreeUtil;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.Pair;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class CenterPanel extends JPanel {
    public CenterPanel() {
        super(new BorderLayout());
        add(new JSplitPane() {{
            setLeftComponent(new LeftPanel());
            setRightComponent(new JPanel(new BorderLayout()) {{
                add(JavaOctetEditor.getInstance().fileTabbedPanel, BorderLayout.CENTER);
                add(new JPanel(new BorderLayout()) {{
                    JPanel self = this;
                    add(new TabbedPane(JTabbedPane.RIGHT) {{
                        addTab("Field&Method", new FlatSVGIcon("icons/structure.svg"), new MemberList() {{
                            JavaOctetEditor.getInstance().event.register(FileTabbedSelectEvent.class, (Consumer<FileTabbedSelectEvent>) event -> {
                                if (event.getSelect() instanceof ClassTabPanel) {
                                    ClassTabPanel select = (ClassTabPanel) event.getSelect();
                                    ClassNode classNode = select.classNode;
                                    setModel(new DefaultListModel<Pair<ClassNode, Object>>() {{
                                        for (FieldNode field : classNode.fields) {
                                            addElement(new Pair<>(classNode, field));
                                        }

                                        for (MethodNode method : classNode.methods) {
                                            addElement(new Pair<>(classNode, method));
                                        }
                                    }});
                                }
                            });
                        }});
                        JScrollPane comp = new JScrollPane();
                        self.add(comp, BorderLayout.CENTER);
                        addChangeListener(e -> {
                            comp.setViewportView(getSelectedComponent());
                        });
                        cancelSelect();
                        setVerticalLabel();
                    }}, BorderLayout.EAST);
                }}, BorderLayout.EAST);
            }});
            setDividerLocation(200);
        }}, BorderLayout.CENTER);
    }
}
