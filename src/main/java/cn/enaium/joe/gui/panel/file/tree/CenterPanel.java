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
import cn.enaium.joe.gui.component.RightTabBar;
import cn.enaium.joe.gui.panel.BorderPanel;
import cn.enaium.joe.gui.panel.LeftPanel;

import javax.swing.*;
import java.awt.*;

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
            JViewport jViewport = new JViewport();
            setCenter(jViewport);
            setRight(new RightTabBar() {{
                addChangeListener(e -> {
                    jViewport.setView(getSelectedComponent());
                });
            }});
        }});
    }
}
