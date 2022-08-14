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

import cn.enaium.joe.gui.component.LabelNodeComboBox;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.OpcodeUtil;
import cn.enaium.joe.wrapper.LabelNodeWrapper;
import org.objectweb.asm.tree.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class JumpInstructionPanel extends AbstractInstructionPanel {
    public JumpInstructionPanel(JumpInsnNode instruction) {
        super(instruction);
        LabelNodeComboBox component = new LabelNodeComboBox(instruction, instruction.label);
        addComponent(new JLabel(LangUtil.i18n("instruction.label")), component);
        setConfirm(() -> {
            Object selectedItem = component.getSelectedItem();
            if (selectedItem != null) {
                instruction.setOpcode(getOpcode());
                instruction.label = ((LabelNodeWrapper) selectedItem).getWrapper();
                return true;
            }
            return false;
        });
    }

    @Override
    public List<String> getOpcodes() {
        return new ArrayList<String>() {{
            add("IFEQ");
            add("IFNE");
            add("IFLT");
            add("IFGE");
            add("IFGT");
            add("IFLE");
            add("IF_ICMPEQ");
            add("IF_ICMPNE");
            add("IF_ICMPLT");
            add("IF_ICMPGE");
            add("IF_ICMPGT");
            add("IF_ICMPLE");
            add("IF_ACMPEQ");
            add("IF_ACMPNE");
            add("GOTO");
            add("JSR");
            add("IFNULL");
            add("IFNONNULL");
        }};
    }
}
