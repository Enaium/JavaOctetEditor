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

package cn.enaium.joe.dialog;

import cn.enaium.joe.util.JMenuUtil;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.wrapper.ObjectWrapper;
import org.objectweb.asm.tree.AnnotationNode;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class AnnotationListDialog extends Dialog {
    public AnnotationListDialog(List<AnnotationNode> annotationNodes) {
        super("Annotation");
        setLayout(new BorderLayout());
        DefaultTableModel dm = new DefaultTableModel(new Object[][]{}, new String[]{"Description", "Has Value"});
        for (AnnotationNode annotationNode : annotationNodes) {
            dm.addRow(new Object[]{new ObjectWrapper(annotationNode), annotationNode.values != null});
        }
        JTable jTable = new JTable(dm) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable.getTableHeader().setReorderingAllowed(false);
        jTable.getTableHeader().setResizingAllowed(false);
        add(new JScrollPane(jTable), BorderLayout.CENTER);
        JMenuUtil.addPopupMenu(jTable, () -> new JPopupMenu() {{
            add(new JMenuItem(LangUtil.i18n("button.edit")) {{
                addActionListener(e -> {
                    new AnnotationValueDialog(((AnnotationNode) ((ObjectWrapper) dm.getValueAt(jTable.getSelectedRow(), 0)).getWrapper()).values).setVisible(true);
                });
            }});
        }}, () -> jTable.getSelectedRow() != -1 && Boolean.parseBoolean(dm.getValueAt(jTable.getSelectedRow(), 1).toString()));
    }
}
