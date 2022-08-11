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

import cn.enaium.joe.gui.panel.confirm.LabelListEditPanel;
import cn.enaium.joe.util.MessageUtil;
import cn.enaium.joe.util.OpcodeUtil;
import cn.enaium.joe.wrapper.LabelNodeWrapper;
import org.benf.cfr.reader.util.StringUtils;
import org.objectweb.asm.tree.*;

import javax.swing.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class TableSwitchInstructionPanel extends AbstractInstructionPanel {
    public TableSwitchInstructionPanel(TableSwitchInsnNode instruction) {
        super(instruction);
        JSpinner min = new JSpinner();
        min.setValue(instruction.min);
        addComponent(new JLabel("Min:"), min);
        JSpinner max = new JSpinner();
        max.setValue(instruction.max);
        addComponent(new JLabel("Max:"), max);
        DefaultComboBoxModel<LabelNodeWrapper> stringDefaultComboBoxModel = new DefaultComboBoxModel<>();
        LabelNodeWrapper selected = null;
        for (AbstractInsnNode abstractInsnNode : OpcodeUtil.getInstructionList(instruction)) {
            if (abstractInsnNode instanceof LabelNode) {
                LabelNodeWrapper anObject = new LabelNodeWrapper(((LabelNode) abstractInsnNode));
                if (abstractInsnNode.equals(instruction.dflt)) {
                    selected = anObject;
                }
                stringDefaultComboBoxModel.addElement(anObject);
            }
        }
        stringDefaultComboBoxModel.setSelectedItem(selected);
        addComponent(new JLabel("Default:"), new JComboBox<>(stringDefaultComboBoxModel));
        addComponent(new JLabel("Labels:"), new JButton("Edit") {{
            addActionListener(e -> {
                MessageUtil.confirm(new LabelListEditPanel(instruction.labels, OpcodeUtil.getInstructionList(instruction)), "Labels Edit");
            });
        }});
        Object selectedItem = stringDefaultComboBoxModel.getSelectedItem();
        if (selectedItem != null) {
            setConfirm(() -> {
                instruction.min = Integer.parseInt(min.getValue().toString());
                instruction.max = Integer.parseInt(max.getValue().toString());
                instruction.dflt = ((LabelNodeWrapper) selectedItem).getWrapper();
                return true;
            });
        }
    }

    @Override
    public List<String> getOpcodes() {
        return Collections.singletonList("TABLESWITCH");
    }
}
