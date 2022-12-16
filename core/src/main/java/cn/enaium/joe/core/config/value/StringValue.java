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

package cn.enaium.joe.core.config.value;

import com.google.gson.JsonElement;

/**
 * @author Enaium
 * @since 2.0.0
 */
public class StringValue extends Value<String> {
    public StringValue(String name, String value, String description) {
        super(name, value, description);
    }

    @Override
    public void serialize(JsonElement element) {
        setValue(element.getAsString());
    }
}
