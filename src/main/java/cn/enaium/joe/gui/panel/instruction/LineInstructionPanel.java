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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class LineInstructionPanel extends AbstractInstructionPanel {
    public LineInstructionPanel(LineNumberNode instruction) {
        super(instruction);
        JSpinner spinner = new JSpinner();
        spinner.setValue(instruction.line);
        addComponent(new JLabel(LangUtil.i18n("instruction.line")), spinner);
        LabelNodeComboBox component = new LabelNodeComboBox(instruction, instruction.start);
        addComponent(new JLabel(LangUtil.i18n("instruction.start")), component);

        setConfirm(() -> {
            Object selectedItem = component.getSelectedItem();
            if (selectedItem != null) {
                instruction.line = Integer.parseInt(spinner.getValue().toString());
                instruction.start = ((LabelNodeWrapper) selectedItem).getWrapper();
                return true;
            }
            return false;
        });
    }

    @Override
    public List<String> getOpcodes() {
        return null;
    }
}
