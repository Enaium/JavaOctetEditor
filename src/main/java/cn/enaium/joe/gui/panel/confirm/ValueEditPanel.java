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

import cn.enaium.joe.wrapper.ObjectWrapper;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            try {
                Method valueOf = objectWrapper.getWrapper().getClass().getMethod("valueOf", String.class);
                objectWrapper.setWrapper(valueOf.invoke(null, jTextField.getText()));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
                if (objectWrapper.getWrapper() instanceof String) {
                    objectWrapper.setWrapper(jTextField.getText());
                }
            }
        });
    }
}
