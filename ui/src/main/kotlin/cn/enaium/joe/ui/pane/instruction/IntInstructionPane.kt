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

import cn.enaium.joe.ui.util.i18n
import javafx.scene.control.Label
import javafx.scene.control.Spinner
import org.objectweb.asm.tree.IntInsnNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class IntInstructionPane(instruction: IntInsnNode) : AbstractInstructionPane(instruction) {
    init {
        val operand = Spinner<Int>(0, Int.MAX_VALUE, instruction.operand)
        add(Label(i18n("instruction.operand")), operand)
        confirm = {
            instruction.opcode = getOpcode()
            instruction.operand = operand.value
            true
        }
    }

    override fun getOpcodes(): List<String> {
        return listOf(
            "BIPUSH",
            "SIPUSH",
            "NEWARRAY"
        )
    }
}