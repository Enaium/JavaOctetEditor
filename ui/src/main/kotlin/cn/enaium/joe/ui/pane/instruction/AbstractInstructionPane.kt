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

import cn.enaium.joe.core.util.OpcodeUtil
import cn.enaium.joe.ui.util.i18n
import javafx.scene.Node
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import org.objectweb.asm.tree.AbstractInsnNode
import org.tbee.javafx.scene.layout.MigPane
import java.lang.NullPointerException
import java.util.concurrent.Callable

/**
 * @author Enaium
 * @since 2.0.0
 */
abstract class AbstractInstructionPane(instruction: AbstractInsnNode) : MigPane("fillx", "[fill]", "[fill]") {
    private val opcode = ComboBox<String>()
    var confirm = { false }

    init {
        if (instruction.opcode != -1) {
            opcode.items.addAll(getOpcodes())
            opcode.selectionModel.select(OpcodeUtil.OPCODE[instruction.opcode])
            add(Label(i18n("instruction.opcode")), opcode)
        }
    }

    fun add(name: Node, control: Node) {
        add(name)
        add(control, "wrap")
    }

    fun getOpcode(): Int {
        if (opcode.selectionModel.selectedItem == null) {
            throw NullPointerException("unselect opcode")
        }
        return OpcodeUtil.reverse(OpcodeUtil.OPCODE)[opcode.selectionModel.selectedItem]!!
    }

    abstract fun getOpcodes(): List<String>
}