/*
 * Copyright 2022 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an"AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.joe.util;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class OpcodeUtil {
    public static final Map<Integer, String> API = new HashMap<Integer, String>() {{
        put(Opcodes.ASM4, "ASM4");
        put(Opcodes.ASM5, "ASM5");
        put(Opcodes.ASM6, "ASM6");
        put(Opcodes.ASM7, "ASM7");
        put(Opcodes.ASM8, "ASM8");
        put(Opcodes.ASM9, "ASM9");
    }};
    public static final Map<Integer, String> VERSION = new HashMap<Integer, String>() {{
        put(Opcodes.V1_1, "V1_1");
        put(Opcodes.V1_2, "V1_2");
        put(Opcodes.V1_3, "V1_3");
        put(Opcodes.V1_4, "V1_4");
        put(Opcodes.V1_5, "V1_5");
        put(Opcodes.V1_6, "V1_6");
        put(Opcodes.V1_7, "V1_7");
        put(Opcodes.V1_8, "V1_8");
        put(Opcodes.V9, "V9");
        put(Opcodes.V10, "V10");
        put(Opcodes.V11, "V11");
        put(Opcodes.V12, "V12");
        put(Opcodes.V13, "V13");
        put(Opcodes.V14, "V14");
        put(Opcodes.V15, "V15");
        put(Opcodes.V16, "V16");
        put(Opcodes.V17, "V17");
        put(Opcodes.V18, "V18");
        put(Opcodes.V19, "V19");
    }};
    public static final Map<Integer, String> HANDLE = new HashMap<Integer, String>() {{
        put(Opcodes.H_GETFIELD, "H_GETFIELD");
        put(Opcodes.H_GETSTATIC, "H_GETSTATIC");
        put(Opcodes.H_PUTFIELD, "H_PUTFIELD");
        put(Opcodes.H_PUTSTATIC, "H_PUTSTATIC");
        put(Opcodes.H_INVOKEVIRTUAL, "H_INVOKEVIRTUAL");
        put(Opcodes.H_INVOKESTATIC, "H_INVOKESTATIC");
        put(Opcodes.H_INVOKESPECIAL, "H_INVOKESPECIAL");
        put(Opcodes.H_NEWINVOKESPECIAL, "H_NEWINVOKESPECIAL");
        put(Opcodes.H_INVOKEINTERFACE, "H_INVOKEINTERFACE");
    }};


    public static final Map<Integer, String> FRAME = new HashMap<Integer, String>() {{
        put(Opcodes.F_NEW, "F_NEW");
        put(Opcodes.F_FULL, "F_FULL");
        put(Opcodes.F_APPEND, "F_APPEND");
        put(Opcodes.F_CHOP, "F_CHOP");
        put(Opcodes.F_SAME, "F_SAME");
        put(Opcodes.F_SAME1, "F_SAME1");
    }};
    public static final Map<Integer, String> OPCODE = new HashMap<Integer, String>() {{
        put(Opcodes.NOP, "NOP");
        put(Opcodes.ACONST_NULL, "ACONST_NULL");
        put(Opcodes.ICONST_M1, "ICONST_M1");
        put(Opcodes.ICONST_0, "ICONST_0");
        put(Opcodes.ICONST_1, "ICONST_1");
        put(Opcodes.ICONST_2, "ICONST_2");
        put(Opcodes.ICONST_3, "ICONST_3");
        put(Opcodes.ICONST_4, "ICONST_4");
        put(Opcodes.ICONST_5, "ICONST_5");
        put(Opcodes.LCONST_0, "LCONST_0");
        put(Opcodes.LCONST_1, "LCONST_1");
        put(Opcodes.FCONST_0, "FCONST_0");
        put(Opcodes.FCONST_1, "FCONST_1");
        put(Opcodes.FCONST_2, "FCONST_2");
        put(Opcodes.DCONST_0, "DCONST_0");
        put(Opcodes.DCONST_1, "DCONST_1");
        put(Opcodes.BIPUSH, "BIPUSH");
        put(Opcodes.SIPUSH, "SIPUSH");
        put(Opcodes.LDC, "LDC");
        put(Opcodes.ILOAD, "ILOAD");
        put(Opcodes.LLOAD, "LLOAD");
        put(Opcodes.FLOAD, "FLOAD");
        put(Opcodes.DLOAD, "DLOAD");
        put(Opcodes.ALOAD, "ALOAD");
        put(Opcodes.IALOAD, "IALOAD");
        put(Opcodes.LALOAD, "LALOAD");
        put(Opcodes.FALOAD, "FALOAD");
        put(Opcodes.DALOAD, "DALOAD");
        put(Opcodes.AALOAD, "AALOAD");
        put(Opcodes.BALOAD, "BALOAD");
        put(Opcodes.CALOAD, "CALOAD");
        put(Opcodes.SALOAD, "SALOAD");
        put(Opcodes.ISTORE, "ISTORE");
        put(Opcodes.LSTORE, "LSTORE");
        put(Opcodes.FSTORE, "FSTORE");
        put(Opcodes.DSTORE, "DSTORE");
        put(Opcodes.ASTORE, "ASTORE");
        put(Opcodes.IASTORE, "IASTORE");
        put(Opcodes.LASTORE, "LASTORE");
        put(Opcodes.FASTORE, "FASTORE");
        put(Opcodes.DASTORE, "DASTORE");
        put(Opcodes.AASTORE, "AASTORE");
        put(Opcodes.BASTORE, "BASTORE");
        put(Opcodes.CASTORE, "CASTORE");
        put(Opcodes.SASTORE, "SASTORE");
        put(Opcodes.POP, "POP");
        put(Opcodes.POP2, "POP2");
        put(Opcodes.DUP, "DUP");
        put(Opcodes.DUP_X1, "DUP_X1");
        put(Opcodes.DUP_X2, "DUP_X2");
        put(Opcodes.DUP2, "DUP2");
        put(Opcodes.DUP2_X1, "DUP2_X1");
        put(Opcodes.DUP2_X2, "DUP2_X2");
        put(Opcodes.SWAP, "SWAP");
        put(Opcodes.IADD, "IADD");
        put(Opcodes.LADD, "LADD");
        put(Opcodes.FADD, "FADD");
        put(Opcodes.DADD, "DADD");
        put(Opcodes.ISUB, "ISUB");
        put(Opcodes.LSUB, "LSUB");
        put(Opcodes.FSUB, "FSUB");
        put(Opcodes.DSUB, "DSUB");
        put(Opcodes.IMUL, "IMUL");
        put(Opcodes.LMUL, "LMUL");
        put(Opcodes.FMUL, "FMUL");
        put(Opcodes.DMUL, "DMUL");
        put(Opcodes.IDIV, "IDIV");
        put(Opcodes.LDIV, "LDIV");
        put(Opcodes.FDIV, "FDIV");
        put(Opcodes.DDIV, "DDIV");
        put(Opcodes.IREM, "IREM");
        put(Opcodes.LREM, "LREM");
        put(Opcodes.FREM, "FREM");
        put(Opcodes.DREM, "DREM");
        put(Opcodes.INEG, "INEG");
        put(Opcodes.LNEG, "LNEG");
        put(Opcodes.FNEG, "FNEG");
        put(Opcodes.DNEG, "DNEG");
        put(Opcodes.ISHL, "ISHL");
        put(Opcodes.LSHL, "LSHL");
        put(Opcodes.ISHR, "ISHR");
        put(Opcodes.LSHR, "LSHR");
        put(Opcodes.IUSHR, "IUSHR");
        put(Opcodes.LUSHR, "LUSHR");
        put(Opcodes.IAND, "IAND");
        put(Opcodes.LAND, "LAND");
        put(Opcodes.IOR, "IOR");
        put(Opcodes.LOR, "LOR");
        put(Opcodes.IXOR, "IXOR");
        put(Opcodes.LXOR, "LXOR");
        put(Opcodes.IINC, "IINC");
        put(Opcodes.I2L, "I2L");
        put(Opcodes.I2F, "I2F");
        put(Opcodes.I2D, "I2D");
        put(Opcodes.L2I, "L2I");
        put(Opcodes.L2F, "L2F");
        put(Opcodes.L2D, "L2D");
        put(Opcodes.F2I, "F2I");
        put(Opcodes.F2L, "F2L");
        put(Opcodes.F2D, "F2D");
        put(Opcodes.D2I, "D2I");
        put(Opcodes.D2L, "D2L");
        put(Opcodes.D2F, "D2F");
        put(Opcodes.I2B, "I2B");
        put(Opcodes.I2C, "I2C");
        put(Opcodes.I2S, "I2S");
        put(Opcodes.LCMP, "LCMP");
        put(Opcodes.FCMPL, "FCMPL");
        put(Opcodes.FCMPG, "FCMPG");
        put(Opcodes.DCMPL, "DCMPL");
        put(Opcodes.DCMPG, "DCMPG");
        put(Opcodes.IFEQ, "IFEQ");
        put(Opcodes.IFNE, "IFNE");
        put(Opcodes.IFLT, "IFLT");
        put(Opcodes.IFGE, "IFGE");
        put(Opcodes.IFGT, "IFGT");
        put(Opcodes.IFLE, "IFLE");
        put(Opcodes.IF_ICMPEQ, "IF_ICMPEQ");
        put(Opcodes.IF_ICMPNE, "IF_ICMPNE");
        put(Opcodes.IF_ICMPLT, "IF_ICMPLT");
        put(Opcodes.IF_ICMPGE, "IF_ICMPGE");
        put(Opcodes.IF_ICMPGT, "IF_ICMPGT");
        put(Opcodes.IF_ICMPLE, "IF_ICMPLE");
        put(Opcodes.IF_ACMPEQ, "IF_ACMPEQ");
        put(Opcodes.IF_ACMPNE, "IF_ACMPNE");
        put(Opcodes.GOTO, "GOTO");
        put(Opcodes.JSR, "JSR");
        put(Opcodes.RET, "RET");
        put(Opcodes.TABLESWITCH, "TABLESWITCH");
        put(Opcodes.LOOKUPSWITCH, "LOOKUPSWITCH");
        put(Opcodes.IRETURN, "IRETURN");
        put(Opcodes.LRETURN, "LRETURN");
        put(Opcodes.FRETURN, "FRETURN");
        put(Opcodes.DRETURN, "DRETURN");
        put(Opcodes.ARETURN, "ARETURN");
        put(Opcodes.RETURN, "RETURN");
        put(Opcodes.GETSTATIC, "GETSTATIC");
        put(Opcodes.PUTSTATIC, "PUTSTATIC");
        put(Opcodes.GETFIELD, "GETFIELD");
        put(Opcodes.PUTFIELD, "PUTFIELD");
        put(Opcodes.INVOKEVIRTUAL, "INVOKEVIRTUAL");
        put(Opcodes.INVOKESPECIAL, "INVOKESPECIAL");
        put(Opcodes.INVOKESTATIC, "INVOKESTATIC");
        put(Opcodes.INVOKEINTERFACE, "INVOKEINTERFACE");
        put(Opcodes.INVOKEDYNAMIC, "INVOKEDYNAMIC");
        put(Opcodes.NEW, "NEW");
        put(Opcodes.NEWARRAY, "NEWARRAY");
        put(Opcodes.ANEWARRAY, "ANEWARRAY");
        put(Opcodes.ARRAYLENGTH, "ARRAYLENGTH");
        put(Opcodes.ATHROW, "ATHROW");
        put(Opcodes.CHECKCAST, "CHECKCAST");
        put(Opcodes.INSTANCEOF, "INSTANCEOF");
        put(Opcodes.MONITORENTER, "MONITORENTER");
        put(Opcodes.MONITOREXIT, "MONITOREXIT");
        put(Opcodes.MULTIANEWARRAY, "MULTIANEWARRAY");
        put(Opcodes.IFNULL, "IFNULL");
        put(Opcodes.IFNONNULL, "IFNONNULL");
    }};


    public static int getLabelIndex(AbstractInsnNode labelNode) {
        int index = 0;
        AbstractInsnNode node = labelNode;
        while (node.getPrevious() != null) {
            node = node.getPrevious();
            if (node instanceof LabelNode) {
                index++;
            }
        }
        return index;
    }
}
