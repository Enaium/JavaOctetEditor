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
package cn.enaium.joe.ui.event

import cn.enaium.joe.core.util.Pair
import java.util.concurrent.CopyOnWriteArrayList
import java.util.function.Consumer

/**
 * @author Enaium
 * @since 2.0.0
 */
class EventManager {
    val listeners: MutableList<Pair<Class<out Event>, Consumer<Event>>> = CopyOnWriteArrayList()
    inline fun <reified T : Event> register(consumer: Consumer<T>) {
        listeners.add(Pair(T::class.java, consumer as Consumer<Event>))
    }

    fun call(event: Event) {
        listeners.stream().filter { it.key == event.javaClass }.forEach { it.value.accept(event) }
    }
}