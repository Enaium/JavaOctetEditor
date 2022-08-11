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
import org.objectweb.asm.tree.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
            if (selectedValue != null && !(selectedValue.getInstruction() instanceof LabelNode)) {
                MessageUtil.confirm(new InstructionEditPanel(selectedValue.getInstruction()), "Instruction Edit");
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
        JLabel comp = new JLabel();
        jList.addListSelectionListener(e -> {
            if (jList.getSelectedValue() != null) {
                MethodInstruction selectedValue = jList.getSelectedValue();
                comp.setText(String.format("Index:%d", selectedValue.getIndex()));
                comp.setVisible(true);
            } else {
                comp.setVisible(false);
            }
        });
        add(comp, BorderLayout.SOUTH);
    }
}
