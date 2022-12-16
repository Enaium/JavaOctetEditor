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

package cn.enaium.joe.ui.pane.confirm

import cn.enaium.joe.ui.pane.instruction.*
import cn.enaium.joe.ui.util.warning
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.FrameNode
import org.objectweb.asm.tree.IincInsnNode
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.IntInsnNode
import org.objectweb.asm.tree.InvokeDynamicInsnNode
import org.objectweb.asm.tree.JumpInsnNode
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.LineNumberNode
import org.objectweb.asm.tree.LookupSwitchInsnNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MultiANewArrayInsnNode
import org.objectweb.asm.tree.TableSwitchInsnNode
import org.objectweb.asm.tree.TypeInsnNode
import org.objectweb.asm.tree.VarInsnNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class InstructionEditPane(instruction: AbstractInsnNode) : ConfirmPane() {
    init {
        var abstractInstructionPane: AbstractInstructionPane? = null

        when (instruction) {
            is InsnNode -> abstractInstructionPane = InstructionPane(instruction)
            is IntInsnNode -> abstractInstructionPane = IntInstructionPane(instruction)
            is VarInsnNode -> abstractInstructionPane = VarInstructionPane(instruction)
            is TypeInsnNode -> abstractInstructionPane = TypeInstructionPane(instruction)
            is FieldInsnNode -> abstractInstructionPane = FieldInstructionPane(instruction)
            is MethodInsnNode -> abstractInstructionPane = MethodInstructionPane(instruction)
            is InvokeDynamicInsnNode -> abstractInstructionPane = InvokeDynamicInstructionPane(instruction)
            is JumpInsnNode -> abstractInstructionPane = JumpInstructionPane(instruction)
            is LabelNode -> {}
            is LdcInsnNode -> abstractInstructionPane = LDCInstructionPane(instruction)
            is IincInsnNode -> abstractInstructionPane = IncrInstructionPane(instruction)
            is TableSwitchInsnNode -> abstractInstructionPane = TableSwitchInstructionPane(instruction)
            is LookupSwitchInsnNode -> abstractInstructionPane = LookupSwitchInstructionPane(instruction)
            is MultiANewArrayInsnNode -> abstractInstructionPane = MultiANewArrayInstructionPane(instruction)
            is FrameNode -> abstractInstructionPane = FrameInstructionPane(instruction)
            is LineNumberNode -> abstractInstructionPane = LineInstructionPane(instruction)
        }

        abstractInstructionPane?.let {
            center = abstractInstructionPane
            confirm = {
                if (!abstractInstructionPane.confirm.invoke()) {
                    warning("Fail")
                }
            }
        }
    }
}