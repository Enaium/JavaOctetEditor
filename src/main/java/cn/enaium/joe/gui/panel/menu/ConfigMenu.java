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

package cn.enaium.joe.gui.panel.menu;

import cn.enaium.joe.MainFX;
import cn.enaium.joe.config.Config;
import cn.enaium.joe.stage.ConfigDialog;
import cn.enaium.joe.util.LangUtil;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * @author Enaium
 * @since 0.7.0
 */
public class ConfigMenu extends Menu {
    public ConfigMenu() {
        super(LangUtil.i18n("menu.config"));
        for (Config value : MainFX.getInstance().config.getConfig().values()) {
            getItems().add(new MenuItem(value.getName()) {{
                setOnAction(e -> new ConfigDialog(value).show());
            }});
        }
    }
}
