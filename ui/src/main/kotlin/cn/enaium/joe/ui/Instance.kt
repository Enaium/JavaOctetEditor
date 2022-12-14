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

package cn.enaium.joe.ui

import cn.enaium.joe.core.model.JarModel
import cn.enaium.joe.ui.JavaOctetEditor.Companion.event
import cn.enaium.joe.ui.event.LoadJar

/**
 * @author Enaium
 * @author 2.0.0
 */
object Instance {
    /**
     * Don't use const,because need to initialize first
     */
    @Suppress("MayBeConstant")
    val NAME = "JavaOctetEditor"
    var jar: JarModel? = null

    init {
        event.register<LoadJar> {
            jar = it.jar
        }
    }
}