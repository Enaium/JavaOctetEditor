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

package cn.enaium.joe.ui.cell

import cn.enaium.joe.ui.control.tree.ClassTreeItem
import cn.enaium.joe.ui.control.tree.FileTreeItem
import cn.enaium.joe.ui.control.tree.FolderTreeItem
import cn.enaium.joe.ui.control.tree.PackageTreeItem
import cn.enaium.joe.ui.util.loadSVG
import javafx.scene.control.TreeCell
import javafx.scene.control.TreeView
import javafx.util.Callback
import org.objectweb.asm.Opcodes

/**
 * @author Enaium
 * @since 2.0.0
 */
class FileTreeCell : TreeCell<Any>() {
    override fun updateItem(item: Any?, empty: Boolean) {
        super.updateItem(item, empty)
        if (empty) {
            graphic = null
            text = null
            contextMenu = null
        } else {
            text = item.toString()
            if (treeItem is ClassTreeItem) {
                graphic = when ((treeItem as ClassTreeItem).classNode.access) {
                    Opcodes.ACC_PUBLIC or Opcodes.ACC_ANNOTATION or Opcodes.ACC_ABSTRACT or Opcodes.ACC_INTERFACE -> {
                        loadSVG("icons/annotation.svg")
                    }

                    Opcodes.ACC_PUBLIC or Opcodes.ACC_ABSTRACT or Opcodes.ACC_INTERFACE -> {
                        loadSVG("icons/interface.svg")
                    }

                    Opcodes.ACC_PUBLIC or Opcodes.ACC_SUPER or Opcodes.ACC_ABSTRACT -> {
                        loadSVG("icons/abstractClass.svg")
                    }

                    Opcodes.ACC_PUBLIC or Opcodes.ACC_FINAL or Opcodes.ACC_SUPER or Opcodes.ACC_ENUM -> {
                        loadSVG("icons/enum.svg")
                    }

                    Opcodes.ACC_PUBLIC or Opcodes.ACC_FINAL or Opcodes.ACC_SUPER -> {
                        loadSVG("icons/finalClass.svg")
                    }

                    else -> {
                        loadSVG("icons/class.svg")
                    }
                }
            } else if (treeItem is PackageTreeItem) {
                graphic = loadSVG("icons/package.svg")
            } else if (treeItem is FileTreeItem) {
                graphic = loadSVG("icons/file.svg")
            } else if (treeItem is FolderTreeItem) {
                graphic = loadSVG("icons/folder.svg")
            } else if (item.toString() == "classes") {
                graphic = loadSVG("icons/classesRoot.svg")
            } else if (item.toString() == "resources") {
                graphic = loadSVG("icons/resourceRoot.svg")
            }
        }
    }
}