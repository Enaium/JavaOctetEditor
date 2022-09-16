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

package cn.enaium.joe.gui.panel.file.tabbed.tab.classes;

import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import org.objectweb.asm.tree.ClassNode;

/**
 * @author Enaium
 */
public class ClassTabPane extends BorderPane {

    public final ClassNode classNode;

    public ClassTabPane(ClassNode classNode) {
        this.classNode = classNode;
        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.BOTTOM);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().add(new Tab("BytecodeView", new TraceBytecodeTabPane(classNode)));
        tabPane.getTabs().add(new Tab("DecompileView", new DecompileTabPane(classNode)));
        tabPane.getTabs().add(new Tab("VisitorEdit", new ASMifierTablePane(classNode)));
        tabPane.getTabs().add(new Tab("InfoEdit", new ClassInfoTabPane(classNode)));
        setCenter(tabPane);
    }
}