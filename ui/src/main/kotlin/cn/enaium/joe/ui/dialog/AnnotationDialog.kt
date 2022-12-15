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

package cn.enaium.joe.ui.dialog

import cn.enaium.joe.ui.util.i18n
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import org.objectweb.asm.tree.AnnotationNode


/**
 * @author Enaium
 * @since 2.0.0
 */
class AnnotationDialog(annotationList: List<AnnotationNode>) : Dialog(i18n("annotation.title")) {
    init {
        content = TableView<Item>().apply {
            isEditable = false

            val description = TableColumn<Item, String>(i18n("annotation.description"))
            description.isResizable = false
            description.cellValueFactory = PropertyValueFactory("description")
            description.prefWidthProperty().bind(widthProperty().multiply(.5))

            val hasValue = TableColumn<Item, Boolean>(i18n("annotation.hasValue"))
            hasValue.isResizable = false
            hasValue.cellValueFactory = PropertyValueFactory("hasValue")
            hasValue.prefWidthProperty().bind(widthProperty().multiply(.5))

            annotationList.forEach {
                items.add(Item(it))
            }
            columns.addAll(description, hasValue)
            contextMenu = ContextMenu().apply {
                items.add(MenuItem(i18n("button.edit")).apply {
                    setOnAction {
                        if (selectionModel.selectedItem != null && selectionModel.selectedItem.hasValue) {
                            AnnotationValueDialog(selectionModel.selectedItem.annotationNode.values).show()
                        }
                    }
                })
            }
        }
    }

    @Suppress("JoinDeclarationAndAssignment")
    class Item(val annotationNode: AnnotationNode) {
        val description: String
        val hasValue: Boolean

        init {
            description = annotationNode.desc
            hasValue = annotationNode.values != null
        }
    }
}