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

import cn.enaium.joe.core.util.ASMUtil
import cn.enaium.joe.core.util.ReflectUtil
import cn.enaium.joe.ui.Instance.jar
import cn.enaium.joe.ui.JavaOctetEditor.Companion.event
import cn.enaium.joe.ui.cell.FileTreeCell
import cn.enaium.joe.ui.control.tree.AnyTreeItem
import cn.enaium.joe.ui.control.tree.ClassTreeItem
import cn.enaium.joe.ui.event.ContentTabChange
import cn.enaium.joe.ui.event.SelectTreeItem
import cn.enaium.joe.ui.pane.content.classes.ClassTabPane
import cn.enaium.joe.ui.util.expandAll
import javafx.scene.control.ToggleButton
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.layout.BorderPane
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class InheritSidePane : BorderPane() {

    var classNode: ClassNode? = null

    init {
        val inherit = TreeView<Any>()
        inherit.isShowRoot = false


        inherit.setCellFactory {
            FileTreeCell()
        }

        fun set(parent: Boolean) {
            inherit.root = TreeItem()
            inherit.root.children.add(ClassTreeItem(classNode!!).apply {
                recursion(this, parent)
            })
            expandAll(inherit.root)
        }

        inherit.setOnMouseClicked {
            if (it.clickCount == 2) {
                inherit.selectionModel.selectedItem?.let { select ->
                    event.call(SelectTreeItem(select as AnyTreeItem))
                }
            }
        }

        event.register<ContentTabChange> { event ->
            event.new?.let {
                inherit.root = TreeItem()
                if (event.new.content is ClassTabPane) {
                    classNode = (event.new.content as ClassTabPane).classTreeItem.classNode
                    set(false)
                } else {
                    classNode = null
                    inherit.root = TreeItem()
                }
            }
        }
        center = inherit
        bottom = ToggleButton("Parent").apply {
            setOnAction {
                if (isSelected) {
                    set(true)
                } else {
                    set(false)
                }
            }
        }
    }


    private fun recursion(classTreeItem: ClassTreeItem, parent: Boolean) {
        val classNode = classTreeItem.classNode
        if (parent) {
            ASMUtil.getParentClass(classNode).forEach {
                var newChild: ClassTreeItem? = null
                if (jar!!.classes.containsKey("$it.class")) {
                    newChild = ClassTreeItem(jar!!.classes["$it.class"]!!)
                } else if (ReflectUtil.classHas(it.replace("/", "."))) {
                    newChild = ClassTreeItem(ASMUtil.acceptClassNode(ClassReader(it)))
                }

                newChild?.let {
                    classTreeItem.children.add(newChild)
                    recursion(newChild, true)
                }
            }
        } else {
            jar!!.classes.values.forEach {
                val parentClass = ASMUtil.getParentClass(it)
                if (parentClass.contains(classNode.name)) {
                    val newChild = ClassTreeItem(it)
                    classTreeItem.children.add(newChild)
                    recursion(newChild, false)
                }
            }
        }
    }
}