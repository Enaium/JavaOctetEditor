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
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.BorderPane
import kotlin.math.ceil

/**
 * @author Enaium
 * @since 2.0.0
 */
class HexPane(fileTreeItem: FileTreeItem) : BorderPane() {
    init {
        val tableView = TableView<Int>()
        for (i in 0..15) {
            val element = TableColumn<Int, String>("%X".format(i))
            element.isReorderable = false
            element.setCellValueFactory { cell ->
                val x = i
                val y = cell.value
                try {
                    SimpleStringProperty("%02X".format(fileTreeItem.data[x + y * 16]))
                } catch (_: ArrayIndexOutOfBoundsException) {
                    SimpleStringProperty("")
                }
            }
            element.prefWidthProperty().bind(widthProperty().multiply(1.0 / 16.0))
            tableView.columns.add(element)
        }

        for (i in 0 until ceil(fileTreeItem.data.size / 16.0).toInt()) {
            tableView.items.add(i)
        }


        center = tableView
    }
}