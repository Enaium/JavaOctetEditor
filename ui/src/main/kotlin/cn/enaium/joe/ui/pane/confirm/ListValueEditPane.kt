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

import cn.enaium.joe.core.util.ASMUtil
import cn.enaium.joe.core.util.ListUtil
import javafx.scene.control.TextArea

/**
 * @author Enaium
 * @since 2.0.0
 */
class ListValueEditPane(anyList: MutableList<Any>) : ConfirmPane() {
    init {
        val type = ListUtil.getType(anyList)
        val textArea = TextArea(anyList.joinToString("\n"))
        center = textArea
        confirm = {
            anyList.clear()
            textArea.text.split("\n").forEach {
                anyList.add(ASMUtil.valueOf(type, it))
            }
        }
    }
}