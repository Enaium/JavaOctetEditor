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

package cn.enaium.joe.ui.control.menu

import cn.enaium.joe.ui.control.menu.file.LoadMenuItem
import cn.enaium.joe.ui.control.menu.file.LoadRecentMenuItem
import cn.enaium.joe.ui.control.menu.file.SaveAllSourceMenuItem
import cn.enaium.joe.ui.control.menu.file.SaveMenuItem
import cn.enaium.joe.ui.util.i18n
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem

/**
 * @author Enaium
 */
class FileMenu : Menu(i18n("menu.file")) {
    init {
        items.add(LoadMenuItem())
        items.add(LoadRecentMenuItem())
        items.add(SaveMenuItem())
        items.add(SaveAllSourceMenuItem())
    }
}