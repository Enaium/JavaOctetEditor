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

import cn.enaium.joe.core.wrapper.Wrapper
import cn.enaium.joe.ui.dialog.ConfirmDialog
import cn.enaium.joe.ui.pane.confirm.BootstrapMethodArgumentEditPane
import cn.enaium.joe.ui.pane.confirm.HandleEditPane
import cn.enaium.joe.ui.util.i18n
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import org.objectweb.asm.tree.InvokeDynamicInsnNode

class InvokeDynamicInstructionPane(instruction: InvokeDynamicInsnNode) : AbstractInstructionPane(instruction) {

    init {
        val name = TextField(instruction.name)
        val description = TextField(instruction.desc)

        add(Label(i18n("instruction.name")), name)
        add(Label(i18n("instruction.description")), description)
        add(Label(i18n("instruction.bootstrapMethod")), Button(i18n("button.edit")).apply {
            setOnAction {
                val handleWrapper = Wrapper(instruction.bsm)
                ConfirmDialog(HandleEditPane(handleWrapper)).apply {
                    confirm = {
                        instruction.bsm = handleWrapper.wrapper
                    }
                }.show()
            }
        })
        add(Label(i18n("instruction.bootstrapMethodArgument")), Button(i18n("button.edit")).apply {
            setOnAction {
                val anyArrayWrapper = Wrapper(instruction.bsmArgs)
                ConfirmDialog(BootstrapMethodArgumentEditPane(anyArrayWrapper)).apply {
                    confirm = {
                        instruction.bsmArgs = anyArrayWrapper.wrapper
                    }
                }.show()
            }
        })




        confirm = {
            instruction.name = name.text
            instruction.desc = description.text
            true
        }
    }

    override fun getOpcodes(): List<String> {
        return listOf("INVOKEDYNAMIC")
    }
}
