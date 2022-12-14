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


import cn.enaium.joe.core.task.DecompileTask
import cn.enaium.joe.ui.Instance
import cn.enaium.joe.ui.JavaOctetEditor.Companion.config
import cn.enaium.joe.ui.JavaOctetEditor.Companion.task
import cn.enaium.joe.ui.control.CodeEditor
import cn.enaium.joe.ui.pane.CodePane
import javafx.application.Platform
import javafx.scene.layout.BorderPane
import org.objectweb.asm.tree.ClassNode


class DecompilePane(classNode: ClassNode) : BorderPane() {
    init {
        center = CodePane(CodeEditor.Language.Type.JAVA).apply {
            task.submit(DecompileTask(Instance.jar, config, classNode)).thenAccept {
                Platform.runLater { codeEditor.replaceText(it) }
            }
        }
    }
}