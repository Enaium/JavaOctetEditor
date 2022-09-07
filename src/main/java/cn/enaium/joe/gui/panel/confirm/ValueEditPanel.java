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
import cn.enaium.joe.wrapper.ObjectWrapper;

import javax.swing.*;
import java.awt.*;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class ValueEditPanel extends ConfirmPanel {
    public ValueEditPanel(ObjectWrapper objectWrapper) {
        setLayout(new BorderLayout());
        JTextField jTextField = new JTextField(objectWrapper.getWrapper().toString());
        add(jTextField, BorderLayout.CENTER);
        setConfirm(() -> {
            objectWrapper.setWrapper(ASMUtil.toType(objectWrapper.getWrapper().getClass(), jTextField.getText()));
        });
    }
}
