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

import cn.enaium.joe.core.util.OpcodeUtil
import cn.enaium.joe.ui.dialog.ConfirmDialog
import cn.enaium.joe.ui.util.i18n
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import org.objectweb.asm.tree.FrameNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class FrameListEditPane(frameNode: FrameNode) : ConfirmPane() {
    init {
        center = SplitPane().apply {
            items.add(BorderPane().apply {
                top = Label(i18n("instruction.local"))
                center = AnyList(frameNode.local)
            })
            items.add(BorderPane().apply {
                top = Label(i18n("instruction.stack"))
                center = AnyList(frameNode.stack)
            })
        }
        confirm = {
            // TODO: optimize
        }
    }

    private class AnyList(anyList: MutableList<Any>) : BorderPane() {
        init {
            val list = ListView<String>().apply {
                anyList.forEach {
                    if (it is String) {
                        items.add(it)
                    } else if (it is Int) {
                        items.add(OpcodeUtil.FRAME_ELEMENT[it])
                    }
                }
            }
            center = list
            bottom = HBox().apply {
                children.add(Button("instruction.addString").apply {
                    setOnAction {
                        val string = TextInputDialog().showAndWait()
                        if (string.get().isNotBlank()) {
                            anyList.add(string.get())
                            list.items.add(string.get())
                        }
                    }
                })
                children.add(Button("instruction.addType").apply {
                    setOnAction {
                        ConfirmDialog(ConfirmPane().apply {
                            val type = ComboBox<String>().apply {
                                items.addAll(OpcodeUtil.FRAME_ELEMENT.values)
                            }
                            center = type
                            confirm = {
                                type.selectionModel.selectedItem?.let {
                                    anyList.add(type.selectionModel.selectedItem)
                                    list.items.add(type.selectionModel.selectedItem)
                                }
                            }
                        }).show()
                    }
                })
                children.add(Button(i18n("button.remove")).apply {
                    setOnAction {
                        list.selectionModel.selectedItem?.let {
                            anyList.removeAt(list.selectionModel.selectedIndex)
                            list.items.removeAt(list.selectionModel.selectedIndex)
                        }
                    }
                })
            }
        }
    }
}