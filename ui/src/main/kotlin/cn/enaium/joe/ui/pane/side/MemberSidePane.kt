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

package cn.enaium.joe.ui.pane.side

import cn.enaium.joe.ui.JavaOctetEditor.Companion.event
import cn.enaium.joe.ui.cell.MemberListCell
import cn.enaium.joe.ui.dialog.Dialog
import cn.enaium.joe.ui.event.ContentTabChange
import cn.enaium.joe.ui.pane.content.classes.ClassTabPane
import cn.enaium.joe.ui.pane.method.MethodTabPane
import javafx.scene.control.ListView
import javafx.scene.layout.BorderPane
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class MemberSidePane : BorderPane() {
    init {
        val memberList = ListView<Pair<ClassNode, Any>>()


        memberList.setCellFactory {
            MemberListCell()
        }

        event.register<ContentTabChange> { event ->
            event.new?.let {
                if (event.new.content is ClassTabPane) {
                    val classNode = (event.new.content as ClassTabPane).classTreeItem.classNode
                    memberList.items.clear()
                    classNode.fields.forEach { field ->
                        memberList.items.add(Pair(classNode, field))
                    }
                    classNode.methods.forEach { method ->
                        memberList.items.add(Pair(classNode, method))
                    }
                }
            }
        }

        memberList.setOnMouseClicked {
            if (it.clickCount == 2) {
                val second = memberList.selectionModel.selectedItem.second
                if (second is MethodNode) {
                    Dialog("Method").apply {
                        content = MethodTabPane(second)
                    }.show()
                }
            }
        }

        center = memberList
    }
}