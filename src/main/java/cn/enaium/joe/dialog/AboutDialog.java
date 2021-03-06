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

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author Enaium
 */
public class AboutDialog extends Dialog {
    public AboutDialog() {
        super("About");
        setContentPane(new JPanel(new GridLayout(2, 0)) {{
            setBorder(new EmptyBorder(10, 10, 10, 10));
            add(new JPanel(new GridLayout(5, 0)) {{
                add(new JLabel("System"));
                add(new JLabel("Information about the OS"));
                add(new JPanel(new BorderLayout()) {{
                    add(new JLabel("Name"), BorderLayout.WEST);
                    add(new JLabel(System.getProperty("os.name")), BorderLayout.EAST);
                }});
                add(new JPanel(new BorderLayout()) {{
                    add(new JLabel("Architecture"), BorderLayout.WEST);
                    add(new JLabel(System.getProperty("os.arch")), BorderLayout.EAST);
                }});
                add(new JSeparator());
            }});
            add(new JPanel(new GridLayout(6, 0)) {{
                add(new JLabel("Java"));
                add(new JLabel("Information about the JVM"));
                add(new JPanel(new BorderLayout()) {{
                    add(new JLabel("Version"), BorderLayout.WEST);
                    add(new JLabel(System.getProperty("java.version")), BorderLayout.EAST);
                }});
                add(new JPanel(new BorderLayout()) {{
                    add(new JLabel("VM Name"), BorderLayout.WEST);
                    add(new JLabel(System.getProperty("java.vm.name")), BorderLayout.EAST);
                }});
                add(new JPanel(new BorderLayout()) {{
                    add(new JLabel("VM Vendor"), BorderLayout.WEST);
                    add(new JLabel(System.getProperty("java.vm.vendor")), BorderLayout.EAST);
                }});
                add(new JPanel(new BorderLayout()) {{
                    add(new JLabel("Home"), BorderLayout.WEST);
                    add(new JLabel(System.getProperty("java.home")), BorderLayout.EAST);
                }});
            }});
        }});
    }
}
