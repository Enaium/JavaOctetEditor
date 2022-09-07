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

package cn.enaium.joe.gui.panel.popup;

import cn.enaium.joe.JavaOctetEditor;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Enaium
 * @since 1.2.0
 */
public abstract class AbstractPopup extends JPanel {
    private Popup popup;

    protected AbstractPopup(String name) {
        super(new BorderLayout());
        add(new JPanel(new BorderLayout()) {{
            setBorder(new EmptyBorder(5, 5, 5, 5));
            setBackground(new JButton().getBackground());
            add(new JLabel(name), BorderLayout.CENTER);
            add(new JLabel(new FlatSVGIcon("icons/minimize.svg") {{
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        destroy();
                    }
                });
            }}), BorderLayout.EAST);

        }}, BorderLayout.NORTH);
    }

    public void place(int x, int y) {
        if (popup == null) {
            popup = PopupFactory.getSharedInstance().getPopup(JavaOctetEditor.getInstance().window, this, x, y);
            popup.show();
        }
    }

    public void destroy() {
        if (popup != null) {
            popup.hide();
            popup = null;
        }
    }
}
