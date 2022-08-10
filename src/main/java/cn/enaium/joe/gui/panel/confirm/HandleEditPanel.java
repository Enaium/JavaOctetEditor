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

import cn.enaium.joe.util.OpcodeUtil;
import cn.enaium.joe.wrapper.Wrapper;
import org.objectweb.asm.Handle;

import javax.swing.*;
import java.awt.*;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class HandleEditPanel extends ConfirmPanel {
    public HandleEditPanel(Wrapper<Handle> wrapper) {
        Handle handle = wrapper.getWrapper();
        setLayout(new BorderLayout());
        JPanel left = new JPanel(new GridLayout(0, 1));
        JPanel right = new JPanel(new GridLayout(0, 1));
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.CENTER);
        left.add(new JLabel("Tag:"));
        JComboBox<String> tag = new JComboBox<>(OpcodeUtil.HANDLE.values().toArray(new String[0]));
        tag.setSelectedItem(OpcodeUtil.HANDLE.get(handle.getTag()));
        right.add(tag);
        left.add(new JLabel("Owner:"));
        JTextField owner = new JTextField(handle.getOwner());
        right.add(owner);
        left.add(new JLabel("Name:"));
        JTextField name = new JTextField(handle.getName());
        right.add(name);
        left.add(new JLabel("Description:"));
        JTextField description = new JTextField(handle.getDesc());
        right.add(description);
        left.add(new JLabel("Interface:"));
        JCheckBox isInterface = new JCheckBox() {{
            setSelected(handle.isInterface());
            setHorizontalAlignment(JCheckBox.RIGHT);
        }};
        right.add(isInterface);
        setConfirm(() -> {
            if (tag.getSelectedItem() != null) {
                wrapper.setWrapper(new Handle(OpcodeUtil.reverse(OpcodeUtil.HANDLE).get(tag.getSelectedItem().toString()), owner.getText(), name.getText(), description.getText(), isInterface.isSelected()));
            }
        });
    }
}
