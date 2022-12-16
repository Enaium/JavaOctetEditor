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
package cn.enaium.joe.ui

import cn.enaium.joe.core.config.ConfigManager
import cn.enaium.joe.core.task.TaskManager
import cn.enaium.joe.ui.config.extend.KeymapConfig
import cn.enaium.joe.ui.dialog.ConfirmDialog
import cn.enaium.joe.ui.event.EventManager
import cn.enaium.joe.ui.pane.MainPane
import cn.enaium.joe.ui.pane.confirm.ConfirmPane
import cn.enaium.joe.ui.util.addStyle
import cn.enaium.joe.ui.util.i18n
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.stage.Stage
import org.pmw.tinylog.Configurator
import org.pmw.tinylog.writers.ConsoleWriter
import org.pmw.tinylog.writers.FileWriter
import kotlin.system.exitProcess


/**
 * @author Enaium
 * @since 2.0.0
 */
class JavaOctetEditor : Application() {

    override fun start(primaryStage: Stage) {
        Configurator.currentConfig().writer(ConsoleWriter(), "[{date: HH:mm:ss.SSS}] {level} > {message}")
            .addWriter(FileWriter("latest.log"), "[{date: HH:mm:ss.SSS}] {level} > {message}").activate()


        stage = primaryStage

        config = ConfigManager().apply {
            setByClass(KeymapConfig())
        }
        config.load()
        Runtime.getRuntime().addShutdownHook(Thread(config::save))

        task = TaskManager()
        event = EventManager()

        create(primaryStage)

        primaryStage.setOnCloseRequest {
            ConfirmDialog(ConfirmPane().apply {
                center = Label(i18n("dialog.wantCloseWindow"))
                confirm = {
                    exitProcess(0)
                }
                cancel = {
                    it.consume()
                }
            }).showAndWait()
        }
    }

    private fun create(primaryStage: Stage) {
        val scene = Scene(MainPane(primaryStage), 1000.0, 600.0)
        primaryStage.title = Instance.NAME
        primaryStage.scene = scene
        primaryStage.show()
    }


    companion object {
        lateinit var stage: Stage
        lateinit var config: ConfigManager
        lateinit var task: TaskManager
        lateinit var event: EventManager
    }
}