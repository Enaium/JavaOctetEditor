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

import cn.enaium.joe.core.util.OpcodeUtil
import cn.enaium.joe.ui.util.ColorUtil
import javafx.scene.Node
import javafx.scene.control.ListCell
import javafx.scene.text.Text
import javafx.scene.text.TextFlow
import org.objectweb.asm.Handle
import org.objectweb.asm.tree.*

/**
 * @author Enaium
 * @since 2.0.0
 */
class InstructionListCell : ListCell<AbstractInsnNode>() {
    override fun updateItem(item: AbstractInsnNode?, empty: Boolean) {
        super.updateItem(item, empty)
        if (empty) {
            graphic = null
            text = null
            contextMenu = null
        } else {
            val instruction = item!!
            val textFlow = TextFlow()

            fun addText(vararg element: Node) {
                textFlow.children.add(Text(" "))
                textFlow.children.addAll(element)
            }

            fun handle(handle: Handle): TextFlow {
                return TextFlow(Text("handle[").apply {
                    fill = ColorUtil.other
                }, Text(OpcodeUtil.HANDLE[handle.tag]).apply {
                    fill = ColorUtil.opcode
                }, Text(":"), Text(handle.owner).apply {
                    fill = ColorUtil.desc
                }, Text(handle.name).apply {
                    fill = ColorUtil.name
                }, Text(handle.desc).apply {
                    fill = ColorUtil.desc
                }, Text(handle.isInterface.toString()).apply {
                    fill = ColorUtil.bool
                }, Text("]").apply {
                    fill = ColorUtil.other
                })
            }

            if (instruction.opcode != -1) {
                addText(Text(OpcodeUtil.OPCODE[instruction.opcode]).apply {
                    fill = ColorUtil.opcode
                })
            }
            when (instruction) {
                is InsnNode -> {
                    //Opcode
                }

                is IntInsnNode -> {
                    addText(Text(instruction.operand.toString()).apply {
                        fill = ColorUtil.base
                    })
                }

                is VarInsnNode -> {
                    addText(Text(instruction.`var`.toString()).apply {
                        fill = ColorUtil.base
                    })
                }

                is TypeInsnNode -> {
                    addText(Text(instruction.desc.toString()).apply {
                        fill = ColorUtil.desc
                    })
                }

                is FieldInsnNode -> {
                    addText(Text(instruction.name).apply {
                        fill = ColorUtil.name
                    })
                    addText(Text(instruction.desc).apply {
                        fill = ColorUtil.desc
                    })
                }

                is MethodInsnNode -> {
                    addText(Text(instruction.name).apply {
                        fill = ColorUtil.name
                    })
                    addText(Text(instruction.desc).apply {
                        fill = ColorUtil.desc
                    })
                    addText(Text(instruction.itf.toString()).apply {
                        fill = ColorUtil.base
                    })
                }

                is InvokeDynamicInsnNode -> {
                    addText(Text(instruction.name).apply {
                        fill = ColorUtil.name
                    })
                    addText(Text(instruction.desc).apply {
                        fill = ColorUtil.desc
                    })
                    addText(Text("\n"))
                    addText(handle(instruction.bsm))
                    addText(Text(instruction.bsmArgs.map {
                        if (it is Handle) {
                            handle(it)
                        } else {
                            it.toString()
                        }
                    }.toString()).apply {
                        fill = ColorUtil.desc
                    })
                }

                is JumpInsnNode -> {
                    addText(Text(OpcodeUtil.getLabelIndex(instruction.label).toString()).apply {
                        fill = ColorUtil.base
                    })
                }

                is LabelNode -> {
                    addText(Text("L").apply {
                        fill = ColorUtil.opcode
                    })
                    addText(Text(OpcodeUtil.getLabelIndex(instruction).toString()).apply {
                        fill = ColorUtil.base
                    })
                }

                is LdcInsnNode -> {
                    when (instruction.cst) {
                        is String -> {
                            addText(Text("\"${instruction.cst}\"").apply {
                                fill = ColorUtil.string
                            })
                        }

                        is Boolean -> {
                            addText(Text(instruction.cst.toString()).apply {
                                fill = ColorUtil.bool
                            })
                        }

                        else -> {
                            addText(Text(instruction.cst.toString()).apply {
                                fill = ColorUtil.base
                            })
                        }
                    }
                }

                is IincInsnNode -> {
                    addText(Text(instruction.`var`.toString()).apply {
                        fill = ColorUtil.base
                    })
                    addText(Text(instruction.incr.toString()).apply {
                        fill = ColorUtil.base
                    })
                }

                is TableSwitchInsnNode -> {
                    addText(Text("range[${instruction.min},${instruction.max}]").apply {
                        fill = ColorUtil.desc
                    })
                    instruction.labels.map { OpcodeUtil.getLabelIndex(it) }.forEach {
                        addText(Text("L${it}").apply {
                            fill = ColorUtil.base
                        })
                    }
                    if (instruction.dflt != null) {
                        addText(Text("default").apply {
                            fill = ColorUtil.other
                        }, Text(":"), Text("L${OpcodeUtil.getLabelIndex(instruction.dflt)}").apply {
                            fill = ColorUtil.base
                        })
                    }
                }

                is LookupSwitchInsnNode -> {
                    for (i in 0 until instruction.keys.size) {
                        addText(
                            Text(instruction.keys[i].toString()).apply {
                                fill = ColorUtil.base
                            },
                            Text(":"),
                            Text(
                                "L" + OpcodeUtil.getLabelIndex(
                                    instruction.labels[i]
                                )
                            ).apply {
                                fill = ColorUtil.base
                            }
                        )
                    }
                    if (instruction.dflt != null) {
                        addText(
                            Text("default").apply {
                                fill = ColorUtil.other
                            },
                            Text(":"),
                            Text("L${OpcodeUtil.getLabelIndex(instruction.dflt)}").apply {
                                fill = ColorUtil.base
                            }
                        )
                    }
                }

                is MultiANewArrayInsnNode -> {
                    addText(Text(instruction.desc).apply { fill = ColorUtil.desc })
                    addText(Text(instruction.dims.toString()).apply { fill = ColorUtil.base })
                }

                is FrameNode -> {
                    addText(Text(OpcodeUtil.FRAME[instruction.type]).apply {
                        fill = ColorUtil.opcode
                    })
                    addText(Text(instruction.local.toString()).apply {
                        fill = ColorUtil.desc
                    })
                    addText(Text(instruction.stack.toString()).apply {
                        fill = ColorUtil.desc
                    })
                }

                is LineNumberNode -> {
                    addText(Text("LINE").apply {
                        fill = ColorUtil.opcode
                    })
                    addText(Text(instruction.line.toString()).apply {
                        fill = ColorUtil.base
                    })
                    addText(Text("L${OpcodeUtil.getLabelIndex(instruction.start)}").apply {
                        fill = ColorUtil.base
                    })
                }
            }
            graphic = textFlow
        }
    }
}