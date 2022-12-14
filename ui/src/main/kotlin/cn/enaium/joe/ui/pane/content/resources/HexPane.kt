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
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.layout.BorderPane

/**
 * @author Enaium
 * @since 2.0.0
 */
class HexPane(fileTreeItem: FileTreeItem) : BorderPane() {
    init {
        val tableView = TableView<Any>()
        for (i in 0..15) {
            tableView.columns.add(TableColumn<Any, String>("%X".format(i)))
        }

        // TODO:
        center = tableView
    }
}