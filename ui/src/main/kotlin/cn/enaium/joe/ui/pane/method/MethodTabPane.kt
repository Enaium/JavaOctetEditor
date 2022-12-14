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

package cn.enaium.joe.ui.pane.method

import cn.enaium.joe.ui.control.MethodInstructionList
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import org.objectweb.asm.tree.MethodNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class MethodTabPane(methodNode: MethodNode) : TabPane() {
    init {
        tabs.addAll(
            Tab("Instruction").apply {
                isClosable = false
                content = MethodInstructionList(methodNode)
            },
            Tab("Info").apply {
                isClosable = false
                content = InfoPane(methodNode)
            }
        )
    }
}