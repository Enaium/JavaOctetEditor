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

import cn.enaium.joe.core.task.SearchOpcodeTask
import cn.enaium.joe.core.util.OpcodeUtil
import cn.enaium.joe.ui.Instance.jar
import cn.enaium.joe.ui.JavaOctetEditor.Companion.task
import cn.enaium.joe.ui.control.SearchResultList
import cn.enaium.joe.ui.util.i18n
import cn.enaium.joe.ui.util.warning
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import org.tbee.javafx.scene.layout.MigPane

/**
 * @author Enaium
 * @since 2.0.0
 */
class SearchOpcodeDialog : Dialog(i18n("search.opcode.title")) {
    init {
        content = BorderPane().apply {
            val searchResultList = SearchResultList()
            top = MigPane("fillx", "[fill][fill]").apply {
                add(Label(i18n("search.opcode")))
                val opcode = ComboBox<String>().apply {
                    isEditable = true
                    OpcodeUtil.OPCODE.forEach {
                        items.add(it.value)
                    }
                }
                add(opcode, "wrap")
                add(Button(i18n("menu.search")).apply {
                    setOnAction {
                        if (opcode.value.isBlank()) {
                            warning("Please input!")
                            return@setOnAction
                        }
                        searchResultList.items.clear()
                        task.submit(SearchOpcodeTask(jar, opcode.value)).thenAccept {
                            it.forEach { result ->
                                searchResultList.items.add(result)
                            }
                        }
                    }
                }, "span 2")
            }
            center = searchResultList
        }
    }
}