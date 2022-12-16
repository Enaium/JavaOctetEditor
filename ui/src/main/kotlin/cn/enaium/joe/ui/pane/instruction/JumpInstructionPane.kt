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
import javafx.scene.control.Label
import org.objectweb.asm.tree.JumpInsnNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class JumpInstructionPane(instruction: JumpInsnNode) : AbstractInstructionPane(instruction) {

    init {
        val labelComboBox = LabelNodeComboBox(instruction.label)
        add(Label(i18n("instruction.label")), labelComboBox)
        confirm = {
            labelComboBox.selectionModel.selectedItem?.let {
                instruction.opcode = getOpcode()
                instruction.label = labelComboBox.selectionModel.selectedItem.wrapper
                true
            }
            false
        }
    }

    override fun getOpcodes(): List<String> {
        return listOf(
            "IFEQ",
            "IFNE",
            "IFLT",
            "IFGE",
            "IFGT",
            "IFLE",
            "IF_ICMPEQ",
            "IF_ICMPNE",
            "IF_ICMPLT",
            "IF_ICMPGE",
            "IF_ICMPGT",
            "IF_ICMPLE",
            "IF_ACMPEQ",
            "IF_ACMPNE",
            "GOTO",
            "JSR",
            "IFNULL",
            "IFNONNULL"
        )
    }
}
