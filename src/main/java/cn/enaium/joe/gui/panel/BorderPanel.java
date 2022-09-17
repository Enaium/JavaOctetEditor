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

import javax.swing.*;
import java.awt.*;

/**
 * @author Enaium
 * @since 1.3.0
 */
public class BorderPanel extends JPanel {
    public BorderPanel() {
        super(new BorderLayout());
    }

    public void setCenter(Component component) {
        add(component, BorderLayout.CENTER);
    }

    public void setTop(Component component) {
        add(component, BorderLayout.NORTH);
    }

    public void setBottom(Component component) {
        add(component, BorderLayout.SOUTH);
    }

    public void setLeft(Component component) {
        add(component, BorderLayout.WEST);
    }

    public void setRight(Component component) {
        add(component, BorderLayout.EAST);
    }
}
