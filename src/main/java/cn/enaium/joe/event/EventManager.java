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

package cn.enaium.joe.event;

import cn.enaium.joe.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class EventManager {
    List<Pair<Class<? extends Listener>, Consumer<Listener>>> listeners = new CopyOnWriteArrayList<>();

    @SuppressWarnings("unchecked")
    public void register(Class<? extends Listener> listener, Consumer<? extends Listener> consumer) {
        listeners.add(new Pair<>(listener, ((Consumer<Listener>) consumer)));
    }

    public void call(Listener listener) {
        listeners.stream().filter(it -> Arrays.stream(it.getKey().getInterfaces()).anyMatch(i -> i == Listener.class)).forEach(it -> it.getValue().accept(listener));
    }
}
