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

package cn.enaium.joe.gui.panel.file.tabbed.tab.resources;

import cn.enaium.joe.gui.panel.file.tree.node.FileTreeNode;
import cn.enaium.joe.util.ASyncUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * @author Enaium
 * @since 0.7.0
 */
public class HexTablePanel extends JPanel {
    public HexTablePanel(FileTreeNode fileTreeNode) {
        super(new BorderLayout());
        DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][]{}, new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"});
        JTable jTable = new JTable(defaultTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable.getTableHeader().setReorderingAllowed(false);
        add(new JScrollPane(jTable), BorderLayout.CENTER);

        ASyncUtil.execute(() -> {
            int row = 0;
            Object[] array = new Object[16];
            byte[] data = fileTreeNode.getData();
            for (int i = 0; i < data.length; i++) {
                byte b = data[i];
                array[row] = String.format("%02X", b);
                if (row == 15 || i == fileTreeNode.getData().length - 1) {
                    defaultTableModel.addRow(array);
                    row = 0;
                    array = new Object[16];
                    continue;
                }
                row++;
            }
        });
    }
}
