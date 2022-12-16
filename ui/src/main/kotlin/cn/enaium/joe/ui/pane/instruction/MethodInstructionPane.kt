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
import javafx.geometry.Pos
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import org.objectweb.asm.tree.MethodInsnNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class MethodInstructionPane(instruction: MethodInsnNode) : AbstractInstructionPane(instruction) {

    init {
        val owner = TextField(instruction.owner)
        val name = TextField(instruction.name)
        val description = TextField(instruction.desc)
        val isInterface = CheckBox().apply {
            isSelected = instruction.itf
            alignment = Pos.CENTER_RIGHT
        }

        add(Label(i18n("instruction.owner")), owner)
        add(Label(i18n("instruction.name")), name)
        add(Label(i18n("instruction.description")), description)
        add(Label(i18n("instruction.interface")), isInterface)


        confirm = {
            instruction.opcode = getOpcode()
            instruction.owner = owner.text
            instruction.name = name.text
            instruction.desc = description.text
            instruction.itf = isInterface.isSelected
            true
        }
    }

    override fun getOpcodes(): List<String> {
        return listOf(
            "GETSTATIC",
            "PUTSTATIC",
            "GETFIELD",
            "PUTFIELD"
        )
    }
}
