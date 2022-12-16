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

package cn.enaium.joe.ui.pane.content.resources

import cn.enaium.joe.ui.control.tree.FileTreeItem
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import java.io.ByteArrayInputStream

/**
 * @author Enaium
 */
class ImagePane(fileTreeItem: FileTreeItem) : BorderPane() {
    init {
        center = ImageView(Image(ByteArrayInputStream(fileTreeItem.data)))
    }
}