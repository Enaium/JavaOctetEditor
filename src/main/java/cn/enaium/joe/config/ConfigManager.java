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

package cn.enaium.joe.config;

import cn.enaium.joe.annotation.NoDeserialize;
import cn.enaium.joe.config.extend.ApplicationConfig;
import cn.enaium.joe.config.extend.CFRConfig;
import cn.enaium.joe.config.value.Value;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Enaium
 * @since 0.7.0
 */
public class ConfigManager {
    private final Map<Class<? extends Config>, Config> configMap = new HashMap<>();

    public ConfigManager() {
        setByClass(new ApplicationConfig());
        setByClass(new CFRConfig());
    }

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
                        map.put(((Value<?>) o).getName(), value.toString());
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    public void load() {
        for (Config value : configMap.values()) {
            try {
                Gson gson = new GsonBuilder().serializeNulls().addDeserializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getAnnotation(NoDeserialize.class) != null;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).setPrettyPrinting().create();
                File file = new File(System.getProperty("."), value.getName() + ".json");
                if (file.exists()) {
                    configMap.put(value.getClass(), gson.fromJson(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8), value.getClass()));
                }
            } catch (IOException e) {
                Logger.error(e);
            }
        }
    }

    public void save() {
        for (Config value : configMap.values()) {
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Files.write(new File(System.getProperty("."), value.getName() + ".json").toPath(), gson.toJson(value).getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                Logger.error(e);
            }
        }
    }
}
