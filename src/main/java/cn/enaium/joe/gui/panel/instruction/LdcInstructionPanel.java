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

import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class LdcInstructionPanel extends AbstractInstructionPanel {
    public LdcInstructionPanel(LdcInsnNode instruction, InsnList instructions) {
        super(instruction, instructions);
        JComboBox<String> jComboBox = new JComboBox<>(new String[]{"String", "float", "double", "int", "long", "Class", "Handle"});
        addComponent(new JLabel("Type:"), jComboBox);
        if (instruction.cst instanceof String) {
            jComboBox.setSelectedItem("String");
        } else if (instruction.cst instanceof Float) {
            jComboBox.setSelectedItem("float");
        } else if (instruction.cst instanceof Double) {
            jComboBox.setSelectedItem("double");
        } else if (instruction.cst instanceof Integer) {
            jComboBox.setSelectedItem("int");
        } else if (instruction.cst instanceof Long) {
            jComboBox.setSelectedItem("long");
        } else if (instruction.cst instanceof Type) {
            jComboBox.setSelectedItem("Class");
        } else if (instruction.cst instanceof Handle) {
            jComboBox.setSelectedItem("Handle");
        }

        JTextField ldc = new JTextField();
        ldc.setText(instruction.cst.toString());
        addComponent(new JLabel("Var:"), ldc);
        setConfirm(() -> {
            Object value;
            if (jComboBox.getSelectedItem() != null) {
                switch (jComboBox.getSelectedItem().toString()) {
                    case "String":
                        value = ldc.getText();
                        break;
                    case "float":
                        value = Float.parseFloat(ldc.getText());
                        break;
                    case "double":
                        value = Double.parseDouble(ldc.getText());
                        break;
                    case "int":
                        value = Integer.parseInt(ldc.getText());
                        break;
                    case "long":
                        value = Long.parseLong(ldc.getText());
                        break;
                    case "Class":
                        value = Type.getType(ldc.getText());
                        break;
                    default:
                        return false;
                }
                instructions.set(instruction, new LdcInsnNode(value));
            }
            return false;
        });
    }

    @Override
    public List<String> getOpcodes() {
        return Collections.singletonList("LDC");
    }
}
