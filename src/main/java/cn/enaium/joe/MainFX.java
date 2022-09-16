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
import cn.enaium.joe.gui.component.FileTab;
import cn.enaium.joe.gui.component.FileTreeFX;
import cn.enaium.joe.gui.panel.CenterPanel;
import cn.enaium.joe.task.TaskManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Enaium
 * @since 1.3.0
 */
public class MainFX extends Application {

    private static MainFX instance;

    public ConfigManager config;
    public TaskManager task;

    public FileTreeFX fileTree;
    public FileTab fileTab;

    public Stage stage;

    @Override
    public void init() throws Exception {
        instance = this;
        config = new ConfigManager();
        config.load();
        task = new TaskManager();
        fileTree = new FileTreeFX();
        fileTab = new FileTab();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("JavaOctetEditor");
        stage.setScene(new Scene(new CenterPanel(), 1000, 600));
        stage.setOnCloseRequest(windowEvent -> System.exit(0));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static MainFX getInstance() {
        return instance;
    }
}
