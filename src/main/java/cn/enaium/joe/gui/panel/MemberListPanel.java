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

package cn.enaium.joe.gui.panel;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.dialog.CallTreeDialog;
import cn.enaium.joe.dialog.FieldDialog;
import cn.enaium.joe.dialog.MethodDialog;
import cn.enaium.joe.event.events.FileTabbedSelectEvent;
import cn.enaium.joe.gui.panel.file.tabbed.tab.classes.ClassTabPanel;
import cn.enaium.joe.util.JMenuUtil;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.OpcodeUtil;
import cn.enaium.joe.util.Pair;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class MemberListPanel extends BorderPanel {
    public MemberListPanel() {

        JList<Pair<ClassNode, Object>> memberList = new JList<>();

        memberList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && memberList.getSelectedIndex() != -1) {
                    edit(memberList.getSelectedValue());
                }
            }
        });

        JMenuUtil.addPopupMenu(memberList, () -> getPopupMenu(memberList.getSelectedValue()), () -> memberList.getSelectedIndex() != -1);

        memberList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0)) {{
            if (isSelected) {
                setBackground(list.getSelectionBackground());
            } else {
                setBackground(list.getBackground());
            }
            String name = null;
            Icon icon = null;
            int access = 0;
            if (value.getValue() instanceof FieldNode) {
                name = ((FieldNode) value.getValue()).name;
                icon = new FlatSVGIcon("icons/field.svg");
                access = ((FieldNode) value.getValue()).access;
            } else if (value.getValue() instanceof MethodNode) {
                name = ((MethodNode) value.getValue()).name;
                icon = new FlatSVGIcon("icons/method.svg");
                access = ((MethodNode) value.getValue()).access;
            }
            Icon accessIcon = null;
            if (OpcodeUtil.isPublic(access)) {
                accessIcon = new FlatSVGIcon("icons/public.svg");
            } else if (OpcodeUtil.isPrivate(access)) {
                accessIcon = new FlatSVGIcon("icons/private.svg");
            } else if (OpcodeUtil.isProtected(access)) {
                accessIcon = new FlatSVGIcon("icons/protected.svg");
            } else if (OpcodeUtil.isStatic(access) && "<clinit>".equals(name)) {
                accessIcon = new FlatSVGIcon("icons/static.svg");
            } else {
                accessIcon = new FlatSVGIcon("icons/cyan_dot.svg");
            }

            add(new JLabel(icon));
            add(new JLabel(accessIcon));
            add(new JLabel(name));
        }});

        JavaOctetEditor.getInstance().event.register(FileTabbedSelectEvent.class, (Consumer<FileTabbedSelectEvent>) event -> {
            if (event.getSelect() instanceof ClassTabPanel) {
                ClassTabPanel select = (ClassTabPanel) event.getSelect();
                ClassNode classNode = select.classNode;
                memberList.setModel(new DefaultListModel<Pair<ClassNode, Object>>() {{
                    for (FieldNode field : classNode.fields) {
                        addElement(new Pair<>(classNode, field));
                    }

                    for (MethodNode method : classNode.methods) {
                        addElement(new Pair<>(classNode, method));
                    }
                }});
            } else {
                ((DefaultListModel<Pair<ClassNode, Object>>) memberList.getModel()).clear();
            }
            memberList.repaint();
        });
        setCenter(new JScrollPane(memberList));
    }

    public static JPopupMenu getPopupMenu(Pair<ClassNode, Object> selectedValue) {
        return new JPopupMenu() {{
            add(new JMenuItem(LangUtil.i18n("button.edit")) {{
                addActionListener(e -> edit(selectedValue));
            }});
            add(new JMenuItem(LangUtil.i18n("popup.member.callTree")) {{
                addActionListener(e -> {
                    if (selectedValue.getValue() instanceof MethodNode) {
                        new CallTreeDialog(selectedValue.getKey(), (MethodNode) selectedValue.getValue()).setVisible(true);
                    }
                });
            }});
        }};
    }

    private static void edit(Pair<ClassNode, Object> value) {
        if (value.getValue() instanceof MethodNode) {
            new MethodDialog(value.getKey(), ((MethodNode) value.getValue())).setVisible(true);
        } else if (value.getValue() instanceof FieldNode) {
            new FieldDialog(value.getKey(), ((FieldNode) value.getValue())).setVisible(true);
        }
    }
}
