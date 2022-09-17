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
import cn.enaium.joe.gui.component.RightTabBar;
import cn.enaium.joe.gui.component.TabbedPane;
import cn.enaium.joe.gui.layout.HalfLayout;
import cn.enaium.joe.gui.panel.BorderPanel;
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
public class CenterPanel extends BorderPanel {
    public CenterPanel() {
        setCenter(new JSplitPane() {{
            setLeftComponent(new LeftPanel());
            setRightComponent(JavaOctetEditor.getInstance().fileTabbedPanel);
            setDividerLocation(200);
        }});
        setRight(new BorderPanel() {{
            JScrollPane comp = new JScrollPane();
            setCenter(comp);
            setRight(new RightTabBar() {{
                addChangeListener(e -> {
                    comp.setViewportView(getSelectedComponent());
                });
            }});
        }});
    }
}
