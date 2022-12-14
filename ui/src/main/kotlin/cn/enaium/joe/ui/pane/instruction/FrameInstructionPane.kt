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
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import org.objectweb.asm.tree.FrameNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class FrameInstructionPane(instruction: FrameNode) : AbstractInstructionPane(instruction) {
    init {
        val frame = ComboBox<String>()
        frame.items.addAll(OpcodeUtil.FRAME.values)
        add(Label(i18n("instruction.type")), frame)
        add(Label(i18n("instruction.localOrStack")), Button(i18n("button.edit")).apply {
            setOnAction {
                // TODO:
            }
        })

        confirm = {
            frame.selectionModel.selectedItem?.let {
                instruction.type = OpcodeUtil.reverse(OpcodeUtil.FRAME)[frame.selectionModel.selectedItem]!!
                true
            }
            false
        }
    }

    override fun getOpcodes(): List<String> {
        return emptyList()
    }
}
