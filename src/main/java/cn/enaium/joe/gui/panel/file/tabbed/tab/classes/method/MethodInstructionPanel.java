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

import cn.enaium.joe.gui.panel.instruction.*;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.MessageUtil;
import org.objectweb.asm.tree.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.StandardCharsets;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class MethodInstructionPanel extends JPanel {
    public MethodInstructionPanel(MethodNode methodNode) {
        super(new BorderLayout());
        JList<MethodInstruction> jList = new JList<>(new DefaultListModel<>());
        for (AbstractInsnNode instruction : methodNode.instructions) {
            ((DefaultListModel<MethodInstruction>) jList.getModel()).addElement(new MethodInstruction(methodNode.instructions.indexOf(instruction), instruction));
        }

        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem jMenuItem = new JMenuItem(LangUtil.i18n("instruction.edit"));
        jMenuItem.addActionListener(e -> {
            MethodInstruction selectedValue = jList.getSelectedValue();
            AbstractInstructionPanel message = null;

            switch (selectedValue.getInstruction().getType()) {
                case AbstractInsnNode.INSN:
                    message = new InstructionPanel((InsnNode) selectedValue.getInstruction(), methodNode.instructions);
                    break;
                case AbstractInsnNode.INT_INSN:
                    message = new IntInstructionPanel((IntInsnNode) selectedValue.getInstruction(), methodNode.instructions);
                    break;
                case AbstractInsnNode.VAR_INSN:
                    message = new VarInstructionPanel((VarInsnNode) selectedValue.getInstruction(), methodNode.instructions);
                    break;
                case AbstractInsnNode.TYPE_INSN:
                    message = new TypeInstructionPanel((TypeInsnNode) selectedValue.getInstruction(), methodNode.instructions);
                    break;
                case AbstractInsnNode.FIELD_INSN:
                    break;
                case AbstractInsnNode.METHOD_INSN:
                    break;
                case AbstractInsnNode.INVOKE_DYNAMIC_INSN:
                    break;
                case AbstractInsnNode.JUMP_INSN:
                    break;
                case AbstractInsnNode.LABEL:
                    break;
                case AbstractInsnNode.LDC_INSN:
                    break;
                case AbstractInsnNode.IINC_INSN:
                    break;
                case AbstractInsnNode.TABLESWITCH_INSN:
                    break;
                case AbstractInsnNode.LOOKUPSWITCH_INSN:
                    break;
                case AbstractInsnNode.MULTIANEWARRAY_INSN:
                    break;
                case AbstractInsnNode.FRAME:
                    break;
                case AbstractInsnNode.LINE:
                    break;
            }

            if (message != null) {
                AbstractInstructionPanel finalMessage = message;
                MessageUtil.confirm(message, LangUtil.i18n("instruction.edit"), () -> {
                    finalMessage.getConfirms().forEach(Runnable::run);
                    MessageUtil.info("Success");
                }, () -> {
                });
            }
        });
        jPopupMenu.add(jMenuItem);

        jList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    if (jList.getSelectedValue() != null) {
                        jPopupMenu.show(jList, e.getX(), e.getY());
                    }
                }
            }
        });
        add(new JScrollPane(jList), BorderLayout.CENTER);
    }
}
