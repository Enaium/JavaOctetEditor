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

package cn.enaium.joe.gui.panel.file.tabbed.tab.resources;

import cn.enaium.joe.gui.component.CodeEditor;
import cn.enaium.joe.gui.component.tree.FileTreeItem;
import javafx.scene.layout.BorderPane;

import java.nio.charset.StandardCharsets;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class TextTablePane extends BorderPane {
    public TextTablePane(FileTreeItem fileTreeNode) {
        String name = fileTreeNode.toString();
        CodeEditor.Language.Type type = CodeEditor.Language.Type.UNKNOWN;

        if (name.matches(".*(java)")) {
            type = CodeEditor.Language.Type.JAVA;
        }

        setCenter(new CodeEditor(type) {{
            replaceText(new String(fileTreeNode.getData(), StandardCharsets.UTF_8));
        }});
    }
}
