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

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.dialog.TaskDialog;
import cn.enaium.joe.task.AbstractTask;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.Pair;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Enaium
 */
public class BottomPanel extends JPanel {
    private final JLabel scale = new JLabel();

    public BottomPanel() {
        super(new GridLayout(1, 4, 10, 10));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        JProgressBar jProgressBar = new JProgressBar() {{
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    new TaskDialog().setVisible(true);
                    super.mouseReleased(e);
                }
            });
        }};

        add(jProgressBar);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            List<Pair<AbstractTask<?>, CompletableFuture<?>>> task = JavaOctetEditor.getInstance().task.getTask();
            if (task.isEmpty()) {
                SwingUtilities.invokeLater(() -> {
                    jProgressBar.setValue(0);
                    jProgressBar.setStringPainted(true);
                    jProgressBar.setString("");
                    jProgressBar.repaint();
                });
            } else {
                Pair<AbstractTask<?>, CompletableFuture<?>> classCompletableFuturePair = task.get(0);
                SwingUtilities.invokeLater(() -> {
                    int progress = classCompletableFuturePair.getFirst().getProgress();
                    jProgressBar.setValue(progress);
                    jProgressBar.setStringPainted(true);
                    jProgressBar.setString(String.format("%s:%s", classCompletableFuturePair.getFirst().getName(), progress) + "%");
                    jProgressBar.repaint();
                });
            }
        }, 1, 1, TimeUnit.MILLISECONDS);

        add(scale);
        JLabel label = new JLabel("\u00A9 Enaium 2022");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        add(label);
    }
}
