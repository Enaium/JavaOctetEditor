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

package cn.enaium.joe.ui.util

import javafx.scene.control.Alert
import javafx.scene.control.TextArea
import java.io.PrintWriter
import java.io.StringWriter

/**
 * @author Enaium
 * @author 2.0.0
 */
fun error(throwable: Throwable) {
    throwable.printStackTrace()
    val alert = Alert(Alert.AlertType.ERROR)
    alert.title = "Error"
    alert.contentText = throwable.message

    val out = StringWriter()
    throwable.printStackTrace(PrintWriter(out))
    val textArea = TextArea(out.toString())
    textArea.isEditable = false
    alert.dialogPane.expandableContent = textArea
    alert.show()
}

fun info(text: String) {
    val alert = Alert(Alert.AlertType.INFORMATION)
    alert.title = "Info"
    alert.contentText = text
    alert.show()
}

fun warning(text: String) {
    val alert = Alert(Alert.AlertType.WARNING)
    alert.title = "Warning"
    alert.contentText = text
    alert.show()
}