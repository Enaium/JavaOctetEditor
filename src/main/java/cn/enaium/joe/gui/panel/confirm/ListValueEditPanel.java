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

import cn.enaium.joe.util.ASMUtil;
import cn.enaium.joe.util.ListUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class ListValueEditPanel extends ConfirmPanel {
    public ListValueEditPanel(List<Object> objects) {
        setLayout(new BorderLayout());

        Class<?> type = ListUtil.getType(objects);

        JTextArea jTextArea = new JTextArea();
        int index = 0;
        for (Object object : objects) {
            jTextArea.append(object.toString());

            if (index != objects.size() - 1) {
                jTextArea.append("\n");
            }
        }
        add(new JScrollPane(jTextArea), BorderLayout.CENTER);
        setConfirm(() -> {
            objects.clear();
            for (String s : jTextArea.getText().replaceAll("^\\s+", "").split("\n")) {
                objects.add(ASMUtil.valueOf(type, s));
            }
        });
    }
}


