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
import javafx.scene.control.TextField
import org.objectweb.asm.tree.MultiANewArrayInsnNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class MultiANewArrayInstructionPane(instruction: MultiANewArrayInsnNode) : AbstractInstructionPane(instruction) {
    init {
        val description = TextField(instruction.desc)
        add(Label(i18n("instruction.description")), description)
        val dims = Spinner<Int>(0, Int.MAX_VALUE, instruction.dims)
        add(Label(i18n("instruction.dimension")), dims)
        confirm = {
            instruction.desc = description.text
            instruction.dims = dims.value
            true
        }
    }

    override fun getOpcodes(): List<String> {
        return listOf("MULTIANEWARRAY")
    }
}
