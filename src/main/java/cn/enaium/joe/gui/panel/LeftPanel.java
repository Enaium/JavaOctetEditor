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
import cn.enaium.joe.dialog.FieldDialog;
import cn.enaium.joe.dialog.MethodDialog;
import cn.enaium.joe.event.Listener;
import cn.enaium.joe.event.listener.FileTabbedSelectListener;
import cn.enaium.joe.gui.layout.HalfLayout;
import cn.enaium.joe.gui.panel.file.tabbed.tab.classes.ClassTabPanel;
import cn.enaium.joe.gui.panel.file.tree.FileTreePanel;
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.JTreeUtil;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.OpcodeUtil;
import cn.enaium.joe.util.Pair;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Enaium
 */
public class LeftPanel extends JPanel {
    public LeftPanel() {
        super(new BorderLayout());
        add(new JPanel(new BorderLayout()) {{
            add(new JPanel(new BorderLayout()) {{
                add(new JToggleButton("M") {{
                    addActionListener(e -> {
                        JavaOctetEditor.getInstance().event.call(new BottomToggleButtonListener(isSelected(), BottomToggleButtonListener.Type.MEMBER));
                    });
                }}, BorderLayout.SOUTH);
            }}, BorderLayout.WEST);
        }}, BorderLayout.CENTER);
    }

    public static class BottomToggleButtonListener implements Listener {
        private final boolean select;

        private final Type type;

        public BottomToggleButtonListener(boolean select, Type type) {
            this.select = select;
            this.type = type;
        }

        public boolean isSelect() {
            return select;
        }

        public Type getType() {
            return type;
        }

        public enum Type {
            MEMBER
        }
    }
}
