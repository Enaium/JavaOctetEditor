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
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Enaium
 * @since 0.8.0
 */
public abstract class AbstractInstructionPanel extends JPanel {
    private final JComboBox<String> opcode = new JComboBox<>(new DefaultComboBoxModel<>());

    private final JPanel names = new JPanel(new GridLayout(0, 1));
    private final JPanel components = new JPanel(new GridLayout(0, 1));

    private Callable<Boolean> confirm = () -> false;

    public AbstractInstructionPanel(AbstractInsnNode instruction) {
        setLayout(new BorderLayout());
        if (instruction.getOpcode() != -1) {
            DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) opcode.getModel();
            getOpcodes().forEach(model::addElement);
            model.setSelectedItem(OpcodeUtil.OPCODE.get(instruction.getOpcode()));
            addComponent(new JLabel("Opcode:"), opcode);
        }

        add(names, BorderLayout.WEST);
        add(components, BorderLayout.CENTER);
    }

    public void addComponent(JComponent name, JComponent component) {
        names.add(new JPanel(new BorderLayout()) {{
            setBorder(new EmptyBorder(10, 10, 10, 10));
            add(name, BorderLayout.CENTER);
        }});
        components.add(new JPanel(new BorderLayout()) {{
            setBorder(new EmptyBorder(10, 10, 10, 10));
            add(component, BorderLayout.CENTER);
        }});
    }

    public void setConfirm(Callable<Boolean> callable) {
        confirm = callable;
    }

    public Callable<Boolean> getConfirm() {
        return confirm;
    }

    public Integer getOpcode() {
        if (opcode.getSelectedItem() == null) {
            throw new NullPointerException("unselected opcode");
        }
        return OpcodeUtil.reverse(OpcodeUtil.OPCODE).get(opcode.getSelectedItem().toString());
    }

    public abstract List<String> getOpcodes();
}
