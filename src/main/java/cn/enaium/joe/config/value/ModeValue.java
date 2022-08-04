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

package cn.enaium.joe.config.value;

import java.util.List;

/**
 * @author Enaium
 * @since 0.7.0
 */
public class ModeValue extends Value<String> {

    private final List<String> mode;

    public ModeValue(String name, String value, String description, List<String> mode) {
        super(name, value, description);
        this.mode = mode;
    }

    public List<String> getMode() {
        return mode;
    }
}
