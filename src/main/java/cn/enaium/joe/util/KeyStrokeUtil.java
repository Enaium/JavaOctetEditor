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

package cn.enaium.joe.util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Enaium
 * @since 1.0.0
 */
public class KeyStrokeUtil {

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    public static void register(JComponent component, KeyStroke keyStroke, Runnable runnable) {
        String key = String.valueOf(ATOMIC_INTEGER.incrementAndGet());
        InputMap inputMap = component.getInputMap(JComponent.WHEN_FOCUSED);
        inputMap.put(keyStroke, key);
        ActionMap actionMap = component.getActionMap();
        actionMap.put(key, Util.ofAction(runnable));
    }
}
