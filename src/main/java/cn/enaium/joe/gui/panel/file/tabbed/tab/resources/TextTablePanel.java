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

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.config.extend.KeymapConfig;
import cn.enaium.joe.gui.panel.BorderPanel;
import cn.enaium.joe.gui.panel.CodeAreaPanel;
import cn.enaium.joe.gui.panel.file.tree.node.FileTreeNode;
import cn.enaium.joe.util.KeyStrokeUtil;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.MessageUtil;
import org.fife.ui.rsyntaxtextarea.FileTypeUtil;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class TextTablePanel extends BorderPanel {
    public TextTablePanel(FileTreeNode fileTreeNode) {
        setCenter(new CodeAreaPanel() {{
            String name = fileTreeNode.toString();
            String syntax = SyntaxConstants.SYNTAX_STYLE_NONE;
            for (Map.Entry<String, List<String>> stringListEntry : FileTypeUtil.get().getDefaultContentTypeToFilterMap().entrySet()) {
                for (String s : stringListEntry.getValue()) {
                    if (name.matches("." + s)) {
                        syntax = stringListEntry.getKey();
                        break;
                    }
                }
            }
            getTextArea().setSyntaxEditingStyle(syntax);
            getTextArea().setText(new String(fileTreeNode.getData(), StandardCharsets.UTF_8));

            KeyStrokeUtil.register(getTextArea(), JavaOctetEditor.getInstance().config.getByClass(KeymapConfig.class).save.getValue(), () -> {
                fileTreeNode.setData(getTextArea().getText().getBytes(StandardCharsets.UTF_8));
                MessageUtil.info(LangUtil.i18n("success"));
            });
        }});
    }
}
