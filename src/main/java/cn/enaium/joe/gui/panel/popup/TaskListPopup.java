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
import cn.enaium.joe.task.AbstractTask;
import cn.enaium.joe.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class TaskListPopup extends AbstractPopup {
    public TaskListPopup() {
        super("TaskList");
        JList<AbstractTask<?>> comp = new JList<>();
        comp.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> new JProgressBar() {{
            setValue(value.getProgress());
            setStringPainted(true);
            setString(String.format("%s:%s", value.getName(), value.getProgress()) + "%");
        }});
        add(new JScrollPane(comp), BorderLayout.CENTER);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> {
                DefaultListModel<AbstractTask<?>> abstractTaskDefaultListModel = new DefaultListModel<>();
                for (Pair<AbstractTask<?>, CompletableFuture<?>> abstractTaskCompletableFuturePair : JavaOctetEditor.getInstance().task.getTask()) {
                    abstractTaskDefaultListModel.addElement(abstractTaskCompletableFuturePair.getKey());
                }
                comp.setModel(abstractTaskDefaultListModel);
            });
        }, 1, 1, TimeUnit.MILLISECONDS);
    }
}
