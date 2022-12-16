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

package cn.enaium.joe.ui.control

import cn.enaium.joe.ui.cell.InstructionListCell
import cn.enaium.joe.ui.dialog.ConfirmDialog
import cn.enaium.joe.ui.pane.confirm.InstructionEditPane
import javafx.scene.control.ListView
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.MethodNode


/**
 * @author Enaium
 * @since 2.0.0
 */
class MethodInstructionList(methodNode: MethodNode) : ListView<AbstractInsnNode>() {
    init {
        setCellFactory {
            InstructionListCell()
        }

        methodNode.instructions.forEach {
            items.add(it)
        }

        setOnMouseClicked {
            if (it.clickCount == 2) {
                selectionModel.selectedItem?.let {
                    ConfirmDialog(InstructionEditPane(selectionModel.selectedItem)).show()
                }
            }
        }
    }
}