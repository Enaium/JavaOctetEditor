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

package cn.enaium.joe.gui.panel.file.tabbed;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.event.events.FileTabbedSelectEvent;
import cn.enaium.joe.util.JMenuUtil;
import cn.enaium.joe.util.LangUtil;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

/**
 * @author Enaium
 */
public class FileTabbedPanel extends JTabbedPane {
    public FileTabbedPanel() {
        setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
        putClientProperty("JTabbedPane.tabClosable", true);
        putClientProperty("JTabbedPane.tabCloseCallback", (BiConsumer<JTabbedPane, Integer>) JTabbedPane::remove);
        addChangeListener(e -> {
            Component selectedComponent = ((FileTabbedPanel) e.getSource()).getSelectedComponent();
            JavaOctetEditor.getInstance().event.call(new FileTabbedSelectEvent(selectedComponent));
        });
        JMenuUtil.addPopupMenu(this, () -> new JPopupMenu() {{
            add(new JMenuItem(LangUtil.i18n("popup.tabbed.closeAll")) {{
                addActionListener(e -> {
                    FileTabbedPanel.this.removeAll();
                });
            }});
            add(new JMenuItem(LangUtil.i18n("popup.tabbed.closeOther")) {{
                addActionListener(e -> {
                    int tabCount = getTabCount();
                    while (tabCount-- > 0) {
                        if (tabCount != getSelectedIndex()) {
                            removeTabAt(tabCount);
                        }
                    }
                });
            }});
            add(new JMenuItem(LangUtil.i18n("popup.tabbed.closeAllLeft")) {{
                addActionListener(e -> {
                    int tabCount = getTabCount();
                    while (tabCount-- > 0) {
                        if (tabCount < getSelectedIndex()) {
                            removeTabAt(tabCount);
                        }
                    }
                });
            }});
            add(new JMenuItem(LangUtil.i18n("popup.tabbed.closeAllRight")) {{
                addActionListener(e -> {
                    int tabCount = getTabCount();
                    while (tabCount-- > 0) {
                        if (tabCount > getSelectedIndex()) {
                            removeTabAt(tabCount);
                        }
                    }
                });
            }});
        }}, () -> getSelectedComponent() != null);
    }
}
