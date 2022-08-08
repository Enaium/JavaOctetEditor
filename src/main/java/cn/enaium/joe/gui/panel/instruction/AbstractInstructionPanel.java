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
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enaium
 * @since 0.8.0
 */
public abstract class AbstractInstructionPanel extends JPanel {
    private final JComboBox<String> opcode = new JComboBox<>(new DefaultComboBoxModel<>());

    private final JPanel names = new JPanel(new GridLayout(0, 1));
    private final JPanel components = new JPanel(new GridLayout(0, 1));

    private final List<Runnable> confirms = new ArrayList<>();

    public AbstractInstructionPanel(AbstractInsnNode instruction, InsnList instructions) {
        if (instruction.getOpcode() != -1) {
            names.add(new JLabel("Opcode:"));
            DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) opcode.getModel();
            getOpcodes().forEach(model::addElement);
            model.setSelectedItem(OpcodeUtil.OPCODE.get(instruction.getOpcode()));
            components.add(opcode);
        }

        add(names, BorderLayout.WEST);
        add(components, BorderLayout.EAST);
    }

    public void addComponent(JComponent name, JComponent component) {
        names.add(name);
        components.add(component);
    }

    public void addConfirm(Runnable runnable) {
        confirms.add(runnable);
    }

    public Integer getOpcode() {
        if (opcode.getSelectedItem() == null) {
            throw new NullPointerException("unselected opcode");
        }
        return OpcodeUtil.reverse(OpcodeUtil.OPCODE).get(opcode.getSelectedItem().toString());
    }

    public List<Runnable> getConfirms() {
        return confirms;
    }

    public abstract List<String> getOpcodes();
}
