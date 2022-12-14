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
            KeyCodeCombination(KeyCode.D, KeyCodeCombination.CONTROL_DOWN),
            "Clone method instruction"
        )
    var remove: KeyCodeValue = KeyCodeValue(
        "Remove",
        KeyCodeCombination(KeyCode.Y, KeyCodeCombination.CONTROL_DOWN),
        "Remove method instruction"
    )
    var copy: KeyCodeValue = KeyCodeValue(
        "Copy",
        KeyCodeCombination(KeyCode.C, KeyCodeCombination.CONTROL_DOWN),
        "Copy method instruction text"
    )
    var insertBefore: KeyCodeValue = KeyCodeValue(
        "InsertBefore",
        KeyCodeCombination(KeyCode.ENTER, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.ALT_DOWN),
        "Insert method instruction before current"
    )
    var insertAfter: KeyCodeValue = KeyCodeValue(
        "InsertAfter",
        KeyCodeCombination(KeyCode.ENTER, KeyCodeCombination.CONTROL_DOWN),
        "Insert method instruction after current"
    )
    var moveUp: KeyCodeValue = KeyCodeValue(
        "Move Up",
        KeyCodeCombination(KeyCode.UP, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.SHIFT_DOWN),
        "Move method instruction up"
    )
    var moveDown: KeyCodeValue = KeyCodeValue(
        "Move Up",
        KeyCodeCombination(KeyCode.DOWN, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.SHIFT_DOWN),
        "Move method instruction down"
    )
    var save: KeyCodeValue =
        KeyCodeValue("Save", KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_DOWN), "Save something")
}