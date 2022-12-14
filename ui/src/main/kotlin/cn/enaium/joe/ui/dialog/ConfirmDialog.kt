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

import cn.enaium.joe.ui.pane.confirm.ConfirmPane
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox

/**
 * @author Enaium
 * @since 2.0.0
 */
class ConfirmDialog(confirmPane: ConfirmPane) : Dialog("Confirm") {
    init {
        content = BorderPane().apply {
            center = confirmPane
            bottom = HBox(10.0).apply {
                alignment = Pos.CENTER_RIGHT
                children.add(Button("OK").apply {
                    setOnAction {
                        confirmPane.confirm.invoke()
                        close()
                    }
                })
                children.add(Button("Cancel").apply {
                    setOnAction {
                        confirmPane.cancel.invoke()
                    }
                })
            }
        }
    }
}