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

package cn.enaium.joe.ui.pane.content.classes


import cn.enaium.joe.ui.control.tree.ClassTreeItem
import cn.enaium.joe.ui.util.i18n
import javafx.geometry.Side
import javafx.scene.control.Tab
import javafx.scene.control.TabPane

/**
 * @author Enaium
 */
class ClassTabPane(val classTreeItem: ClassTreeItem) : TabPane() {
    init {
        side = Side.BOTTOM
        tabs.addAll(
            Tab(i18n("class.tab.bytecode")).apply {
                isClosable = false
                content = BytecodePane(classTreeItem.classNode)
            },
            Tab(i18n("class.tab.decompile")).apply {
                isClosable = false
                content = DecompilePane(classTreeItem.classNode)
            },
            Tab(i18n("class.tab.visitor")).apply {
                isClosable = false
                content = ASMifierPane(classTreeItem.classNode)
            },
            Tab(i18n("class.tab.info")).apply {
                isClosable = false
                content = InfoPane(classTreeItem.classNode)
            },
        )
    }
}