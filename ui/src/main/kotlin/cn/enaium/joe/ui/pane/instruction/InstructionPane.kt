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

import org.objectweb.asm.tree.AbstractInsnNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class InstructionPane(instruction: AbstractInsnNode) : AbstractInstructionPane(instruction) {

    init {
        confirm = {
            with(instruction.javaClass.superclass.getDeclaredField("opcode")) {
                isAccessible = true
                set(instruction, getOpcode())
            }
            true
        }
    }

    override fun getOpcodes(): List<String> {
        return listOf(
            "NOP",
            "ACONST_NULL",
            "ICONST_M1",
            "ICONST_0",
            "ICONST_1",
            "ICONST_2",
            "ICONST_3",
            "ICONST_4",
            "ICONST_5",
            "LCONST_0",
            "LCONST_1",
            "FCONST_0",
            "FCONST_1",
            "FCONST_2",
            "DCONST_0",
            "DCONST_1",
            "IALOAD",
            "LALOAD",
            "FALOAD",
            "DALOAD",
            "AALOAD",
            "BALOAD",
            "CALOAD",
            "SALOAD",
            "IASTORE",
            "LASTORE",
            "FASTORE",
            "DASTORE",
            "AASTORE",
            "BASTORE",
            "CASTORE",
            "SASTORE",
            "POP",
            "POP2",
            "DUP",
            "DUP_X1",
            "DUP_X2",
            "DUP2",
            "DUP2_X1",
            "DUP2_X2",
            "SWAP",
            "IADD",
            "LADD",
            "FADD",
            "DADD",
            "ISUB",
            "LSUB",
            "FSUB",
            "DSUB",
            "IMUL",
            "LMUL",
            "FMUL",
            "DMUL",
            "IDIV",
            "LDIV",
            "FDIV",
            "DDIV",
            "IREM",
            "LREM",
            "FREM",
            "DREM",
            "INEG",
            "LNEG",
            "FNEG",
            "DNEG",
            "ISHL",
            "LSHL",
            "ISHR",
            "LSHR",
            "IUSHR",
            "LUSHR",
            "IAND",
            "LAND",
            "IOR",
            "LOR",
            "IXOR",
            "LXOR",
            "I2L",
            "I2F",
            "I2D",
            "L2I",
            "L2F",
            "L2D",
            "F2I",
            "F2L",
            "F2D",
            "D2I",
            "D2L",
            "D2F",
            "I2B",
            "I2C",
            "I2S",
            "LCMP",
            "FCMPL",
            "FCMPG",
            "DCMPL",
            "DCMPG",
            "IRETURN",
            "LRETURN",
            "FRETURN",
            "DRETURN",
            "ARETURN",
            "RETURN",
            "ARRAYLENGTH",
            "ATHROW",
            "MONITORENTER",
            "MONITOREXIT",
        )
    }
}