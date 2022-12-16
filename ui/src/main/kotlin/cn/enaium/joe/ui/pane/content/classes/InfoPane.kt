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


import cn.enaium.joe.ui.dialog.AnnotationDialog
import cn.enaium.joe.ui.util.i18n
import cn.enaium.joe.ui.util.info
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import org.benf.cfr.reader.util.StringUtils
import org.objectweb.asm.tree.ClassNode
import org.tbee.javafx.scene.layout.MigPane
import java.lang.String
import kotlin.apply


/**
 * @author Enaium
 */
class InfoPane(classNode: ClassNode) : MigPane("fillx", "[fill][fill]") {
    init {
        add(Label(i18n("class.info.name")))
        val name = TextField(classNode.name)
        add(name, "wrap")
        add(Label(i18n("class.info.sourceFile")))
        val sourceFile = TextField(classNode.sourceFile)
        add(sourceFile, "wrap")
        add(Label(i18n("class.info.debugFile")))
        val sourceDebug = TextField(classNode.sourceDebug)
        add(sourceDebug, "wrap")
        add(Label(i18n("class.info.access")))
        val access = TextField(String.valueOf(classNode.access))
        add(access, "wrap")
        add(Label(i18n("class.info.version")))
        val version = TextField(String.valueOf(classNode.version))
        add(version, "wrap")
        add(Label(i18n("class.info.signature")))
        val signature = TextField(classNode.signature)
        add(signature, "wrap")
        add(Label(i18n("class.info.superName")))
        val superName = TextField(classNode.superName)
        add(superName, "wrap")
        add(Label(i18n("class.info.interfaces")))
        val interfaces = TextField(classNode.interfaces.joinToString(";"))
        add(interfaces, "wrap")
        add(Label(i18n("class.info.outerClass")))
        val outerClass = TextField(classNode.outerClass)
        add(outerClass, "wrap")
        add(Label(i18n("class.info.outerMethod")))
        val outerMethod = TextField(classNode.outerMethod)
        add(outerMethod, "wrap")
        add(Label(i18n("class.info.outerMethodDescription")))
        val outerMethodDesc = TextField(classNode.outerMethodDesc)
        add(outerMethodDesc, "wrap")
        add(Label(i18n("class.info.visibleAnnotation")))
        add(Button(i18n("button.edit")).apply {
            setOnAction {
                classNode.visibleAnnotations?.let {
                    AnnotationDialog(classNode.visibleAnnotations).show()
                }
            }
        }, "wrap")
        add(Label(i18n("class.info.invisibleAnnotation")))
        add(Button(i18n("button.edit")).apply {
            setOnAction {
                classNode.invisibleAnnotations?.let {
                    AnnotationDialog(classNode.invisibleAnnotations).show()
                }
            }
        }, "wrap")
        add(Button(i18n("button.save")).apply {
            setOnAction {
                if (name.text?.isNotBlank() == true) {
                    classNode.name = name.text
                }
                if (sourceFile.text?.isNotBlank() == true) {
                    classNode.sourceFile = sourceFile.text
                } else {
                    classNode.sourceFile = null
                }
                if (sourceDebug.text?.isNotBlank() == true) {
                    classNode.sourceDebug = sourceDebug.text
                } else {
                    classNode.sourceDebug = null
                }
                if (access.text?.isNotBlank() == true) {
                    classNode.access = access.text.toInt()
                }
                if (version.text?.isNotBlank() == true) {
                    classNode.version = version.text.toInt()
                }
                if (signature.text?.isNotBlank() == true) {
                    classNode.signature = signature.text
                } else {
                    classNode.signature = null
                }
                if (interfaces.text?.isNotBlank() == true) {
                    classNode.interfaces = listOf(
                        *superName.text.split(";".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray())
                } else {
                    classNode.interfaces = emptyList()
                }
                if (outerClass.text?.isNotBlank() == true) {
                    classNode.outerClass = outerClass.text
                } else {
                    classNode.outerClass = null
                }
                if (outerMethod.text?.isNotBlank() == true) {
                    classNode.outerMethod = outerMethod.text
                } else {
                    classNode.outerClass = null
                }
                if (outerMethodDesc.text?.isNotBlank() == true) {
                    classNode.outerMethodDesc = outerMethodDesc.text
                } else {
                    classNode.outerClass = null
                }
                info(i18n("success"))
            }
        }, "span 2")
    }
}