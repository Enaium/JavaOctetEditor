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

package cn.enaium.joe;

import cn.enaium.joe.config.ConfigManager;
import cn.enaium.joe.gui.panel.BottomPanel;
import cn.enaium.joe.gui.panel.LeftPanel;
import cn.enaium.joe.gui.panel.file.tabbed.FileTabbedPanel;
import cn.enaium.joe.gui.panel.file.tree.FileTreePanel;
import cn.enaium.joe.gui.panel.menu.ConfigMenu;
import cn.enaium.joe.gui.panel.menu.FileMenu;
import cn.enaium.joe.gui.panel.menu.HelpMenu;
import cn.enaium.joe.gui.panel.menu.SearchMenu;
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.MessageUtil;
import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Enaium
 */
public class JavaOctetEditor {
    private static JavaOctetEditor instance;

    public static final String TITLE = "JavaOctetEditor";

    public JFrame window = new JFrame(TITLE);

    public Jar jar;

    public FileTabbedPanel fileTabbedPanel;

    public FileTreePanel fileTreePanel;

    public BottomPanel bottomPanel;

    public ConfigManager config;

    public JavaOctetEditor() {
        instance = this;
        config = new ConfigManager();
        config.load();
        Runtime.getRuntime().addShutdownHook(new Thread(config::save));
        fileTabbedPanel = new FileTabbedPanel();
        fileTreePanel = new FileTreePanel();
        bottomPanel = new BottomPanel();
    }

    public void run() {

        ToolTipManager.sharedInstance().setInitialDelay(0);

        AbstractTokenMakerFactory abstractTokenMakerFactory = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
        abstractTokenMakerFactory.putMapping("text/custom", BytecodeTokenMaker.class.getName());

        window.setJMenuBar(new JMenuBar() {{
            add(new FileMenu());
            add(new SearchMenu());
            add(new ConfigMenu());
            add(new HelpMenu());
        }});

        window.setContentPane(new JPanel(new BorderLayout()) {
            {
                add(new JSplitPane() {{
                    setDividerLocation(150);
                    setLeftComponent(new LeftPanel());
                    setRightComponent(fileTabbedPanel);
                }}, BorderLayout.CENTER);
                add(bottomPanel, BorderLayout.SOUTH);
            }
        });
        window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MessageUtil.confirm(LangUtil.i18n("dialog.wantCloseWindow"), LangUtil.i18n("warning"), () -> {
                    window.dispose();
                    System.exit(0);
                }, () -> {
                });
            }
        });
        window.setSize(800, 500);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static JavaOctetEditor getInstance() {
        return instance;
    }
}
