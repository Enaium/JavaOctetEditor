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

package cn.enaium.joe.core.config;

import cn.enaium.joe.core.config.extend.ApplicationConfig;
import cn.enaium.joe.core.config.extend.CFRConfig;
import cn.enaium.joe.core.config.extend.FernFlowerConfig;
import cn.enaium.joe.core.config.extend.ProcyonConfig;
import cn.enaium.joe.core.config.value.Value;
import com.google.gson.*;
import javafx.scene.input.KeyCodeCombination;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Enaium
 * @since 2.0.0
 */
public class ConfigManager {
    private final Map<Class<? extends Config>, Config> configMap = new LinkedHashMap<>();

    public ConfigManager() {
        setByClass(new ApplicationConfig());
        setByClass(new CFRConfig());
        setByClass(new FernFlowerConfig());
        setByClass(new ProcyonConfig());
    }

    @SuppressWarnings("unchecked")
    public <T> T getByClass(Class<T> klass) {
        if (configMap.containsKey(klass)) {
            return (T) configMap.get(klass);
        } else {
            throw new RuntimeException("Not found " + klass);
        }
    }

    public void setByClass(Config config) {
        configMap.put(config.getClass(), config);
    }

    public Map<Class<? extends Config>, Config> getConfig() {
        return configMap;
    }

    public Map<String, String> getConfigMap(Class<? extends Config> config) {
        Map<String, String> map = new HashMap<>();
        for (Field declaredField : config.getDeclaredFields()) {
            declaredField.setAccessible(true);
            try {
                Object o = declaredField.get(getByClass(config));
                if (o instanceof Value<?>) {
                    Object value = ((Value<?>) o).getValue();
                    if (value != null) {
                        map.put(declaredField.getName(), value.toString());
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    private GsonBuilder gson() {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping();
    }

    public void load() throws IOException, IllegalAccessException {
        for (Map.Entry<Class<? extends Config>, Config> classConfigEntry : configMap.entrySet()) {

            Class<? extends Config> klass = classConfigEntry.getKey();
            Config config = classConfigEntry.getValue();
            File file = new File(System.getProperty("."), config.getName() + ".json");
            if (file.exists()) {
                JsonObject jsonObject = gson().create().fromJson(Files.readString(file.toPath()), JsonObject.class);

                for (Field configField : klass.getDeclaredFields()) {
                    configField.setAccessible(true);
                    if (!jsonObject.has(configField.getName())) {
                        continue;
                    }

                    if (!jsonObject.has(configField.getName())) {
                        continue;
                    }

                    if (!jsonObject.get(configField.getName()).getAsJsonObject().has("value")) {
                        continue;
                    }

                    JsonElement valueJsonElement = jsonObject.get(configField.getName()).getAsJsonObject().get("value");

                    Object valueObject = configField.get(config);
                    if (valueObject instanceof Value<?> value) {
                        value.serialize(valueJsonElement);
                    }
                }
            }
        }
    }

    public void save() throws IOException {
        for (Config value : configMap.values()) {
            Files.writeString(new File(System.getProperty("."), value.getName() + ".json").toPath(), gson().registerTypeAdapter(KeyCodeCombination.class, (JsonSerializer<KeyCodeCombination>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString())).create().toJson(value));
        }
    }
}
