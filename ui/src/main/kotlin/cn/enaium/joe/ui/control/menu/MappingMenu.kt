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

import cn.enaium.joe.core.task.RemappingTask
import cn.enaium.joe.ui.Instance.jar
import cn.enaium.joe.ui.JavaOctetEditor
import cn.enaium.joe.ui.JavaOctetEditor.Companion.event
import cn.enaium.joe.ui.JavaOctetEditor.Companion.task
import cn.enaium.joe.ui.event.LoadJar
import cn.enaium.joe.ui.util.i18n
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.stage.FileChooser
import net.fabricmc.mappingio.format.MappingFormat
import java.io.File

/**
 * @author Enaium
 * @since 2.0.0
 */
class MappingMenu : Menu(i18n("menu.mapping")) {
    init {
        MappingFormat.values().forEach { value ->
            items.add(MenuItem(value.name).apply {
                setOnAction {
                    val fileChooser = FileChooser()
                    fileChooser.title = "Open Mapping File"
                    fileChooser.initialDirectory = File(System.getProperty("user.home"))
                    fileChooser.extensionFilters.addAll(
                        FileChooser.ExtensionFilter(
                            "Mapping file(*.txt,*.map,*.mapping,*.pro,*.srg)",
                            "*.txt",
                            "*.map",
                            "*.mapping",
                            "*.pro",
                            "*.srg",
                            "*.tsrg",
                            "*.tiny",
                            "*.tiyv2"
                        )
                    )
                    val showOpenDialog = fileChooser.showOpenDialog(JavaOctetEditor.stage)
                    if (jar != null && showOpenDialog != null) {
                        task.submit(RemappingTask(jar, showOpenDialog, value)).thenAccept {
                            event.call(LoadJar(it))
                        }
                    }
                }
            })
        }
    }
}