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

import cn.enaium.joe.gui.component.tree.FileTreeItem;
import cn.enaium.joe.gui.panel.file.tree.node.FileTreeNode;
import cn.enaium.joe.util.IOUtil;
import cn.enaium.joe.util.ImageUtil;
import cn.enaium.joe.util.MessageUtil;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class ImageTablePanel extends BorderPane {
    public ImageTablePanel(FileTreeItem fileTreeNode) {
        setCenter(new ScrollPane(new ImageView(ImageUtil.load(fileTreeNode.getData()))));
    }
}
