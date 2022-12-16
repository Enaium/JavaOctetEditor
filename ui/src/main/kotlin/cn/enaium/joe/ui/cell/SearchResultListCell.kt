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

import cn.enaium.joe.core.model.SearchResultModel
import cn.enaium.joe.core.util.OpcodeUtil
import cn.enaium.joe.ui.util.ColorUtil
import javafx.scene.control.ListCell
import javafx.scene.text.Text
import javafx.scene.text.TextFlow
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.MethodInsnNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class SearchResultListCell : ListCell<SearchResultModel>() {
    override fun updateItem(item: SearchResultModel?, empty: Boolean) {
        super.updateItem(item, empty)
        if (empty) {
            graphic = null
            text = null
            contextMenu = null
        } else {
            when (val result = item!!.result) {
                is FieldInsnNode -> {
                    graphic = TextFlow(Text(result.name).apply {
                        fill = ColorUtil.name
                    }, Text(":").apply {
                        fill = ColorUtil.opcode
                    }, Text(result.desc).apply {
                        fill = ColorUtil.desc
                    })
                }

                is MethodInsnNode -> {
                    graphic = TextFlow(Text(result.name).apply {
                        fill = ColorUtil.name
                    }, Text(":").apply {
                        fill = ColorUtil.opcode
                    }, Text(result.desc).apply {
                        fill = ColorUtil.desc
                    }, Text(result.itf.toString()).apply {
                        fill = ColorUtil.bool
                    })
                }

                is LdcInsnNode -> {
                    graphic = Text(result.cst.toString()).apply {
                        fill = when (result.cst) {
                            is Boolean -> {
                                ColorUtil.bool
                            }

                            is String -> {
                                ColorUtil.string
                            }

                            else -> {
                                ColorUtil.base
                            }
                        }
                    }
                }

                is AbstractInsnNode -> {
                    text = OpcodeUtil.OPCODE[result.opcode]
                }
            }
        }
    }
}