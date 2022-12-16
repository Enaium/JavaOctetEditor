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
import cn.enaium.joe.ui.util.i18n
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Spinner
import org.objectweb.asm.tree.TableSwitchInsnNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class TableSwitchInstructionPane(instruction: TableSwitchInsnNode) : AbstractInstructionPane(instruction) {
    init {
        val min = Spinner<Int>(0, Int.MAX_VALUE, instruction.min)
        add(Label(i18n("instruction.min")), min)
        val max = Spinner<Int>(0, Int.MAX_VALUE, instruction.max)
        add(Label(i18n("instruction.max")), max)
        val dflt = LabelNodeComboBox(instruction.dflt)
        add(Label(i18n("instruction.default")), dflt)
        add(Label(i18n("instruction.keyOrLabel")), Button("button.edit").apply {
            setOnAction {
                // TODO:
            }
        })
        confirm = {
            instruction.min = min.value
            instruction.max = max.value
            instruction.dflt = dflt.selectionModel.selectedItem.wrapper
            true
        }
    }

    override fun getOpcodes(): List<String> {
        return listOf("TABLESWITCH")
    }
}
