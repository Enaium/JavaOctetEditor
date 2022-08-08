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

package cn.enaium.joe.gui.panel.file.tabbed.tab.classes.method;

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
public class MethodInstruction {
    private final int index;
    private final AbstractInsnNode instruction;

    public MethodInstruction(int index, AbstractInsnNode instruction) {
        this.index = index;
        this.instruction = instruction;
    }

    public int getIndex() {
        return index;
    }

    public AbstractInsnNode getInstruction() {
        return instruction;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>");

        if (instruction.getOpcode() != -1) {
            stringBuilder.append(HtmlUtil.setColor(OpcodeUtil.OPCODE.get(instruction.getOpcode()), FontColor.opcode));
        }

        switch (instruction.getType()) {
            case AbstractInsnNode.INSN:
                //Opcode
                break;
            case AbstractInsnNode.INT_INSN:
                IntInsnNode intInsnNode = (IntInsnNode) instruction;
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(intInsnNode.operand), FontColor.base));
                break;
            case AbstractInsnNode.VAR_INSN:
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(((VarInsnNode) instruction).var), FontColor.base));
                break;
            case AbstractInsnNode.TYPE_INSN:
                append(stringBuilder, HtmlUtil.setColor(((TypeInsnNode) instruction).desc, FontColor.desc));
                break;
            case AbstractInsnNode.FIELD_INSN:
                FieldInsnNode fieldInsnNode = (FieldInsnNode) instruction;
                append(stringBuilder, HtmlUtil.setColor(fieldInsnNode.name, FontColor.name));
                append(stringBuilder, HtmlUtil.setColor(fieldInsnNode.desc, FontColor.desc));
                break;
            case AbstractInsnNode.METHOD_INSN:
                MethodInsnNode methodInsnNode = (MethodInsnNode) instruction;
                append(stringBuilder, HtmlUtil.setColor(methodInsnNode.name, FontColor.name));
                append(stringBuilder, HtmlUtil.setColor(methodInsnNode.desc, FontColor.desc));
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(methodInsnNode.itf), FontColor.bool));
                break;
            case AbstractInsnNode.INVOKE_DYNAMIC_INSN:
                InvokeDynamicInsnNode invokeDynamicInsnNode = (InvokeDynamicInsnNode) instruction;
                append(stringBuilder, HtmlUtil.setColor(invokeDynamicInsnNode.name, FontColor.name));
                append(stringBuilder, HtmlUtil.setColor(invokeDynamicInsnNode.desc, FontColor.desc));
                append(stringBuilder, "\n");
                append(stringBuilder, handle(invokeDynamicInsnNode.bsm));
                String[] strings = Arrays.stream(invokeDynamicInsnNode.bsmArgs).map(it -> {
                    if (it instanceof Handle) {
                        return handle(((Handle) it));
                    } else {
                        return it.toString();
                    }
                }).toArray(String[]::new);
                append(stringBuilder, HtmlUtil.setColor(Arrays.toString(strings), FontColor.desc));
                break;
            case AbstractInsnNode.JUMP_INSN:
                append(stringBuilder, HtmlUtil.setColor("L" + OpcodeUtil.getLabelIndex(((JumpInsnNode) instruction).label), FontColor.base));
                break;
            case AbstractInsnNode.LABEL:
                append(stringBuilder, HtmlUtil.setColor("L", FontColor.opcode));
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(OpcodeUtil.getLabelIndex(instruction)), FontColor.base));
                break;
            case AbstractInsnNode.LDC_INSN:
                LdcInsnNode ldcInsnNode = (LdcInsnNode) instruction;
                if (ldcInsnNode.cst instanceof String) {
                    append(stringBuilder, HtmlUtil.setColor("\"" + ldcInsnNode.cst + "\"", FontColor.string));
                } else if (ldcInsnNode.cst instanceof Boolean) {
                    append(stringBuilder, HtmlUtil.setColor(ldcInsnNode.cst.toString(), FontColor.bool));
                } else {
                    append(stringBuilder, HtmlUtil.setColor(ldcInsnNode.cst.toString(), FontColor.base));
                }
                break;
            case AbstractInsnNode.IINC_INSN:
                IincInsnNode iincInsnNode = (IincInsnNode) instruction;
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(iincInsnNode.var), FontColor.base));
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(iincInsnNode.incr), FontColor.base));
                break;
            case AbstractInsnNode.TABLESWITCH_INSN:
                TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode) instruction;
                append(stringBuilder, HtmlUtil.setColor(String.format("range[%s:%s]", tableSwitchInsnNode.min, tableSwitchInsnNode.max), FontColor.desc));
                tableSwitchInsnNode.labels.stream().map(OpcodeUtil::getLabelIndex).forEach(it -> append(stringBuilder, HtmlUtil.setColor("L" + it, FontColor.base)));
                if (tableSwitchInsnNode.dflt != null) {
                    append(stringBuilder, HtmlUtil.setColor("default", FontColor.other) + ":" + HtmlUtil.setColor("L" + OpcodeUtil.getLabelIndex(tableSwitchInsnNode.dflt), FontColor.base));
                }
                break;
            case AbstractInsnNode.LOOKUPSWITCH_INSN:
                LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode) instruction;
                for (int i = 0; i < lookupSwitchInsnNode.keys.size(); i++) {
                    append(stringBuilder, HtmlUtil.setColor(String.valueOf(lookupSwitchInsnNode.keys.get(i)), FontColor.base) + ":" + HtmlUtil.setColor("L" + OpcodeUtil.getLabelIndex(lookupSwitchInsnNode.labels.get(i)), FontColor.base));
                }
                if (lookupSwitchInsnNode.dflt != null) {
                    append(stringBuilder, HtmlUtil.setColor("default", FontColor.other) + ":" + HtmlUtil.setColor("L" + OpcodeUtil.getLabelIndex(lookupSwitchInsnNode.dflt), FontColor.base));
                }
                break;
            case AbstractInsnNode.MULTIANEWARRAY_INSN:
                MultiANewArrayInsnNode multiANewArrayInsnNode = (MultiANewArrayInsnNode) instruction;
                append(stringBuilder, HtmlUtil.setColor(multiANewArrayInsnNode.desc, FontColor.desc));
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(multiANewArrayInsnNode.dims), FontColor.base));
                break;
            case AbstractInsnNode.FRAME:
                FrameNode frameNode = (FrameNode) instruction;
                append(stringBuilder, HtmlUtil.setColor(OpcodeUtil.FRAME.get(frameNode.type), FontColor.opcode));
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(frameNode.local), FontColor.desc));
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(frameNode.stack), FontColor.desc));
                break;
            case AbstractInsnNode.LINE:
                append(stringBuilder, HtmlUtil.setColor("LINE", FontColor.opcode));
                append(stringBuilder, HtmlUtil.setColor(String.valueOf(((LineNumberNode) instruction).line), FontColor.base));
                break;
        }

        stringBuilder.append("</html>");
        return stringBuilder.toString();
    }

    public void append(StringBuilder stringBuilder, String str) {
        stringBuilder.append(" ").append(str);
    }

    public String handle(Handle handle) {
        return HtmlUtil.setColor("handle[", FontColor.other) +
                HtmlUtil.setColor(OpcodeUtil.HANDLE.get(handle.getTag()), FontColor.opcode) + ":" +
                HtmlUtil.setColor(handle.getOwner(), FontColor.desc) + "." +
                HtmlUtil.setColor(handle.getName(), FontColor.name) +
                HtmlUtil.setColor(handle.getDesc(), FontColor.desc) +
                HtmlUtil.setColor(String.valueOf(handle.isInterface()), FontColor.bool) +
                HtmlUtil.setColor("]", FontColor.other);
    }


    interface FontColor {
        Color opcode = new Color(196, 118, 215);
        Color name = new Color(222, 107, 116);
        Color desc = new Color(227, 191, 122);
        Color base = new Color(208, 153, 102);
        Color string = new Color(150, 193, 115);
        Color bool = new Color(196, 118, 215);
        Color other = new Color(62, 137, 236);
    }
}
