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

import cn.enaium.joe.core.config.extend.ApplicationConfig
import cn.enaium.joe.ui.JavaOctetEditor
import cn.enaium.joe.ui.JavaOctetEditor.Companion.event
import cn.enaium.joe.ui.cell.FileTreeCell
import cn.enaium.joe.ui.control.tree.*
import cn.enaium.joe.ui.event.LoadJar
import cn.enaium.joe.ui.event.ResultJump
import cn.enaium.joe.ui.event.SelectTreeItem
import cn.enaium.joe.ui.util.expandAll
import cn.enaium.joe.ui.util.i18n
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import org.objectweb.asm.tree.ClassNode


/**
 * @author Enaium
 */
class FileTree : TreeView<Any>() {
    private val classesRoot = AnyTreeItem("classes")
    private val resourceRoot = AnyTreeItem("resources")

    init {
        setCellFactory {
            FileTreeCell()
        }
        isShowRoot = false
        root = TreeItem()
        root.children.add(classesRoot)
        root.children.add(resourceRoot)
        root.isExpanded = true
        setOnMouseClicked {
            if (it.clickCount == 2) {
                selectionModel.selectedItem?.let { select ->
                    event.call(SelectTreeItem(select as AnyTreeItem))
                }
            }
        }

        event.register<ResultJump> {
            selectClassNode(classesRoot, it.classNode)
        }

        contextMenu = ContextMenu().apply {
            items.add(MenuItem(i18n("context.fileTree.expandAll")).apply {
                setOnAction {
                    selectionModel.selectedItem?.let {
                        expandAll(selectionModel.selectedItem)
                    }
                }
            })
        }


        event.register<LoadJar> {
            classesRoot.children.clear()
            resourceRoot.children.clear()

            val hasMap: MutableMap<String, AnyTreeItem> = HashMap()
            for (classNode in it.jar.classes.values) {
                val split = classNode.name.split("/")
                var prev: AnyTreeItem? = null
                val stringBuilder = StringBuilder()
                for ((index, s) in split.withIndex()) {
                    stringBuilder.append(s)
                    var packageItem = PackageTreeItem(s)
                    if (split.size == index + 1) {
                        packageItem = ClassTreeItem(classNode)
                    }
                    if (prev == null) {
                        if (!hasMap.containsKey(stringBuilder.toString())) {
                            classesRoot.children.add(packageItem)
                            hasMap[stringBuilder.toString()] = packageItem
                            prev = packageItem
                        } else {
                            prev = hasMap[stringBuilder.toString()]
                        }
                    } else {
                        if (!hasMap.containsKey(stringBuilder.toString())) {
                            prev.children.add(packageItem)
                            hasMap[stringBuilder.toString()] = packageItem
                            prev = packageItem
                        } else {
                            prev = hasMap[stringBuilder.toString()]
                        }
                    }
                }
            }
            sort(classesRoot)
            compact(classesRoot)
            hasMap.clear()
            for ((key, value) in it.jar.resources) {
                val split = key.split("/")
                var prev: AnyTreeItem? = null
                val stringBuilder = StringBuilder()
                for ((index, s) in split.withIndex()) {
                    stringBuilder.append(s)
                    var folderTreeItem = FolderTreeItem(s)
                    if (split.size == index + 1) {
                        folderTreeItem = FileTreeItem(s, value)
                    }
                    if (prev == null) {
                        if (!hasMap.containsKey(stringBuilder.toString())) {
                            resourceRoot.children.add(folderTreeItem)
                            hasMap[stringBuilder.toString()] = folderTreeItem
                            prev = folderTreeItem
                        } else {
                            prev = hasMap[stringBuilder.toString()]
                        }
                    } else {
                        if (!hasMap.containsKey(stringBuilder.toString())) {
                            prev.children.add(folderTreeItem)
                            hasMap[stringBuilder.toString()] = folderTreeItem
                            prev = folderTreeItem
                        } else {
                            prev = hasMap[stringBuilder.toString()]
                        }
                    }
                }
            }
            sort(resourceRoot)
            compact(resourceRoot)
        }
    }

    private fun compact(anyTreeItem: TreeItem<Any>) {
        if (!JavaOctetEditor.config.getByClass(ApplicationConfig::class.java).compactMiddlePackage.value) {
            return
        }
        if (!anyTreeItem.isLeaf) {
            val parent = anyTreeItem.parent
            if (parent.children.size == 1 && !(parent.equals(classesRoot) || parent.equals(resourceRoot))) {
                parent.value = "${parent.value}.${anyTreeItem.value}"
                parent.children.clear()
                for (child in anyTreeItem.children) {
                    child.parent.value = parent
                    parent.children.add(child)
                }
            }

            for (i in 0 until anyTreeItem.children.size) {
                compact(anyTreeItem.children[i])
            }
        }
    }

    private fun sort(anyTreeItem: TreeItem<Any>) {
        if (!anyTreeItem.isLeaf) {
            anyTreeItem.children.sortWith(Comparator { o1: TreeItem<Any>, o2: TreeItem<Any> ->
                val class1 = o1 is ClassTreeItem
                val class2 = o2 is ClassTreeItem
                val file1 = o1 is FileTreeItem
                val file2 = o2 is FileTreeItem
                if (class1 && !class2) {
                    return@Comparator 1
                }
                if (!class1 && class2) {
                    return@Comparator -1
                }
                if (file1 && !file2) {
                    return@Comparator 1
                }
                if (!file1 && file2) {
                    return@Comparator -1
                }
                o1.toString().compareTo(o2.toString())
            })
            for (i in 0 until anyTreeItem.children.size) {
                sort(anyTreeItem.children[i])
            }
        }
    }

    private fun selectClassNode(anyTreeItem: TreeItem<Any>, classNode: ClassNode) {
        if (anyTreeItem is ClassTreeItem) {
            if (anyTreeItem.classNode == classNode) {
                selectionModel.select(anyTreeItem)
                event.call(SelectTreeItem(anyTreeItem as AnyTreeItem))
            }
        }

        anyTreeItem.children.forEach {
            selectClassNode(it, classNode)
        }
    }
}