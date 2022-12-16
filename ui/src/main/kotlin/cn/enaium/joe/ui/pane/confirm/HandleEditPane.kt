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

import cn.enaium.joe.core.util.OpcodeUtil
import cn.enaium.joe.core.wrapper.Wrapper
import cn.enaium.joe.ui.util.i18n
import javafx.geometry.Pos
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import org.objectweb.asm.Handle
import org.tbee.javafx.scene.layout.MigPane

/**
 * @author Enaium
 * @since 2.0.0
 */
class HandleEditPane(handleWrapper: Wrapper<Handle>) : ConfirmPane() {
    init {
        center = MigPane("fillx,", "[fill][fill]").apply {
            add(Label(i18n("instruction.tag")))
            val tag = ComboBox<String>().apply {
                OpcodeUtil.HANDLE.values.forEach {
                    items.add(it)
                }
            }
            add(tag, "wrap")
            add(Label(i18n("instruction.owner")))
            val owner = TextField(handleWrapper.wrapper.owner)
            add(owner, "wrap")
            add(Label(i18n("instruction.name")))
            val name = TextField(handleWrapper.wrapper.name)
            add(name, "wrap")
            add(Label(i18n("instruction.description")))
            val description = TextField(handleWrapper.wrapper.desc)
            add(description, "wrap")
            add(Label(i18n("instruction.interface")))
            val isInterface = CheckBox().apply {
                alignment = Pos.CENTER_RIGHT
            }
            add(isInterface, "wrap")
            confirm = {
                tag.value?.let {
                    handleWrapper.wrapper = Handle(
                        OpcodeUtil.reverse(OpcodeUtil.HANDLE)[tag.selectionModel.selectedItem]!!,
                        owner.text,
                        name.text,
                        description.text,
                        isInterface.isSelected
                    )
                }
            }
        }
    }
}