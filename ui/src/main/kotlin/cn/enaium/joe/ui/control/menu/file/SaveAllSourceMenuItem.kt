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

package cn.enaium.joe.ui.control.menu.file

import cn.enaium.joe.core.task.SaveAllSourceTask
import cn.enaium.joe.ui.Instance.jar
import cn.enaium.joe.ui.JavaOctetEditor.Companion.config
import cn.enaium.joe.ui.JavaOctetEditor.Companion.stage
import cn.enaium.joe.ui.JavaOctetEditor.Companion.task
import cn.enaium.joe.ui.util.i18n
import javafx.scene.control.MenuItem
import javafx.stage.FileChooser
import java.io.File

/**
 * @author Enaium
 * @since 2.0.0
 */
class SaveAllSourceMenuItem : MenuItem(i18n("menu.file.saveAllSource")) {
    init {
        setOnAction {
            jar?.let {
                val fileChooser = FileChooser()
                fileChooser.title = "Open Resource File"
                fileChooser.initialDirectory = File(System.getProperty("user.home"))
                fileChooser.extensionFilters.addAll(
                    FileChooser.ExtensionFilter("Zip File(*.zip,*.jar)", "*.jar", "*.zip")
                )
                val showSaveDialog = fileChooser.showSaveDialog(stage)
                showSaveDialog?.let {
                    task.submit(SaveAllSourceTask(jar, showSaveDialog, config))
                }
            }
        }
    }
}