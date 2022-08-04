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

import cn.enaium.joe.util.LangUtil;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Enaium
 */
public class ContactDialog extends Dialog {
    public ContactDialog() {
        super(LangUtil.i18n("menu.help.contact"));
        setSize(290, 150);
        setContentPane(new JPanel(new GridLayout(1, 0)) {{
            add(new JPanel(new BorderLayout()) {{
                add(new JLabel() {{
                    setIcon(new FlatSVGIcon("icons/github.svg"));
                }}, BorderLayout.WEST);
                add(new JLabel("<html><a>https://github.com/Enaium/JavaOctetEditor</a></html>") {{
                    addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            try {
                                Desktop.getDesktop().browse(new URI("https://github.com/Enaium/JavaOctetEditor"));
                            } catch (IOException | URISyntaxException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    });
                }});
            }});
        }});
    }
}
