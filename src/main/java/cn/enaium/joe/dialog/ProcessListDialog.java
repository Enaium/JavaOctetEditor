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

import cn.enaium.joe.Main;
import cn.enaium.joe.util.JFileChooserUtil;
import cn.enaium.joe.util.JMenuUtil;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.MessageUtil;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.sun.tools.attach.*;
import com.sun.tools.attach.spi.AttachProvider;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Enaium
 * @since 1.1.0
 */
public class ProcessListDialog extends Dialog {
    public ProcessListDialog() {
        super("ProcessList");
        setLayout(new BorderLayout());
        DefaultListModel<Process> virtualMachineDescriptorDefaultListModel = new DefaultListModel<>();
        JList<Process> jList = new JList<>(virtualMachineDescriptorDefaultListModel);
        jList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> new JPanel(new BorderLayout()) {{
            setBorder(new EmptyBorder(5, 5, 5, 5));
            if (isSelected) {
                setBackground(list.getSelectionBackground());
            } else {
                setBackground(list.getBackground());
            }
            try {
                add(new JPanel(new BorderLayout()) {{
                    add(new JLabel() {{
                        setVerticalAlignment(JLabel.CENTER);
                        setIcon(new FlatSVGIcon("icons/openjdk.svg"));
                    }}, BorderLayout.CENTER);
                }}, BorderLayout.WEST);
                add(new JPanel(new BorderLayout()) {{
                    add(new JLabel(String.format("%s - %s", value.getVirtualMachineDescriptor().id(), value.getVirtualMachineDescriptor().displayName())), BorderLayout.NORTH);
                    add(new JLabel("VM:" + value.getVirtualMachine().getSystemProperties().getProperty("java.vm.name")), BorderLayout.CENTER);
                    add(new JLabel("Version:" + value.getVirtualMachine().getSystemProperties().getProperty("java.version")), BorderLayout.SOUTH);
                }}, BorderLayout.CENTER);
            } catch (IOException ignore) {

            }
        }});
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor virtualMachineDescriptor : list) {
            try {
                AttachProvider attachProvider = virtualMachineDescriptor.provider();
                VirtualMachine virtualMachine = attachProvider.attachVirtualMachine(virtualMachineDescriptor);
                virtualMachineDescriptorDefaultListModel.addElement(new Process(virtualMachineDescriptor, virtualMachine));
            } catch (AttachNotSupportedException e) {
                MessageUtil.error(e);
            } catch (IOException ignore) {

            }
        }

        JPopupMenu jPopupMenu = new JPopupMenu();
        jPopupMenu.add(new JMenuItem(LangUtil.i18n("popup.attach.properties")) {{
            addActionListener(e -> {
                try {
                    new Dialog(jList.getSelectedValue().getVirtualMachineDescriptor().displayName()) {{
                        setLayout(new BorderLayout());
                        DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Key", "Value"});
                        add(new JScrollPane(new JTable(defaultTableModel)), BorderLayout.CENTER);
                        for (Map.Entry<Object, Object> objectObjectEntry : jList.getSelectedValue().getVirtualMachine().getSystemProperties().entrySet()) {
                            defaultTableModel.addRow(new Object[]{objectObjectEntry.getKey(), objectObjectEntry.getValue()});
                        }
                    }}.setVisible(true);
                } catch (IOException ignore) {

                }
            });
        }});
        jPopupMenu.add(new JMenuItem(LangUtil.i18n("menu.attach")) {{
            addActionListener(e -> {
                MessageUtil.confirm("", LangUtil.i18n("menu.attach"), () -> {
                    try {
                        VirtualMachine virtualMachine = jList.getSelectedValue().getVirtualMachine();
                        virtualMachine.loadAgent(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getAbsolutePath());
                        virtualMachine.detach();
                    } catch (AgentLoadException | AgentInitializationException | IOException exception) {
                        MessageUtil.error(exception);
                    }
                    System.exit(0);
                });
            });
        }});
        jPopupMenu.add(new JMenuItem(LangUtil.i18n("popup.attach.external")) {{
            addActionListener(e -> {
                File show = JFileChooserUtil.show(JFileChooserUtil.Type.OPEN);
                if (show != null) {
                    try {
                        VirtualMachine virtualMachine = jList.getSelectedValue().getVirtualMachine();
                        virtualMachine.loadAgent(show.getAbsolutePath());
                        virtualMachine.detach();
                    } catch (AgentLoadException | AgentInitializationException |
                             IOException exception) {
                        MessageUtil.error(exception);
                    }
                    System.exit(0);
                }
            });
        }});
        JMenuUtil.addPopupMenu(jList, () -> jPopupMenu, () -> jList.getSelectedValue() != null);

        add(new JScrollPane(jList), BorderLayout.CENTER);
    }

    private static class Process {
        private final VirtualMachineDescriptor virtualMachineDescriptor;
        private final VirtualMachine virtualMachine;

        public Process(VirtualMachineDescriptor virtualMachineDescriptor, VirtualMachine virtualMachine) {
            this.virtualMachineDescriptor = virtualMachineDescriptor;
            this.virtualMachine = virtualMachine;
        }

        public VirtualMachineDescriptor getVirtualMachineDescriptor() {
            return virtualMachineDescriptor;
        }

        public VirtualMachine getVirtualMachine() {
            return virtualMachine;
        }
    }
}
