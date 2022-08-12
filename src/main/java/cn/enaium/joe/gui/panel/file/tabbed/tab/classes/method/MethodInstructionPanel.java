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

import cn.enaium.joe.gui.panel.confirm.InstructionEditPanel;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.MessageUtil;
import cn.enaium.joe.wrapper.InstructionWrapper;
import org.objectweb.asm.tree.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        for (AbstractInsnNode instruction : methodNode.instructions) {
            instructionDefaultListModel.addElement(new InstructionWrapper(instruction));
        }

        JPopupMenu jPopupMenu = new JPopupMenu();
        jPopupMenu.add(new JMenuItem(LangUtil.i18n("instruction.edit")) {{
            addActionListener(e -> {
                InstructionWrapper selectedValue = instructionJList.getSelectedValue();
                if (selectedValue != null && !(selectedValue.getWrapper() instanceof LabelNode)) {
                    MessageUtil.confirm(new InstructionEditPanel(selectedValue.getWrapper()), "Instruction Edit");
                }
            });
        }});

        jPopupMenu.add(new JMenuItem(LangUtil.i18n("instruction.clone")) {{
            addActionListener(e -> {
                InstructionWrapper selectedValue = instructionJList.getSelectedValue();
                if (instructionJList.getSelectedIndex() != -1 || selectedValue != null) {
                    AbstractInsnNode clone;
                    if (selectedValue.getWrapper() instanceof LabelNode) {
                        clone = new LabelNode();
                    } else {
                        clone = selectedValue.getWrapper().clone(new HashMap<>());
                    }

                    instructionDefaultListModel.add(instructionJList.getSelectedIndex(), new InstructionWrapper(clone));
                    methodNode.instructions.insert(selectedValue.getWrapper(), clone);
                }
            });
        }});

        instructionJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    if (instructionJList.getSelectedValue() != null) {
                        jPopupMenu.show(instructionJList, e.getX(), e.getY());
                    }
                }
            }
        });
        add(new JScrollPane(instructionJList), BorderLayout.CENTER);
        JLabel comp = new JLabel();
        instructionJList.addListSelectionListener(e -> {
            if (instructionJList.getSelectedIndex() != -1) {
                comp.setText(String.format("Index:%d", instructionJList.getSelectedIndex()));
                comp.setVisible(true);
            } else {
                comp.setVisible(false);
            }
        });
        add(comp, BorderLayout.SOUTH);
    }
}
