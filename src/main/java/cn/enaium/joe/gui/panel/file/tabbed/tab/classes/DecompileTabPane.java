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
import cn.enaium.joe.MainFX;
import cn.enaium.joe.gui.component.CodeEditor;
import cn.enaium.joe.gui.panel.CodeAreaPanel;
import cn.enaium.joe.task.DecompileTask;
import cn.enaium.joe.util.StyleUtil;
import javafx.application.Platform;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.objectweb.asm.tree.ClassNode;

import java.awt.*;

/**
 * @author Enaium
 */
public class DecompileTabPane extends ClassNodeTabPane {
    public DecompileTabPane(ClassNode classNode) {
        super(classNode);
        CodeEditor codeArea = new CodeEditor(CodeEditor.Language.Type.JAVA);
        codeArea.setEditable(false);
        MainFX.getInstance().task.submit(new DecompileTask(classNode)).thenAccept(it -> {
            Platform.runLater(() -> {
                codeArea.replaceText(it);
            });
        });
        setCenter(new VirtualizedScrollPane<>(codeArea));
    }
}
