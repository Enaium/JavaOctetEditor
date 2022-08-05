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
import cn.enaium.joe.config.extend.CFRConfig;
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

    public FileTabbedPanel fileTabbedPanel = new FileTabbedPanel();

    public FileTreePanel fileTreePanel = new FileTreePanel();

    public BottomPanel bottomPanel = new BottomPanel();

    public ConfigManager configManager = new ConfigManager();

    public JavaOctetEditor() {
        instance = this;
    }

    public void run() {
        configManager.load();
        Runtime.getRuntime().addShutdownHook(new Thread(configManager::save));

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
                MessageUtil.confirm(LangUtil.i18n("dialog.wantCloseWindow"), "WARNING", () -> {
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
