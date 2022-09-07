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

package cn.enaium.joe.wrapper;

import cn.enaium.joe.util.ColorUtil;
import cn.enaium.joe.util.HtmlUtil;
import cn.enaium.joe.util.OpcodeUtil;
import org.objectweb.asm.Handle;
import org.objectweb.asm.tree.*;

import java.awt.*;
import java.util.Arrays;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class InstructionWrapper extends Wrapper<AbstractInsnNode> {


    public InstructionWrapper(AbstractInsnNode wrapper) {
        super(wrapper);
    }

    @Override
    public String toString() {


        if (getWrapper() == null) {
            return " ";
        }


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>");

        if (getWrapper().getOpcode() != -1) {
            stringBuilder.append(HtmlUtil.setColor(OpcodeUtil.OPCODE.get(getWrapper().getOpcode()), ColorUtil.opcode));
        }

        switch (getWrapper().getType()) {
            case AbstractInsnNode.INSN:
                //Opcode
                break;
            case AbstractInsnNode.INT_INSN:
                IntInsnNode intInsnNode = (IntInsnNode) getWrapper();
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(intInsnNode.operand), ColorUtil.base));
                break;
            case AbstractInsnNode.VAR_INSN:
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(((VarInsnNode) getWrapper()).var), ColorUtil.base));
                break;
            case AbstractInsnNode.TYPE_INSN:
                append(stringBuilder, HtmlUtil.setColor(((TypeInsnNode) getWrapper()).desc, ColorUtil.desc));
                break;
            case AbstractInsnNode.FIELD_INSN:
                FieldInsnNode fieldInsnNode = (FieldInsnNode) getWrapper();
                append(stringBuilder, HtmlUtil.setColor(fieldInsnNode.name, ColorUtil.name));
                append(stringBuilder, HtmlUtil.setColor(fieldInsnNode.desc, ColorUtil.desc));
                break;
            case AbstractInsnNode.METHOD_INSN:
                MethodInsnNode methodInsnNode = (MethodInsnNode) getWrapper();
                append(stringBuilder, HtmlUtil.setColor(methodInsnNode.name, ColorUtil.name));
                append(stringBuilder, HtmlUtil.setColor(methodInsnNode.desc, ColorUtil.desc));
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(methodInsnNode.itf), ColorUtil.bool));
                break;
            case AbstractInsnNode.INVOKE_DYNAMIC_INSN:
                InvokeDynamicInsnNode invokeDynamicInsnNode = (InvokeDynamicInsnNode) getWrapper();
                append(stringBuilder, HtmlUtil.setColor(invokeDynamicInsnNode.name, ColorUtil.name));
                append(stringBuilder, HtmlUtil.setColor(invokeDynamicInsnNode.desc, ColorUtil.desc));
                append(stringBuilder, "\n");
                append(stringBuilder, handle(invokeDynamicInsnNode.bsm));
                String[] strings = Arrays.stream(invokeDynamicInsnNode.bsmArgs).map(it -> {
                    if (it instanceof Handle) {
                        return handle(((Handle) it));
                    } else {
                        return it.toString();
                    }
                }).toArray(String[]::new);
                append(stringBuilder, HtmlUtil.setColor(Arrays.toString(strings), ColorUtil.desc));
                break;
            case AbstractInsnNode.JUMP_INSN:
                append(stringBuilder, HtmlUtil.setColor("L" + OpcodeUtil.getLabelIndex(((JumpInsnNode) getWrapper()).label), ColorUtil.base));
                break;
            case AbstractInsnNode.LABEL:
                append(stringBuilder, HtmlUtil.setColor("L", ColorUtil.opcode));
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(OpcodeUtil.getLabelIndex(getWrapper())), ColorUtil.base));
                break;
            case AbstractInsnNode.LDC_INSN:
                LdcInsnNode ldcInsnNode = (LdcInsnNode) getWrapper();
                if (ldcInsnNode.cst instanceof String) {
                    append(stringBuilder, HtmlUtil.setColor("\"" + ldcInsnNode.cst + "\"", ColorUtil.string));
                } else if (ldcInsnNode.cst instanceof Boolean) {
                    append(stringBuilder, HtmlUtil.setColor(ldcInsnNode.cst.toString(), ColorUtil.bool));
                } else {
                    append(stringBuilder, HtmlUtil.setColor(ldcInsnNode.cst.toString(), ColorUtil.base));
                }
                break;
            case AbstractInsnNode.IINC_INSN:
                IincInsnNode iincInsnNode = (IincInsnNode) getWrapper();
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(iincInsnNode.var), ColorUtil.base));
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(iincInsnNode.incr), ColorUtil.base));
                break;
            case AbstractInsnNode.TABLESWITCH_INSN:
                TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode) getWrapper();
                append(stringBuilder, HtmlUtil.setColor(String.format("range[%s:%s]", tableSwitchInsnNode.min, tableSwitchInsnNode.max), ColorUtil.desc));
                tableSwitchInsnNode.labels.stream().map(OpcodeUtil::getLabelIndex).forEach(it -> append(stringBuilder, HtmlUtil.setColor("L" + it, ColorUtil.base)));
                if (tableSwitchInsnNode.dflt != null) {
                    append(stringBuilder, HtmlUtil.setColor("default", ColorUtil.other) + ":" + HtmlUtil.setColor("L" + OpcodeUtil.getLabelIndex(tableSwitchInsnNode.dflt), ColorUtil.base));
                }
                break;
            case AbstractInsnNode.LOOKUPSWITCH_INSN:
                LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode) getWrapper();
                for (int i = 0; i < lookupSwitchInsnNode.keys.size(); i++) {
                    append(stringBuilder, HtmlUtil.setColor(String.valueOf(lookupSwitchInsnNode.keys.get(i)), ColorUtil.base) + ":" + HtmlUtil.setColor("L" + OpcodeUtil.getLabelIndex(lookupSwitchInsnNode.labels.get(i)), ColorUtil.base));
                }
                if (lookupSwitchInsnNode.dflt != null) {
                    append(stringBuilder, HtmlUtil.setColor("default", ColorUtil.other) + ":" + HtmlUtil.setColor("L" + OpcodeUtil.getLabelIndex(lookupSwitchInsnNode.dflt), ColorUtil.base));
                }
                break;
            case AbstractInsnNode.MULTIANEWARRAY_INSN:
                MultiANewArrayInsnNode multiANewArrayInsnNode = (MultiANewArrayInsnNode) getWrapper();
                append(stringBuilder, HtmlUtil.setColor(multiANewArrayInsnNode.desc, ColorUtil.desc));
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(multiANewArrayInsnNode.dims), ColorUtil.base));
                break;
            case AbstractInsnNode.FRAME:
                FrameNode frameNode = (FrameNode) getWrapper();
                append(stringBuilder, HtmlUtil.setColor(OpcodeUtil.FRAME.get(frameNode.type), ColorUtil.opcode));
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(frameNode.local), ColorUtil.desc));
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(frameNode.stack), ColorUtil.desc));
                break;
            case AbstractInsnNode.LINE:
                LineNumberNode lineNumberNode = (LineNumberNode) getWrapper();
                append(stringBuilder, HtmlUtil.setColor("LINE", ColorUtil.opcode));
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(lineNumberNode.line), ColorUtil.base));
                append(stringBuilder, HtmlUtil.setColor("L" + OpcodeUtil.getLabelIndex(lineNumberNode.start), ColorUtil.base));
                break;
        }

        stringBuilder.append("</html>");
        return stringBuilder.toString();
    }

    private void append(StringBuilder stringBuilder, String str) {
        stringBuilder.append(" ").append(str);
    }

    private String handle(Handle handle) {
        return HtmlUtil.setColor("handle[", ColorUtil.other) +
                HtmlUtil.setColor(OpcodeUtil.HANDLE.get(handle.getTag()), ColorUtil.opcode) + ":" +
                HtmlUtil.setColor(handle.getOwner(), ColorUtil.desc) + "." +
                HtmlUtil.setColor(handle.getName(), ColorUtil.name) +
                HtmlUtil.setColor(handle.getDesc(), ColorUtil.desc) +
                HtmlUtil.setColor(String.valueOf(handle.isInterface()), ColorUtil.bool) +
                HtmlUtil.setColor("]", ColorUtil.other);
    }
}
