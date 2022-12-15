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

import cn.enaium.joe.core.config.Config
import cn.enaium.joe.core.config.value.*
import cn.enaium.joe.ui.config.value.KeyCodeValue
import cn.enaium.joe.ui.util.i18n
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import org.tbee.javafx.scene.layout.MigPane

/**
 * @author Enaium
 * @since 2.0.0
 */
class ConfigDialog(config: Config) : Dialog(i18n("menu.config")) {
    init {
        content = ScrollPane(MigPane("fillx", "[fill][fill]").apply {
            config.javaClass.declaredFields.forEach { field ->
                field.isAccessible = true
                val o = field.get(config)
                if (o is Value<*>) {
                    add(Label(o.name))
                }

                when (o) {
                    is StringValue -> {
                        add(TextField(o.value).apply {
                            setOnInputMethodTextChanged {
                                o.value = text
                            }
                        }, "wrap")
                    }

                    is IntegerValue -> {
                        add(Spinner<Int>(0, Int.MAX_VALUE, o.value).apply {
                            setOnInputMethodTextChanged {
                                o.value = value
                            }
                        }, "wrap")
                    }

                    is EnableValue -> {
                        add(CheckBox().apply {
                            alignment = Pos.CENTER_RIGHT
                            isSelected = o.value
                            setOnAction {
                                o.value = isSelected
                            }
                        }, "wrap")
                    }

                    is ModeValue -> {
                        add(ComboBox<String>().apply {
                            value = o.value
                            o.mode.forEach {
                                items.add(it)
                            }
                            selectionModel.selectedItemProperty()
                                .addListener { _, _, newValue ->
                                    o.value = newValue
                                }
                        }, "wrap")
                    }

                    is KeyCodeValue -> {
                        add(Button(o.value.name).apply {
                            setOnKeyPressed {
                                val modifiers = mutableListOf<KeyCombination.Modifier>()

                                if (it.isAltDown) {
                                    modifiers.add(KeyCombination.ALT_DOWN)
                                }

                                if (it.isControlDown) {
                                    modifiers.add(KeyCombination.CONTROL_DOWN)
                                }

                                if (it.isShiftDown) {
                                    modifiers.add(KeyCombination.SHIFT_DOWN)
                                }

                                if (!it.code.isModifierKey) {
                                    o.value = KeyCodeCombination(it.code, *modifiers.toTypedArray())
                                    text = o.value.name
                                }
                            }
                        }, "wrap")
                    }
                }
            }
        }).apply {
            isFitToWidth = true
            isFitToHeight = true
        }
    }
}