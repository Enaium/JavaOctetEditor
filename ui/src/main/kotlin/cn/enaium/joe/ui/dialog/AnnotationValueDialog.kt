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

import cn.enaium.joe.core.util.ListUtil
import cn.enaium.joe.core.wrapper.Wrapper
import cn.enaium.joe.ui.pane.confirm.ListValueEditPane
import cn.enaium.joe.ui.pane.confirm.ValueEditPane
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
@Suppress("UNCHECKED_CAST")
class AnnotationValueDialog(anyList: MutableList<Any>) : Dialog(i18n("annotation.title")) {
    init {
        content = TableView<Item>().apply {

            isEditable = false

            val description = TableColumn<Item, String>(i18n("annotation.description"))
            description.isResizable = false
            description.cellValueFactory = PropertyValueFactory("description")
            description.prefWidthProperty().bind(widthProperty().multiply(.5))

            val value = TableColumn<Item, Boolean>(i18n("annotation.value"))
            value.isResizable = false
            value.cellValueFactory = PropertyValueFactory("value")
            value.prefWidthProperty().bind(widthProperty().multiply(.5))
            columns.addAll(description, value)
            anyList.indices.forEach { i ->
                if (i % 2 == 1) {
                    items.add(Item(anyList[i - 1].toString(), anyList[i]))
                }
            }

            contextMenu = ContextMenu().apply {
                items.add(MenuItem(i18n("button.edit")).apply {
                    setOnAction {
                        val selectedItem = selectionModel.selectedItem
                        selectedItem?.let {
                            if (selectedItem.value is List<*>) {
                                if (ListUtil.getType(selectedItem.value) == AnnotationNode::class.java) {
                                    AnnotationDialog(selectedItem.value.map { it as AnnotationNode }).show()
                                } else {
                                    ConfirmDialog(ListValueEditPane(selectedItem.value as MutableList<Any>)).show()
                                }
                            } else {
                                val index: Int = (selectionModel.selectedIndex + 1) * 2 - 1

                                if (selectedItem.value.javaClass.isArray) {
                                    val newList = mutableListOf(selectedItem.value)
                                    ConfirmDialog(ListValueEditPane(newList)).apply {
                                        confirm = {
                                            anyList[index] = newList
                                        }
                                    }.show()
                                } else {
                                    val wrapper = Wrapper(selectedItem.value)
                                    ConfirmDialog(ValueEditPane(wrapper)).apply {
                                        confirm = {
                                            anyList[index] = wrapper.wrapper
                                        }
                                    }.show()
                                }
                            }
                        }
                    }
                })
            }
        }
    }

    class Item(val description: String, val value: Any)
}