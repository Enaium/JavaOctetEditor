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
import cn.enaium.joe.core.wrapper.Wrapper
import javafx.scene.control.TextField

/**
 * @author Enaium
 * @since 2.0.0
 */
class ValueEditPane(anyWrapper: Wrapper<Any>) : ConfirmPane() {
    init {
        val textField = TextField(anyWrapper.wrapper.toString())
        center = textField
        confirm = {
            anyWrapper.wrapper = ASMUtil.valueOf(anyWrapper.wrapper.javaClass, textField.text)
        }
    }
}