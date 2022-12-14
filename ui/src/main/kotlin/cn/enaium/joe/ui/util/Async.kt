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

import javafx.application.Platform
import java.util.concurrent.Executors
import java.util.function.Consumer
import java.util.function.Supplier

/**
 * @author Enaium
 * @author 2.0.0
 */
fun runLater(run: Runnable, ui: Runnable) {
    val executorService = Executors.newSingleThreadExecutor()
    executorService.execute {
        run.run()
        if (ui != null) {
            Platform.runLater(ui)
        }
    }
}

fun <T> runLater(supplier: Supplier<T>, ui: Consumer<T>) {
    val executorService = Executors.newSingleThreadExecutor()
    executorService.execute {
        val t: T = supplier.get()
        if (ui != null) {
            Platform.runLater { ui.accept(t) }
        }
    }
}