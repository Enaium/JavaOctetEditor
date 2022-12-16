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

package cn.enaium.joe.ui.dialog

import cn.enaium.joe.ui.util.loadSVG
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.control.Label
import org.tbee.javafx.scene.layout.MigPane
import java.awt.Desktop
import java.net.URI

/**
 * @author Enaium
 * @since 2.0.0
 */
class ContactDialog : Dialog("About") {
    init {
        content = MigPane().apply {
            fun add(name: String, icon: Node, link: String) {
                add(Label().apply {
                    graphic = icon
                })
                add(Label(name))
                add(Label(link).apply {
                    cursor = Cursor.HAND
                    setOnMouseClicked {
                        Desktop.getDesktop().browse(URI(link));
                    }
                })
            }
            add("GitHub", loadSVG("icons/github.svg"), "https://github.com/Enaium/JavaOctetEditor")
        }
    }
}