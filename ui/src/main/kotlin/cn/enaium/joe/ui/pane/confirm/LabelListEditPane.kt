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

package cn.enaium.joe.ui.pane.confirm

import cn.enaium.joe.core.wrapper.LabelNodeWrapper
import cn.enaium.joe.ui.dialog.ConfirmDialog
import cn.enaium.joe.ui.util.i18n
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.ListView
import javafx.scene.layout.HBox
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.LabelNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class LabelListEditPane(labelList: MutableList<LabelNode>, insnList: InsnList) : ConfirmPane() {
    init {
        val list = ListView<LabelNodeWrapper>().apply {
            labelList.forEach {
                items.add(LabelNodeWrapper(it))
            }
        }
        center = list
        bottom = HBox().apply {
            children.add(Button(i18n("button.add")).apply {
                setOnAction {
                    val comboBox = ComboBox<LabelNodeWrapper>().apply {

                        insnList.forEach {
                            if (it is LabelNode) {
                                items.add(LabelNodeWrapper(it))
                            }
                        }
                    }
                    ConfirmDialog(ConfirmPane().apply {
                        center = comboBox

                        confirm = {
                            comboBox.value?.let {
                                list.items.add(comboBox.value)
                            }
                        }
                    }).show()
                }
            })
            children.add(Button(i18n("button.remove")).apply {
                list.selectionModel.selectedItem?.let {
                    list.items.removeAt(list.selectionModel.selectedIndex)
                }
            })
        }

        confirm = {
            labelList.clear()
            list.items.forEach {
                labelList.add(it.wrapper)
            }
        }
    }
}