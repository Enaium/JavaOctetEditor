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

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.event.Event;
import cn.enaium.joe.Instance;
import cn.enaium.joe.util.LangUtil;
import org.objectweb.asm.tree.ClassNode;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * @author Enaium
 */
public class ClassTabPanel extends JPanel {

    public final ClassNode classNode;

    public ClassTabPanel(ClassNode classNode) {
        super(new BorderLayout());
        this.classNode = classNode;
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        jTabbedPane.addTab(LangUtil.i18n("class.tab.bytecodeView"), new TraceBytecodeTabPanel(classNode));
        jTabbedPane.addTab(LangUtil.i18n("class.tab.decompileEdit"), new DecompileTabPanel(classNode));
        jTabbedPane.addTab(LangUtil.i18n("class.tab.visitorEdit"), new ASMifierTablePanel(classNode));
        jTabbedPane.addTab(LangUtil.i18n("class.tab.infoEdit"), new ClassInfoTabPanel(classNode));
        jTabbedPane.setSelectedIndex(Instance.INSTANCE.classTabIndex);
        jTabbedPane.addChangeListener(e -> JavaOctetEditor.getInstance().event.call(new Change(Instance.INSTANCE.classTabIndex = jTabbedPane.getSelectedIndex())));
        JavaOctetEditor.getInstance().event.register(Change.class, (Consumer<Change>) event -> jTabbedPane.setSelectedIndex(event.index));
        add(jTabbedPane);
    }

    private static class Change implements Event {
        private final int index;

        public Change(int index) {
            this.index = index;
        }
    }
}