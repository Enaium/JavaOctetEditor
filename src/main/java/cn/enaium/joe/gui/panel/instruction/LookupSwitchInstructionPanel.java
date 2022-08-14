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
import cn.enaium.joe.gui.panel.confirm.LookupSwitchEditPanel;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.MessageUtil;
import cn.enaium.joe.util.OpcodeUtil;
import cn.enaium.joe.wrapper.LabelNodeWrapper;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class LookupSwitchInstructionPanel extends AbstractInstructionPanel {
    public LookupSwitchInstructionPanel(LookupSwitchInsnNode instruction) {
        super(instruction);
        LabelNodeComboBox component = new LabelNodeComboBox(instruction, instruction.dflt);
        addComponent(new JLabel(LangUtil.i18n("instruction.default")), component);
        addComponent(new JLabel(LangUtil.i18n("instruction.keyOrLabel")), new JButton(LangUtil.i18n("button.edit")) {{
            addActionListener(e -> {
                MessageUtil.confirm(new LookupSwitchEditPanel(instruction.keys, instruction.labels), LangUtil.i18n("instruction.keyOrLabel"));
            });
        }});
        setConfirm(() -> {
            Object selectedItem = component.getSelectedItem();
            if (selectedItem != null) {
                instruction.dflt = ((LabelNodeWrapper) selectedItem).getWrapper();
                return true;
            }
            return false;
        });
    }

    @Override
    public List<String> getOpcodes() {
        return Collections.singletonList("LOOKUPSWITCH");
    }
}
