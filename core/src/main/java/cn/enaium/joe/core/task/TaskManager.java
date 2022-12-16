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

package cn.enaium.joe.core.task;

import cn.enaium.joe.core.annotation.Repeatable;
import cn.enaium.joe.core.util.Pair;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Enaium
 * @since 2.0.0
 */
public class TaskManager {
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final List<Pair<AbstractTask<?>, CompletableFuture<?>>> task = new CopyOnWriteArrayList<>();

    public <T> CompletableFuture<T> submit(AbstractTask<T> abstractTask) {
        for (Pair<AbstractTask<?>, CompletableFuture<?>> classCompletableFuturePair : task) {
            if (classCompletableFuturePair.getKey().getClass().equals(abstractTask.getClass()) && !classCompletableFuturePair.getKey().getClass().isAnnotationPresent(Repeatable.class)) {
                throw new RuntimeException("task already exists");
            }
        }
        CompletableFuture<T> completableFuture = CompletableFuture.supplyAsync(abstractTask, executorService);
        completableFuture.whenComplete((t, throwable) -> {
            task.removeIf(it -> it.getValue().isDone());
            if (throwable != null) {
                throw new RuntimeException(throwable);
            }
        });

        task.add(new Pair<>(abstractTask, completableFuture));
        return completableFuture;
    }

    public List<Pair<AbstractTask<?>, CompletableFuture<?>>> getAll() {
        return task;
    }
}
