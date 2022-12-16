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

import cn.enaium.joe.ui.dialog.AnnotationDialog
import cn.enaium.joe.ui.util.i18n
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import org.objectweb.asm.tree.MethodNode
import org.tbee.javafx.scene.layout.MigPane
import java.lang.String
import java.util.*
import kotlin.Any
import kotlin.apply


/**
 * @author Enaium
 * @since 2.0.0
 */
class InfoPane(methodNode: MethodNode) : MigPane("fillx", "[fill][fill]") {
    init {
        add(Label(i18n("class.info.name")))
        val name = TextField(methodNode.name)
        add(name, "wrap")
        add(Label(i18n("class.info.description")))
        val description = TextField(methodNode.desc)
        add(description, "wrap")
        add(Label(i18n("class.info.access")))
        val access = TextField(String.valueOf(methodNode.access))
        add(access, "wrap")
        add(Label(i18n("class.info.signature")))
        val signature = TextField(methodNode.signature)
        add(signature, "wrap")
        add(Label(i18n("class.info.exceptions")))
        val exceptions = TextField(methodNode.exceptions.joinToString(";"))
        add(exceptions, "wrap")
        add(Label(i18n("class.info.visibleAnnotation")))
        add(Button(i18n("button.edit")).apply {
            setOnAction {
                methodNode.visibleAnnotations?.let {
                    AnnotationDialog(methodNode.visibleAnnotations).show()
                }
            }
        }, "wrap")
        add(Label(i18n("class.info.invisibleAnnotation")))
        add(Button(i18n("button.edit")).apply {
            setOnAction {
                methodNode.invisibleAnnotations?.let {
                    AnnotationDialog(methodNode.invisibleAnnotations).show()
                }
            }
        }, "wrap")
        add(Button("button.save").apply {
            setOnAction {
                if (name.text?.isNotBlank() == true) {
                    methodNode.name = name.text
                }

                if (description.text?.isNotBlank() == true) {
                    methodNode.desc = description.text
                } else {
                    methodNode.desc = null
                }

                if (access.text?.isNotBlank() == true) {
                    methodNode.access = access.text.toInt()
                }

                if (signature.text?.isNotBlank() == true) {
                    methodNode.signature = signature.text
                } else {
                    methodNode.signature = null
                }

                if (signature.text?.isNotBlank() == true) {
                    methodNode.exceptions = signature.text.split(";")
                } else {
                    methodNode.exceptions = emptyList()
                }
            }
        }, "span 2")
    }
}