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


import cn.enaium.joe.ui.control.CodeEditor
import cn.enaium.joe.ui.pane.CodePane
import cn.enaium.joe.ui.util.runLater
import javafx.scene.layout.BorderPane
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.util.ASMifier
import org.objectweb.asm.util.TraceClassVisitor
import java.io.PrintWriter
import java.io.StringWriter


class ASMifierPane(classNode: ClassNode) : BorderPane() {
    init {

        center = CodePane(CodeEditor.Language.Type.JAVA).apply {
            runLater({
                val stringWriter = StringWriter()
                classNode.accept(TraceClassVisitor(null, ASMifier(), PrintWriter(stringWriter)))
                stringWriter.toString()
            }, { result ->
                val trim = getMiddle(getMiddle(result)).trim()
                codeEditor.replaceText(trim.substring(0, trim.lastIndexOf("\n")))
            })
        }
    }

    private fun getMiddle(s: String): String {
        return s.substring(s.indexOf("{") + 1, s.lastIndexOf("}"))
    }
}