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

import cn.enaium.joe.MainFX;
import cn.enaium.joe.event.Event;
import cn.enaium.joe.event.events.FileTabSelectEvent;
import cn.enaium.joe.gui.component.MainMenu;
import cn.enaium.joe.gui.component.MemberListFX;
import cn.enaium.joe.gui.panel.file.tabbed.tab.classes.ClassInfoTabPane;
import cn.enaium.joe.gui.panel.file.tabbed.tab.classes.ClassNodeTabPane;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import org.objectweb.asm.tree.ClassNode;

import java.util.function.Consumer;

/**
 * @author Enaium
 * @since 1.3.0
 */
public class CenterPanel extends BorderPane {
    public CenterPanel() {
        setTop(new MainMenu());
        setCenter(new SplitPane(MainFX.getInstance().fileTree, MainFX.getInstance().fileTab, new Accordion() {{
            getPanes().add(new TitledPane("Member", new MemberListFX()));
            setMinWidth(0);
        }}) {{
            setDividerPositions(0.2, 0.9);
        }});
    }
}
