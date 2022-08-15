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

import cn.enaium.joe.gui.panel.confirm.FrameListEditPanel;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.MessageUtil;
import cn.enaium.joe.util.OpcodeUtil;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;

import javax.swing.*;
import java.util.List;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class FrameInstructionPanel extends AbstractInstructionPanel {
    public FrameInstructionPanel(FrameNode instruction) {
        super(instruction);
        JComboBox<String> component = new JComboBox<>(OpcodeUtil.FRAME.values().toArray(new String[0]));
        component.setSelectedItem(OpcodeUtil.FRAME.get(instruction.type));
        addComponent(new JLabel(LangUtil.i18n("instruction.type")), component);
        addComponent(new JLabel(LangUtil.i18n("instruction.localOrStack")), new JButton(LangUtil.i18n("button.edit")) {{
            addActionListener(e -> {
                FrameListEditPanel frameListEditPanel = new FrameListEditPanel(instruction);
                MessageUtil.confirm(frameListEditPanel, "Frame Edit", () -> {
                    frameListEditPanel.getConfirm().run();
                }, () -> {
                    frameListEditPanel.getCancel().run();
                });
            });
        }});
        setConfirm(() -> {
            if (component.getSelectedItem() != null) {
                instruction.type = OpcodeUtil.reverse(OpcodeUtil.FRAME).get(component.getSelectedItem().toString());
                return true;
            } else {
                return false;
            }
        });
    }

    @Override
    public List<String> getOpcodes() {
        return null;
    }
}
