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

package cn.enaium.joe.gui.panel.method;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.config.extend.KeymapConfig;
import cn.enaium.joe.gui.component.InstructionComboBox;
import cn.enaium.joe.gui.panel.confirm.InstructionEditPanel;
import cn.enaium.joe.util.Util;
import cn.enaium.joe.util.*;
import cn.enaium.joe.wrapper.InstructionWrapper;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.HashMap;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class MethodInstructionPanel extends JPanel {
    public MethodInstructionPanel(MethodNode methodNode) {
        super(new BorderLayout());
        DefaultListModel<InstructionWrapper> instructionDefaultListModel = new DefaultListModel<>();
        JList<InstructionWrapper> instructionJList = new JList<>(instructionDefaultListModel);
        instructionJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        instructionJList.setPrototypeCellValue(new InstructionWrapper(null));
        instructionJList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> new JPanel(new BorderLayout()) {{
            if (isSelected) {
                setBackground(list.getSelectionBackground());
            } else {
                setBackground(list.getBackground());
            }
            add(new JLabel(String.format("%04d ", index)), BorderLayout.WEST);
            add(new JLabel(value.toString()), BorderLayout.CENTER);
        }});
        for (AbstractInsnNode instruction : methodNode.instructions) {
            instructionDefaultListModel.addElement(new InstructionWrapper(instruction));
        }

        JPopupMenu jPopupMenu = new JPopupMenu();
        KeymapConfig keymapConfig = JavaOctetEditor.getInstance().config.getByClass(KeymapConfig.class);

        addItem(instructionJList, jPopupMenu, LangUtil.i18n("popup.instructions.edit"), () -> {
            InstructionWrapper selectedValue = instructionJList.getSelectedValue();
            if (selectedValue != null && !(selectedValue.getWrapper() instanceof LabelNode)) {
                MessageUtil.confirm(new InstructionEditPanel(selectedValue.getWrapper()), LangUtil.i18n("popup.instructions.edit"));
            }
        }, keymapConfig.edit.getValue());


        addItem(instructionJList, jPopupMenu, LangUtil.i18n("popup.instructions.clone"), () -> {
            InstructionWrapper selectedValue = instructionJList.getSelectedValue();
            if (instructionJList.getSelectedIndex() != -1 || selectedValue != null) {
                AbstractInsnNode clone;
                if (selectedValue.getWrapper() instanceof LabelNode) {
                    clone = new LabelNode();
                } else {
                    clone = selectedValue.getWrapper().clone(new HashMap<>());
                }

                instructionDefaultListModel.add(instructionJList.getSelectedIndex() + 1, new InstructionWrapper(clone));
                methodNode.instructions.insert(selectedValue.getWrapper(), clone);
            }
        }, keymapConfig.clone.getValue());

        addItem(instructionJList, jPopupMenu, LangUtil.i18n("popup.instructions.remove"), () -> {
            InstructionWrapper selectedValue = instructionJList.getSelectedValue();
            if (instructionJList.getSelectedIndex() != -1 || selectedValue != null) {
                MessageUtil.confirm(LangUtil.i18n("dialog.wantRemove"), LangUtil.i18n("button.remove"), () -> {
                    instructionDefaultListModel.remove(instructionJList.getSelectedIndex());
                    methodNode.instructions.remove(selectedValue.getWrapper());
                });
            }
        }, keymapConfig.remove.getValue());

        addItem(instructionJList, jPopupMenu, LangUtil.i18n("popup.instructions.copyText"), () -> {
            InstructionWrapper selectedValue = instructionJList.getSelectedValue();
            if (instructionJList.getSelectedIndex() != -1 || selectedValue != null) {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(HtmlUtil.remove(selectedValue.toString())), null);
            }
        }, keymapConfig.copy.getValue());

        addItem(instructionJList, jPopupMenu, LangUtil.i18n("popup.instructions.insertBefore"), () -> insert(methodNode, instructionJList, true), keymapConfig.insertBefore.getValue());
        addItem(instructionJList, jPopupMenu, LangUtil.i18n("popup.instructions.insertAfter"), () -> insert(methodNode, instructionJList, false), keymapConfig.insertAfter.getValue());
        addItem(instructionJList, jPopupMenu, LangUtil.i18n("popup.instructions.moveUp"), () -> moveInstruction(instructionJList, methodNode, true), keymapConfig.insertAfter.getValue());
        addItem(instructionJList, jPopupMenu, LangUtil.i18n("popup.instructions.moveDown"), () -> moveInstruction(instructionJList, methodNode, false), keymapConfig.insertAfter.getValue());

        JMenuUtil.addPopupMenu(instructionJList, () -> jPopupMenu, () -> instructionJList.getSelectedValue() != null);
        add(new JScrollPane(instructionJList), BorderLayout.CENTER);
    }

    private void addItem(JList<InstructionWrapper> instructionJList, JPopupMenu jPopupMenu, String text, Runnable runnable, KeyStroke key) {
        jPopupMenu.add(new JMenuItem(text) {{
            KeyStrokeUtil.register(instructionJList, key, runnable);
            setAccelerator(key);
            addActionListener(Util.ofAction(runnable));
        }});
    }

    private static void moveInstruction(JList<InstructionWrapper> instructionJList, MethodNode methodNode, boolean up) {
        DefaultListModel<InstructionWrapper> instructionDefaultListModel = ((DefaultListModel<InstructionWrapper>) instructionJList.getModel());
        InstructionWrapper selectedValue = instructionJList.getSelectedValue();
        if (instructionJList.getSelectedIndex() != -1 || selectedValue != null) {
            AbstractInsnNode node = up ? selectedValue.getWrapper().getPrevious() : selectedValue.getWrapper().getNext();
            if (node != null) {
                try {
                    InstructionWrapper instructionWrapper = instructionDefaultListModel.get(instructionJList.getSelectedIndex() + (up ? -1 : 1));
                    instructionDefaultListModel.removeElement(instructionWrapper);
                    instructionDefaultListModel.add(instructionJList.getSelectedIndex() + (up ? 1 : 0), instructionWrapper);
                } catch (Exception ignore) {

                }
                methodNode.instructions.remove(node);
                if (up) {
                    methodNode.instructions.insert(selectedValue.getWrapper(), node);
                } else {
                    methodNode.instructions.insertBefore(selectedValue.getWrapper(), node);
                }
            }
        }
    }

    private static void insert(MethodNode methodNode, JList<InstructionWrapper> instructionJList, boolean before) {
        if (instructionJList.getSelectedIndex() == -1 || instructionJList.getSelectedValue() == null) {
            return;
        }
        DefaultListModel<InstructionWrapper> instructionDefaultListModel = ((DefaultListModel<InstructionWrapper>) instructionJList.getModel());
        InstructionComboBox instructionComboBox = new InstructionComboBox();
        MessageUtil.confirm(instructionComboBox, "select insert instruction", () -> {
            AbstractInsnNode abstractInsnNode;
            int selectedIndex = instructionComboBox.getSelectedIndex();
            switch (selectedIndex) {
                case AbstractInsnNode.INSN:
                    abstractInsnNode = new InsnNode(Opcodes.NOP);
                    break;
                case AbstractInsnNode.INT_INSN:
                    abstractInsnNode = new IntInsnNode(Opcodes.BIPUSH, 0);
                    break;
                case AbstractInsnNode.VAR_INSN:
                    abstractInsnNode = new VarInsnNode(Opcodes.ILOAD, 0);
                    break;
                case AbstractInsnNode.TYPE_INSN:
                    abstractInsnNode = new TypeInsnNode(Opcodes.ILOAD, "");
                    break;
                case AbstractInsnNode.FIELD_INSN:
                    abstractInsnNode = new FieldInsnNode(Opcodes.GETSTATIC, "", "", "");
                    break;
                case AbstractInsnNode.METHOD_INSN:
                    abstractInsnNode = new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "", "", "", false);
                    break;
                case AbstractInsnNode.INVOKE_DYNAMIC_INSN:
                    abstractInsnNode = new InvokeDynamicInsnNode("", "", new Handle(Opcodes.H_GETFIELD, "", "", "", false));
                    break;
                case AbstractInsnNode.JUMP_INSN:
                    abstractInsnNode = new JumpInsnNode(Opcodes.IFEQ, OpcodeUtil.getFirstLabel(methodNode.instructions));
                    break;
                case AbstractInsnNode.LABEL:
                    abstractInsnNode = new LabelNode();
                    break;
                case AbstractInsnNode.LDC_INSN:
                    abstractInsnNode = new LdcInsnNode("");
                    break;
                case AbstractInsnNode.IINC_INSN:
                    abstractInsnNode = new IntInsnNode(Opcodes.IINC, 0);
                    break;
                case AbstractInsnNode.TABLESWITCH_INSN:
                    abstractInsnNode = new TableSwitchInsnNode(0, 0, OpcodeUtil.getFirstLabel(methodNode.instructions));
                    break;
                case AbstractInsnNode.LOOKUPSWITCH_INSN:
                    abstractInsnNode = new LookupSwitchInsnNode(OpcodeUtil.getFirstLabel(methodNode.instructions), new int[]{}, new LabelNode[]{});
                    break;
                case AbstractInsnNode.MULTIANEWARRAY_INSN:
                    abstractInsnNode = new MultiANewArrayInsnNode("", 0);
                    break;
                case AbstractInsnNode.FRAME:
                    abstractInsnNode = new FrameNode(Opcodes.F_NEW, 0, new Object[]{}, 0, new Object[]{});
                    break;
                case AbstractInsnNode.LINE:
                    abstractInsnNode = new LineNumberNode(0, OpcodeUtil.getFirstLabel(methodNode.instructions));
                    break;
                default:
                    throw new RuntimeException();
            }

            MessageUtil.confirm(new InstructionEditPanel(abstractInsnNode), LangUtil.i18n("popup.instructions.edit"), () -> {
                if (before) {
                    methodNode.instructions.insertBefore(instructionJList.getSelectedValue().getWrapper(), abstractInsnNode);
                } else {
                    methodNode.instructions.insert(instructionJList.getSelectedValue().getWrapper(), abstractInsnNode);
                }
                instructionDefaultListModel.add(instructionJList.getSelectedIndex() + (before ? 0 : 1), new InstructionWrapper(abstractInsnNode));
            });
        });
    }
}
