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

import cn.enaium.joe.gui.panel.CodeAreaPanel;
import cn.enaium.joe.gui.panel.file.tree.node.FileTreeNode;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class TextTablePanel extends JPanel {
    public TextTablePanel(FileTreeNode fileTreeNode) {
        super(new BorderLayout());
        add(new CodeAreaPanel() {{
            String name = fileTreeNode.toString();
            String syntax = SyntaxConstants.SYNTAX_STYLE_NONE;
            if (name.matches(".*(xml|svg)")) {
                syntax = SyntaxConstants.SYNTAX_STYLE_XML;
            } else if (name.matches(".*(md|markdown)")) {
                syntax = SyntaxConstants.SYNTAX_STYLE_MARKDOWN;
            } else if (name.matches(".*(properties)")) {
                syntax = SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE;
            } else if (name.matches(".*(yml)")) {
                syntax = SyntaxConstants.SYNTAX_STYLE_YAML;
            } else if (name.matches(".*(html)")) {
                syntax = SyntaxConstants.SYNTAX_STYLE_YAML;
            } else if (name.matches(".*(css)")) {
                syntax = SyntaxConstants.SYNTAX_STYLE_YAML;
            } else if (name.matches(".*(js)")) {
                syntax = SyntaxConstants.SYNTAX_STYLE_JSON;
            } else if (name.matches(".*(json)")) {
                syntax = SyntaxConstants.SYNTAX_STYLE_JSON;
            }
            getTextArea().setSyntaxEditingStyle(syntax);
            getTextArea().setText(new String(fileTreeNode.getData(), StandardCharsets.UTF_8));
        }});
    }
}
