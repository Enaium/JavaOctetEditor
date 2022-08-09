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

package cn.enaium.joe.dialog;

import cn.enaium.joe.gui.panel.instruction.*;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.MessageUtil;
import org.objectweb.asm.tree.*;
import org.tinylog.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class InstructionEditDialog extends Dialog {
    public InstructionEditDialog(AbstractInsnNode instruction, InsnList instructions) {
        super("Instruction Edit");
        setLayout(new BorderLayout());

        AbstractInstructionPanel abstractInstructionPanel = null;

        switch (instruction.getType()) {
            case AbstractInsnNode.INSN:
                abstractInstructionPanel = new InstructionPanel((InsnNode) instruction, instructions);
                break;
            case AbstractInsnNode.INT_INSN:
                abstractInstructionPanel = new IntInstructionPanel((IntInsnNode) instruction, instructions);
                break;
            case AbstractInsnNode.VAR_INSN:
                abstractInstructionPanel = new VarInstructionPanel((VarInsnNode) instruction, instructions);
                break;
            case AbstractInsnNode.TYPE_INSN:
                abstractInstructionPanel = new TypeInstructionPanel((TypeInsnNode) instruction, instructions);
                break;
            case AbstractInsnNode.FIELD_INSN:
                abstractInstructionPanel = new FieldInstructionPanel((FieldInsnNode) instruction, instructions);
                break;
            case AbstractInsnNode.METHOD_INSN:
                abstractInstructionPanel = new MethodInstructionPanel((MethodInsnNode) instruction, instructions);
                break;
            case AbstractInsnNode.INVOKE_DYNAMIC_INSN:
                break;
            case AbstractInsnNode.JUMP_INSN:
                abstractInstructionPanel = new JumpInstructionPanel(((JumpInsnNode) instruction), instructions);
                break;
            case AbstractInsnNode.LABEL:
                break;
            case AbstractInsnNode.LDC_INSN:
                abstractInstructionPanel = new LdcInstructionPanel(((LdcInsnNode) instruction), instructions);
                break;
            case AbstractInsnNode.IINC_INSN:
                abstractInstructionPanel = new IncrInstructionPanel((IincInsnNode) instruction, instructions);
                break;
            case AbstractInsnNode.TABLESWITCH_INSN:
                break;
            case AbstractInsnNode.LOOKUPSWITCH_INSN:
                break;
            case AbstractInsnNode.MULTIANEWARRAY_INSN:
                break;
            case AbstractInsnNode.FRAME:
                abstractInstructionPanel = new FrameInstructionPanel(((FrameNode) instruction), instructions);
                break;
            case AbstractInsnNode.LINE:
                abstractInstructionPanel = new LineInstructionPanel(((LineNumberNode) instruction), instructions);
                break;
        }
        if (abstractInstructionPanel != null) {
            AbstractInstructionPanel finalMessage = abstractInstructionPanel;
            add(new JPanel(new BorderLayout()) {{
                setBorder(new EmptyBorder(10, 10, 10, 10));
                add(finalMessage, BorderLayout.CENTER);
            }}, BorderLayout.CENTER);
            add(new JPanel() {{
                add(new JButton("Confirm") {{
                    addActionListener(e -> {
                        try {
                            if (finalMessage.getConfirm().call()) {
                                MessageUtil.info("Succeed");
                                dispose();
                            } else {
                                MessageUtil.info("Failed");
                            }
                        } catch (Exception ex) {
                            MessageUtil.error(ex);
                        }
                    });
                }});
                add(new JButton("Cancel") {{
                    addActionListener(e -> dispose());
                }});
            }}, BorderLayout.SOUTH);
        }
        pack();
    }
}
