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

package cn.enaium.joe.gui.panel;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.event.Event;
import cn.enaium.joe.gui.component.TabbedPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Enaium
 */
public class LeftPanel extends JPanel {
    public LeftPanel() {
        super(new BorderLayout());
        add(new JPanel(new BorderLayout()) {{
            add(new TabbedPane(JTabbedPane.LEFT) {{
                addTab("Project", JavaOctetEditor.getInstance().fileTree);
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JavaOctetEditor.getInstance().event.call(new ToggleTabEvent(getSelectedComponent(), ToggleTabEvent.Type.TOP));
                    }
                });
                setVerticalLabel();
            }}, BorderLayout.NORTH);
            add(new TabbedPane(JTabbedPane.LEFT) {{
//                addTab("Member", new MemberList(new ClassNode()) {{
//                    JavaOctetEditor.getInstance().event.register(FileTabbedSelectListener.class, (Consumer<FileTabbedSelectListener>) listener -> {
//                        Component select = listener.getSelect();
//                        if (select instanceof ClassTabPane) {
//                            setModel(new MemberList(((ClassTabPane) select).getClassNode()).getModel());
//                        }
//                    });
//                }});
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JavaOctetEditor.getInstance().event.call(new ToggleTabEvent(getSelectedComponent(), ToggleTabEvent.Type.BOTTOM));
                    }
                });
                cancelSelect();
                setVerticalLabel();
            }}, BorderLayout.SOUTH);
        }}, BorderLayout.WEST);
    }

    public static class ToggleTabEvent implements Event {
        private final Component select;
        private final Type type;

        public ToggleTabEvent(Component select, Type type) {
            this.select = select;
            this.type = type;
        }

        public Component getSelect() {
            return select;
        }

        public Type getType() {
            return type;
        }

        public enum Type {
            TOP, BOTTOM
        }
    }
}
