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

import cn.enaium.joe.gui.component.LabelNodeComboBox;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.MessageUtil;
import cn.enaium.joe.wrapper.LabelNodeWrapper;
import org.objectweb.asm.tree.LabelNode;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class LookupSwitchEditPanel extends ConfirmPanel {
    public LookupSwitchEditPanel(List<Integer> keys, List<LabelNode> labels) {
        setLayout(new BorderLayout());
        DefaultTableModel dm = new DefaultTableModel(new Object[][]{}, new String[]{"Key", "Label"});
        JTable jTable = new JTable(dm) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable.getTableHeader().setReorderingAllowed(false);
        jTable.getTableHeader().setResizingAllowed(false);
        for (int i = 0; i < keys.size(); i++) {
            dm.addRow(new Object[]{keys.get(i), new LabelNodeWrapper(labels.get(i))});
        }
        add(new JScrollPane(jTable), BorderLayout.CENTER);
        add(new JPanel() {{
            add(new JButton(LangUtil.i18n("button.add")) {{
                addActionListener(e -> {
                    MessageUtil.confirm(new ConfirmPanel() {{
                        JSpinner key = new JSpinner();
                        add(key);
                        LabelNodeComboBox value = new LabelNodeComboBox(labels.get(0), null);
                        add(value);
                        setConfirm(() -> {
                            Object selectedItem = value.getSelectedItem();
                            if (selectedItem != null) {
                                dm.addRow(new Object[]{key.getValue(), selectedItem});
                            }
                        });
                    }}, "Add");
                });
            }});
            add(new JButton(LangUtil.i18n("button.remove")) {{
                addActionListener(e -> {
                    if (jTable.getSelectedRow() != -1) {
                        dm.removeRow(jTable.getSelectedRow());
                    }
                });
            }});
        }}, BorderLayout.SOUTH);
        setConfirm(() -> {
            keys.clear();
            labels.clear();
            for (int i = 0; i < jTable.getRowCount(); i++) {
                keys.add(Integer.parseInt(jTable.getValueAt(i, 0).toString()));
                labels.add(((LabelNodeWrapper) jTable.getValueAt(i, 1)).getWrapper());
            }
        });
    }
}
