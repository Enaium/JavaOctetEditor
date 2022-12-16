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

package cn.enaium.joe.ui.dialog

import cn.enaium.joe.core.util.ReflectUtil
import cn.enaium.joe.ui.util.i18n
import javafx.scene.control.Label
import javafx.scene.control.Separator
import net.miginfocom.layout.CC
import org.tbee.javafx.scene.layout.MigPane
import javax.tools.ToolProvider

/**
 * @author Enaium
 * @since 2.0.0
 */
class AboutDialog : Dialog("About") {
    init {
        content = MigPane("fillx", "[fill][fill]").apply {
            add(Label(i18n("about.system")), "wrap")
            add(Label(i18n("about.system.description")), "wrap")
            add(Label(i18n("about.system.name")))
            add(Label(System.getProperty("os.name")), "wrap")
            add(Label(i18n("about.system.architecture")))
            add(Label(System.getProperty("os.arch")), "wrap")

            add(Separator(), CC().span(2).wrap())

            add(Label(i18n("about.java")), "wrap")
            add(Label(i18n("about.java.description")), "wrap")
            add(Label(i18n("about.java.version")))
            add(Label(System.getProperty("java.version")), "wrap")
            add(Label(i18n("about.java.vm.name")))
            add(Label(System.getProperty("java.vm.name")), "wrap")
            add(Label(i18n("about.java.vm.vendor")))
            add(Label(System.getProperty("java.vm.vendor")), "wrap")
            add(Label(i18n("about.java.home")))
            add(Label(System.getProperty("java.home")), "wrap")
            add(Label(i18n("about.java.supportCompiler")))
            add(Label("${ToolProvider.getSystemJavaCompiler() != null}"), "wrap")
            add(Label(i18n("about.java.supportAttach")))
            add(Label("${ReflectUtil.classHas("com.sun.tools.attach.VirtualMachine")}"), "wrap")

            add(Separator(), CC().span(2).wrap())

            add(Label("JavaFX"), "wrap")
            add(Label("Information about the JavaFX UI"), "wrap")
            add(Label("Version"))
            add(Label(System.getProperty("javafx.version")), "wrap")
        }
    }
}