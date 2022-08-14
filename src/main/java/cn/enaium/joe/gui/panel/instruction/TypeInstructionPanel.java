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

import cn.enaium.joe.util.LangUtil;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.TypeInsnNode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class TypeInstructionPanel extends AbstractInstructionPanel {
    public TypeInstructionPanel(TypeInsnNode instruction) {
        super(instruction);
        JTextField description = new JTextField(instruction.desc);
        addComponent(new JLabel(LangUtil.i18n("instruction.description")), description);
        setConfirm(() -> {
            instruction.setOpcode(getOpcode());
            instruction.desc = description.getText();
            return true;
        });
    }

    @Override
    public List<String> getOpcodes() {
        return new ArrayList<String>() {{
            add("NEW");
            add("ANEWARRAY");
            add("CHECKCAST");
            add("INSTANCEOF");
        }};
    }
}
