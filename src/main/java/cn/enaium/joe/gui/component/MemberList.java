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

package cn.enaium.joe.gui.component;

import cn.enaium.joe.dialog.FieldDialog;
import cn.enaium.joe.dialog.MethodDialog;
import cn.enaium.joe.util.OpcodeUtil;
import cn.enaium.joe.util.Pair;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class MemberList extends JList<Pair<ClassNode, Object>> {
    public MemberList() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && getSelectedIndex() != -1) {
                    Pair<ClassNode, Object> value = ((DefaultListModel<Pair<ClassNode, Object>>) getModel()).get(getSelectedIndex());
                    if (value.getValue() instanceof MethodNode) {
                        new MethodDialog(value.getKey(), ((MethodNode) value.getValue())).setVisible(true);
                    } else if (value.getValue() instanceof FieldNode) {
                        new FieldDialog(value.getKey(), ((FieldNode) value.getValue())).setVisible(true);
                    }
                }
            }
        });
        setCellRenderer((list, value, index, isSelected, cellHasFocus) -> new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0)) {{
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
    }
}
