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

import cn.enaium.joe.gui.panel.CodeAreaPanel;
import cn.enaium.joe.util.ASyncUtil;
import cn.enaium.joe.util.CfrUtil;
import org.objectweb.asm.tree.ClassNode;

import java.awt.*;

/**
 * @author Enaium
 */
public class DecompileTabPanel extends ClassNodeTabPanel {
    public DecompileTabPanel(ClassNode classNode) {
        super(classNode);
        setLayout(new BorderLayout());
        CodeAreaPanel codeAreaPanel = new CodeAreaPanel();
        codeAreaPanel.getTextArea().setSyntaxEditingStyle("text/java");
        ASyncUtil.execute(() -> {
            codeAreaPanel.getTextArea().setText(CfrUtil.getSource(classNode));
        });
        codeAreaPanel.getTextArea().setCaretPosition(0);
        add(codeAreaPanel);
    }
}
