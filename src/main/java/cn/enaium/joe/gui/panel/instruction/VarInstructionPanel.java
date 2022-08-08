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
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class VarInstructionPanel extends AbstractInstructionPanel {
    public VarInstructionPanel(VarInsnNode instruction, InsnList instructions) {
        super(instruction, instructions);
        JSpinner spinner = new JSpinner();
        spinner.setValue(instruction.var);
        addComponent(new JLabel("Var:"), spinner);
        addConfirm(() -> instructions.set(instruction, new VarInsnNode(getOpcode(), Integer.parseInt(spinner.getValue().toString()))));
    }

    @Override
    public List<String> getOpcodes() {
        return new ArrayList<String>() {{
            add("ILOAD");
            add("LLOAD");
            add("FLOAD");
            add("DLOAD");
            add("ALOAD");
            add("ISTORE");
            add("LSTORE");
            add("FSTORE");
            add("DSTORE");
            add("ASTORE");
            add("RET");
        }};
    }
}