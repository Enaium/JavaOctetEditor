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

import cn.enaium.joe.ui.control.LabelNodeComboBox
import cn.enaium.joe.ui.dialog.ConfirmDialog
import cn.enaium.joe.ui.pane.confirm.LookupSwitchEditPane
import cn.enaium.joe.ui.util.i18n
import javafx.scene.control.Button
import javafx.scene.control.Label
import org.objectweb.asm.tree.LookupSwitchInsnNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class LookupSwitchInstructionPane(instruction: LookupSwitchInsnNode) : AbstractInstructionPane(instruction) {
    init {
        val dflt = LabelNodeComboBox(instruction.dflt)
        add(Label(i18n("instruction.default")), dflt)
        add(Label(i18n("instruction.keyOrLabel")), Button(i18n("button.edit")).apply {
            setOnAction {
                ConfirmDialog(LookupSwitchEditPane(instruction.keys, instruction.labels)).show()
            }
        })
        confirm = {
            instruction.dflt = dflt.selectionModel.selectedItem.wrapper
            true
        }
    }

    override fun getOpcodes(): List<String> {
        return listOf("LOOKUPSWITCH")
    }
}
