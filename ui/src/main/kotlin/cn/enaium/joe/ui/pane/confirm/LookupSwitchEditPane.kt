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

package cn.enaium.joe.ui.pane.confirm

import cn.enaium.joe.core.wrapper.LabelNodeWrapper
import cn.enaium.joe.ui.control.LabelNodeComboBox
import cn.enaium.joe.ui.dialog.ConfirmDialog
import cn.enaium.joe.ui.util.i18n
import javafx.scene.control.Button
import javafx.scene.control.Spinner
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.HBox
import org.objectweb.asm.tree.LabelNode
import org.tbee.javafx.scene.layout.MigPane

/**
 * @author Enaium
 */
class LookupSwitchEditPane(keys: MutableList<Int>, labels: MutableList<LabelNode>) : ConfirmPane() {
    init {
        val tableView = TableView<Item>().apply {
            isEditable = false
            val key = TableColumn<Item, String>(i18n("instruction.lookSwitch.keys"))
            key.isResizable = false
            key.cellValueFactory = PropertyValueFactory("key")
            key.prefWidthProperty().bind(widthProperty().multiply(.5))

            val label = TableColumn<Item, Boolean>(i18n("instruction.lookSwitch.labels"))
            label.isResizable = false
            label.cellValueFactory = PropertyValueFactory("labelNodeWrapper")
            label.prefWidthProperty().bind(widthProperty().multiply(.5))

            labels.indices.forEach {
                items.add(Item(keys[it], LabelNodeWrapper(labels[it])))
            }
            columns.addAll(key, label)
        }
        center = tableView
        bottom = HBox().apply {
            children.add(Button(i18n("button.add")).apply {
                setOnAction {
                    ConfirmDialog(ConfirmPane().apply {
                        center = MigPane("fillx", "[fill][fill]").apply {
                            val key = Spinner<Int>()
                            val label = LabelNodeComboBox(labels[0])
                            add(key)
                            add(label)
                            confirm = {
                                label.value?.let {
                                    tableView.items.add(Item(key.value, label.value))
                                }
                            }
                        }
                    }).show()
                }
            })
            children.add(Button(i18n("button.remove")).apply {
                setOnAction {
                    tableView.selectionModel.selectedItem?.let {
                        tableView.items.removeAt(tableView.selectionModel.selectedIndex)
                    }
                }
            })
        }
        confirm = {
            keys.clear()
            labels.clear()
            tableView.items.forEach {
                keys.add(it.key)
                labels.add(it.labelNodeWrapper.wrapper)
            }
        }
    }

    class Item(val key: Int, val labelNodeWrapper: LabelNodeWrapper)
}