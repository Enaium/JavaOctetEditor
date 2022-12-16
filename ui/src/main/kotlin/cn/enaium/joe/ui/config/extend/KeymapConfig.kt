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

package cn.enaium.joe.ui.config.extend

import cn.enaium.joe.core.config.Config
import cn.enaium.joe.ui.config.value.KeyCodeValue
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javax.swing.KeyStroke


/**
 * @author Enaium
 * @since 2.0.0
 */
class KeymapConfig : Config("Keymap") {
    var edit: KeyCodeValue = KeyCodeValue("Edit", KeyCodeCombination(KeyCode.ENTER), "Edit method instruction")
    var clone: KeyCodeValue =
        KeyCodeValue(
            "Clone",
            KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN),
            "Clone method instruction"
        )
    var remove: KeyCodeValue = KeyCodeValue(
        "Remove",
        KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN),
        "Remove method instruction"
    )
    var copy: KeyCodeValue = KeyCodeValue(
        "Copy",
        KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN),
        "Copy method instruction text"
    )
    var insertBefore: KeyCodeValue = KeyCodeValue(
        "InsertBefore",
        KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN),
        "Insert method instruction before current"
    )
    var insertAfter: KeyCodeValue = KeyCodeValue(
        "InsertAfter",
        KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN),
        "Insert method instruction after current"
    )
    var moveUp: KeyCodeValue = KeyCodeValue(
        "Move Up",
        KeyCodeCombination(KeyCode.UP, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
        "Move method instruction up"
    )
    var moveDown: KeyCodeValue = KeyCodeValue(
        "Move Up",
        KeyCodeCombination(KeyCode.DOWN, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
        "Move method instruction down"
    )
    var save: KeyCodeValue =
        KeyCodeValue("Save", KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN), "Save something")
}