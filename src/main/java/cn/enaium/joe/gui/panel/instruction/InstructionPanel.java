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

package cn.enaium.joe.gui.panel.instruction;

import cn.enaium.joe.util.OpcodeUtil;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class InstructionPanel extends AbstractInstructionPanel {
    public InstructionPanel(InsnNode instruction) {
        super(instruction);
        setConfirm(() -> {
            Field opcode = instruction.getClass().getSuperclass().getDeclaredField("opcode");
            opcode.setAccessible(true);
            opcode.set(instruction, getOpcode());
            return true;
        });
    }

    @Override
    public List<String> getOpcodes() {
        return new ArrayList<String>() {{
            add("NOP");
            add("ACONST_NULL");
            add("ICONST_M1");
            add("ICONST_0");
            add("ICONST_1");
            add("ICONST_2");
            add("ICONST_3");
            add("ICONST_4");
            add("ICONST_5");
            add("LCONST_0");
            add("LCONST_1");
            add("FCONST_0");
            add("FCONST_1");
            add("FCONST_2");
            add("DCONST_0");
            add("DCONST_1");
            add("IALOAD");
            add("LALOAD");
            add("FALOAD");
            add("DALOAD");
            add("AALOAD");
            add("BALOAD");
            add("CALOAD");
            add("SALOAD");
            add("IASTORE");
            add("LASTORE");
            add("FASTORE");
            add("DASTORE");
            add("AASTORE");
            add("BASTORE");
            add("CASTORE");
            add("SASTORE");
            add("POP");
            add("POP2");
            add("DUP");
            add("DUP_X1");
            add("DUP_X2");
            add("DUP2");
            add("DUP2_X1");
            add("DUP2_X2");
            add("SWAP");
            add("IADD");
            add("LADD");
            add("FADD");
            add("DADD");
            add("ISUB");
            add("LSUB");
            add("FSUB");
            add("DSUB");
            add("IMUL");
            add("LMUL");
            add("FMUL");
            add("DMUL");
            add("IDIV");
            add("LDIV");
            add("FDIV");
            add("DDIV");
            add("IREM");
            add("LREM");
            add("FREM");
            add("DREM");
            add("INEG");
            add("LNEG");
            add("FNEG");
            add("DNEG");
            add("ISHL");
            add("LSHL");
            add("ISHR");
            add("LSHR");
            add("IUSHR");
            add("LUSHR");
            add("IAND");
            add("LAND");
            add("IOR");
            add("LOR");
            add("IXOR");
            add("LXOR");
            add("I2L");
            add("I2F");
            add("I2D");
            add("L2I");
            add("L2F");
            add("L2D");
            add("F2I");
            add("F2L");
            add("F2D");
            add("D2I");
            add("D2L");
            add("D2F");
            add("I2B");
            add("I2C");
            add("I2S");
            add("LCMP");
            add("FCMPL");
            add("FCMPG");
            add("DCMPL");
            add("DCMPG");
            add("IRETURN");
            add("LRETURN");
            add("FRETURN");
            add("DRETURN");
            add("ARETURN");
            add("RETURN");
            add("ARRAYLENGTH");
            add("ATHROW");
            add("MONITORENTER");
            add("MONITOREXIT");
        }};
    }
}
