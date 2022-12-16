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

package cn.enaium.joe.ui.pane.instruction

import cn.enaium.joe.core.wrapper.Wrapper
import cn.enaium.joe.ui.dialog.ConfirmDialog
import cn.enaium.joe.ui.pane.confirm.HandleEditPane
import cn.enaium.joe.ui.util.i18n
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import org.objectweb.asm.Handle
import org.objectweb.asm.Type
import org.objectweb.asm.tree.LdcInsnNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class LdcInstructionPane(instruction: LdcInsnNode) : AbstractInstructionPane(instruction) {
    init {
        val type = ComboBox<String>().apply {
            items.addAll("String", "float", "double", "int", "long", "Class", "Handle")
        }
        add(Label(i18n("instruction.type")), type)
        var handle: Handle? = null
        when (instruction.cst) {
            is String -> {
                type.selectionModel.select("String")
            }

            is Float -> {
                type.selectionModel.select("float")
            }

            is Double -> {
                type.selectionModel.select("double")
            }

            is Int -> {
                type.selectionModel.select("int")
            }

            is Long -> {
                type.selectionModel.select("long")
            }

            is Type -> {
                type.selectionModel.select("Class")
            }

            is Handle -> {
                type.selectionModel.select("Handle")
                handle = instruction.cst as Handle
            }
        }

        handle?.let {
            add(Label(i18n("instruction.handle")), Button(i18n("button.edit")).apply {
                setOnAction {
                    val handleWrapper = Wrapper(handle)
                    ConfirmDialog(HandleEditPane(handleWrapper)).apply {
                        confirm = {
                            instruction.cst = handleWrapper.wrapper
                        }
                    }.show()
                }
            })
        } ?: let {
            val ldc = TextField(instruction.cst.toString())
            add(Label("instruction.var", ldc))
            confirm = {
                type.selectionModel.selectedItem?.let {
                    when (type.selectionModel.selectedItem) {
                        "String" -> {
                            instruction.cst = ldc.text
                        }

                        "float" -> {
                            instruction.cst = ldc.text.toFloat()
                        }

                        "double" -> {
                            instruction.cst = ldc.text.toDouble()
                        }

                        "int" -> {
                            instruction.cst = ldc.text.toInt()
                        }

                        "long" -> {
                            instruction.cst = ldc.text.toLong()
                        }

                        "Class" -> {
                            instruction.cst = Type.getType(ldc.text)
                        }
                    }
                }
                true
            }
        }
    }

    override fun getOpcodes(): List<String> {
        return listOf("LDC")
    }
}
