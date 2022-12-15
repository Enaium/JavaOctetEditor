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

package cn.enaium.joe.ui.control

import cn.enaium.joe.core.model.SearchResultModel
import cn.enaium.joe.ui.JavaOctetEditor.Companion.event
import cn.enaium.joe.ui.cell.SearchResultListCell
import cn.enaium.joe.ui.event.ResultJump
import cn.enaium.joe.ui.util.ColorUtil
import cn.enaium.joe.ui.util.i18n
import javafx.scene.control.ContextMenu
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.control.MenuItem
import javafx.scene.text.Text
import javafx.scene.text.TextFlow
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.MethodInsnNode
import java.awt.Color

/**
 * @author Enaium
 * @since 2.0.0
 */
class SearchResultList : ListView<SearchResultModel>() {
    init {
        setCellFactory {
            SearchResultListCell()
        }
        contextMenu = ContextMenu().apply {
            items.add(MenuItem(i18n("context.result.jump")).apply {
                setOnAction {
                    selectionModel.selectedItem?.let {
                        event.call(ResultJump(selectionModel.selectedItem.classNode))
                    }
                }
            })
        }
    }
}