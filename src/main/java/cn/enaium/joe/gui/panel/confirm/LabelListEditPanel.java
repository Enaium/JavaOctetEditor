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

package cn.enaium.joe.gui.panel.confirm;

import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.MessageUtil;
import cn.enaium.joe.wrapper.LabelNodeWrapper;
import cn.enaium.joe.wrapper.Wrapper;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class LabelListEditPanel extends ConfirmPanel {
    public LabelListEditPanel(List<LabelNode> labelNodes, InsnList instructions) {
        setLayout(new BorderLayout());
        DefaultListModel<LabelNodeWrapper> labelNodeWrapperDefaultListModel = new DefaultListModel<>();
        JList<LabelNodeWrapper> labelNodeWrapperJList = new JList<>(labelNodeWrapperDefaultListModel);
        for (LabelNode labelNode : labelNodes) {
            labelNodeWrapperDefaultListModel.addElement(new LabelNodeWrapper(labelNode));
        }
        add(new JScrollPane(labelNodeWrapperJList), BorderLayout.CENTER);
        add(new JPanel() {{
            add(new JButton(LangUtil.i18n("button.add")) {{
                addActionListener(e -> {
                    DefaultComboBoxModel<LabelNodeWrapper> labelNodeWrapperDefaultComboBoxModel = new DefaultComboBoxModel<>();
                    JComboBox<LabelNodeWrapper> labelNodeWrapperJComboBox = new JComboBox<>(labelNodeWrapperDefaultComboBoxModel);
                    for (AbstractInsnNode instruction : instructions) {
                        if (instruction instanceof LabelNode) {
                            labelNodeWrapperDefaultComboBoxModel.addElement(new LabelNodeWrapper((LabelNode) instruction));
                        }
                    }
                    MessageUtil.confirm(labelNodeWrapperJComboBox, "Label", () -> {
                        if (labelNodeWrapperJComboBox.getSelectedItem() != null) {
                            labelNodeWrapperDefaultListModel.addElement(((LabelNodeWrapper) labelNodeWrapperJComboBox.getSelectedItem()));
                        }
                    }, () -> {
                    });
                });
            }});
            add(new JButton(LangUtil.i18n("button.remove")) {{
                addActionListener(e -> {
                    if (labelNodeWrapperJList.getSelectedIndex() != -1) {
                        labelNodeWrapperDefaultListModel.remove(labelNodeWrapperJList.getSelectedIndex());
                    }
                });
            }});
        }}, BorderLayout.SOUTH);
        setConfirm(() -> {
            labelNodes.clear();
            for (int i = 0; i < labelNodeWrapperDefaultListModel.size(); i++) {
                labelNodes.add(labelNodeWrapperDefaultListModel.get(i).getWrapper());
            }
        });
    }
}
