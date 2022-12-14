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

package cn.enaium.joe.ui.pane


import cn.enaium.joe.ui.control.FileTree
import cn.enaium.joe.ui.pane.content.ContentTabPane
import cn.enaium.joe.ui.pane.side.SidePane
import javafx.scene.control.SplitPane
import javafx.scene.layout.BorderPane

/**
 * @author Enaium
 * @since 2.0.0
 */
class ProjectPane : BorderPane() {
    init {
        center = SplitPane().apply {
            items.add(FileTree())
            items.add(ContentTabPane())
            items.add(SidePane())
            setDividerPositions(.2, .9)
        }
    }
}