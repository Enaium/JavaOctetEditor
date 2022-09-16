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

package cn.enaium.joe.util;

import javafx.application.Platform;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Enaium
 */
public class ASyncUtil {

    public static void execute(Runnable run) {
        execute(run, null);
    }

    public static void execute(Runnable run, Runnable ui) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            run.run();
            if (ui != null) {
                Platform.runLater(ui);
            }
        });
    }

    public static <T> void execute(Supplier<T> supplier, Consumer<T> ui) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            T t = supplier.get();
            if (ui != null) {
                Platform.runLater(() -> ui.accept(t));
            }
        });
    }
}
