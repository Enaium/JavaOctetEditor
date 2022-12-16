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

import cn.enaium.joe.core.wrapper.Wrapper
import cn.enaium.joe.ui.dialog.ConfirmDialog
import cn.enaium.joe.ui.util.i18n
import javafx.collections.FXCollections
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import org.objectweb.asm.Handle
import org.objectweb.asm.Type
import org.tbee.javafx.scene.layout.MigPane

/**
 * @author Enaium
 * @since 2.0.0
 */
class BootstrapMethodArgumentEditPane(anyArrayWrapper: Wrapper<Array<Any>>) : ConfirmPane() {
    init {
        val list = ListView<Any>().apply {
            items = FXCollections.observableArrayList<Any>().apply {
                anyArrayWrapper.wrapper.forEach {
                    add(it)
                }
            }
        }
        center = BorderPane().apply {
            center = list
            bottom = HBox().apply {
                children.add(Button(i18n("button.add")).apply {
                    setOnAction {
                        ConfirmDialog(ConfirmPane().apply {
                            center = MigPane("fillx", "[fill][fill]").apply {
                                add(Label(i18n("instruction.type")))
                                val type = ComboBox<String>().apply {
                                    items.addAll("String", "float", "double", "int", "long", "Class")
                                }
                                add(type, "wrap")
                                add(Label(i18n("instruction.var")))
                                val ldc = TextField()
                                add(ldc)
                                confirm = {
                                    type.value?.let {
                                        when (type.value) {
                                            "String" -> {
                                                list.items.add(ldc.text)
                                            }

                                            "float" -> {
                                                list.items.add(ldc.text.toFloat())
                                            }

                                            "double" -> {
                                                list.items.add(ldc.text.toDouble())
                                            }

                                            "int" -> {
                                                list.items.add(ldc.text.toInt())
                                            }

                                            "long" -> {
                                                list.items.add(ldc.text.toLong())
                                            }

                                            "Class" -> {
                                                list.items.add(Type.getType(ldc.text))
                                            }
                                        }
                                    }
                                }
                            }
                        }).show()
                    }
                })
                children.add(Button(i18n("instruction.addHandle")).apply {
                    setOnAction {
                        val handleWrapper = Wrapper(Handle(1, "", "", "", false))
                        ConfirmDialog(HandleEditPane(handleWrapper)).apply {
                            confirm = {
                                list.items.add(handleWrapper.wrapper)
                            }
                        }.show()
                    }
                })
                children.add(Button(i18n("button.remove")).apply {
                    setOnAction {
                        list.selectionModel.selectedItem?.let {
                            list.items.remove(list.selectionModel.selectedItem)
                        }
                    }
                })
            }
        }

        confirm = {
            anyArrayWrapper.wrapper = list.items.toTypedArray()
        }
    }
}