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

import cn.enaium.joe.gui.panel.confirm.ListValueEditPanel;
import cn.enaium.joe.gui.panel.confirm.ValueEditPanel;
import cn.enaium.joe.util.JMenuUtil;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.ListUtil;
import cn.enaium.joe.util.MessageUtil;
import cn.enaium.joe.wrapper.ObjectWrapper;
import org.objectweb.asm.tree.AnnotationNode;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class AnnotationValueDialog extends Dialog {
    public AnnotationValueDialog(List<Object> objects) {
        super("Annotation Value");
        DefaultTableModel dm = new DefaultTableModel(new Object[][]{}, new String[]{"Description", "Value"});

        for (Object object : objects) {
            int index = objects.indexOf(object);
            if (index % 2 == 1) {
                dm.addRow(new Object[]{objects.get(index - 1), new ObjectWrapper(object)});
            }
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
                    ObjectWrapper valueAt = (ObjectWrapper) dm.getValueAt(jTable.getSelectedRow(), 1);
                    if (valueAt.getWrapper() instanceof ArrayList<?>) {
                        @SuppressWarnings("unchecked") List<Object> wrapper = (List<Object>) valueAt.getWrapper();
                        if (ListUtil.getType(wrapper) == AnnotationNode.class) {
                            new AnnotationListDialog(wrapper.stream().map(it -> ((AnnotationNode) it)).collect(Collectors.toList())).setVisible(true);
                        } else {
                            MessageUtil.confirm(new ListValueEditPanel(wrapper), "List");
                        }
                    } else {
                        int index = (jTable.getSelectedRow() + 1) * 2 - 1;
                        if (valueAt.getWrapper().getClass().isArray()) {
                            ArrayList<Object> newList = new ArrayList<>(Arrays.asList(((Object[]) valueAt.getWrapper())));
                            MessageUtil.confirm(new ListValueEditPanel(newList), "List", () -> {
                                String[] strings = newList.stream().map(Object::toString).toArray(String[]::new);
                                valueAt.setWrapper(strings);
                                objects.set(index, strings);
                            });
                        } else {
                            ObjectWrapper objectWrapper = new ObjectWrapper(valueAt.getWrapper());
                            ValueEditPanel confirmPanel = new ValueEditPanel(objectWrapper);
                            MessageUtil.confirm(confirmPanel, LangUtil.i18n("button.edit"), () -> {
                                valueAt.setWrapper(objectWrapper.getWrapper());
                                objects.set(index, objectWrapper.getWrapper());
                            });
                        }
                    }
                });
            }});
        }}, () -> jTable.getSelectedRow() != -1);
    }
}
