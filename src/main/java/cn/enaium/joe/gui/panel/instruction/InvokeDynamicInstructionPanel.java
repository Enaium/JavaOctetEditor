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

import cn.enaium.joe.gui.panel.confirm.BootstrapMethodArgumentEditPanel;
import cn.enaium.joe.gui.panel.confirm.HandleEditPanel;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.MessageUtil;
import cn.enaium.joe.wrapper.Wrapper;
import org.objectweb.asm.Handle;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class InvokeDynamicInstructionPanel extends AbstractInstructionPanel {
    public InvokeDynamicInstructionPanel(InvokeDynamicInsnNode instruction) {
        super(instruction);
        JTextField name = new JTextField(instruction.name);
        JTextField description = new JTextField(instruction.desc);
        addComponent(new JLabel(LangUtil.i18n("instruction.name")), name);
        addComponent(new JLabel(LangUtil.i18n("instruction.description")), description);
        addComponent(new JLabel(LangUtil.i18n("instruction.bootstrapMethod")), new JButton(LangUtil.i18n("button.edit")) {{
            addActionListener(e -> {
                Wrapper<Handle> wrapper = new Wrapper<>(instruction.bsm);
                HandleEditPanel handleEditPanel = new HandleEditPanel(wrapper);
                MessageUtil.confirm(handleEditPanel, "Handle Edit", () -> {
                    handleEditPanel.getConfirm().run();
                    instruction.bsm = wrapper.getWrapper();
                }, () -> {

                });
            });
        }});
        addComponent(new JLabel(LangUtil.i18n("instruction.bootstrapMethodArgument")), new JButton(LangUtil.i18n("button.edit")) {{
            addActionListener(e -> {
                Wrapper<Object[]> bsmArgs = new Wrapper<>(instruction.bsmArgs);
                BootstrapMethodArgumentEditPanel confirmPanel = new BootstrapMethodArgumentEditPanel(bsmArgs);
                MessageUtil.confirm(confirmPanel, LangUtil.i18n("instruction.bootstrapMethodArgument"), () -> {
                    confirmPanel.getConfirm().run();
                    instruction.bsmArgs = bsmArgs.getWrapper();
                }, () -> {
                });
            });
        }});
        setConfirm(() -> {
            instruction.name = name.getText();
            instruction.desc = description.getText();
            return true;
        });
    }

    @Override
    public List<String> getOpcodes() {
        return Collections.singletonList("INVOKEDYNAMIC");
    }
}
