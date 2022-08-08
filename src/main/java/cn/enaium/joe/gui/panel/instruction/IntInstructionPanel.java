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

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class IntInstructionPanel extends AbstractInstructionPanel {
    public IntInstructionPanel(IntInsnNode instruction, InsnList instructions) {
        super(instruction, instructions);
        JSpinner spinner = new JSpinner();
        addComponent(new JLabel("Operand:"), spinner);
        addConfirm(() -> instructions.set(instruction, new IntInsnNode(getOpcode(), Integer.parseInt(spinner.getValue().toString()))));
    }

    @Override
    public List<String> getOpcodes() {
        return new ArrayList<String>() {{
            add("BIPUSH");
            add("SIPUSH");
            add("NEWARRAY");
        }};
    }
}
