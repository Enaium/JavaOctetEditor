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

package cn.enaium.joe.ui.pane.content


import cn.enaium.joe.ui.JavaOctetEditor.Companion.event
import cn.enaium.joe.ui.control.tree.ClassTreeItem
import cn.enaium.joe.ui.control.tree.FileTreeItem
import cn.enaium.joe.ui.event.ContentTabChange
import cn.enaium.joe.ui.event.SelectTreeItem
import cn.enaium.joe.ui.pane.content.classes.ClassTabPane
import cn.enaium.joe.ui.pane.content.resources.ResourceTabPane
import javafx.scene.control.Tab
import javafx.scene.control.TabPane

/**
 * @author Enaium
 * @since 2.0.0
 */
class ContentTabPane : TabPane() {
    init {
        event.register<SelectTreeItem> {
            when (it.item) {
                is ClassTreeItem -> {
                    Tab(it.item.toString()).apply {
                        content = ClassTabPane(it.item)
                    }
                }

                is FileTreeItem -> {
                    Tab(it.item.toString()).apply {
                        content = ResourceTabPane(it.item)
                    }
                }

                else -> {
                    null
                }
            }?.let { tab ->
                tabs.add(tab)
                selectionModel.select(tab)
            }
        }

        selectionModel.selectedItemProperty()
            .addListener { _, oldValue, newValue -> event.call(ContentTabChange(oldValue, newValue)) }
    }
}