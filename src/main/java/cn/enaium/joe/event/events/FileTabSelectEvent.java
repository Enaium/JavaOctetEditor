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

package cn.enaium.joe.event.events;

import cn.enaium.joe.event.Event;
import javafx.scene.control.Tab;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class FileTabSelectEvent implements Event {
    private final Tab select;

    public FileTabSelectEvent(Tab select) {
        this.select = select;
    }

    public Tab getSelect() {
        return select;
    }
}
