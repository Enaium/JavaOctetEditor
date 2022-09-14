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

package cn.enaium.joe.gui.panel.menu.file;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.MainFX;
import cn.enaium.joe.config.extend.ApplicationConfig;
import cn.enaium.joe.task.InputJarTask;
import cn.enaium.joe.util.LangUtil;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Set;

/**
 * @author Enaium
 * @since 0.9.0
 */
public class LoadRecentMenu extends Menu {
    public LoadRecentMenu() {
        super(LangUtil.i18n("menu.file.loadRecent"));
        update();
        setOnShowing(event -> {
            update();
        });
    }

    private void update() {
        Set<String> loadRecent = MainFX.getInstance().config.getByClass(ApplicationConfig.class).loadRecent.getValue();
        getItems().clear();
        for (String s : loadRecent) {
            getItems().add(new MenuItem(s) {{
                setOnAction(e -> {
                    File file = new File(s);
                    if (file.exists()) {
                        MainFX.getInstance().task.submit(new InputJarTask(file));
                    } else {
                        loadRecent.remove(s);
                    }
                });
            }});
        }
    }
}
