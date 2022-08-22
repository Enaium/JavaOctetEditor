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

import cn.enaium.joe.config.extend.ApplicationConfig;
import cn.enaium.joe.config.extend.CFRConfig;
import cn.enaium.joe.config.value.Value;
import cn.enaium.joe.util.MessageUtil;
import cn.enaium.joe.util.ReflectUtil;
import com.google.gson.*;
import com.google.gson.annotations.Expose;

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
                        map.put(((Value<?>) o).getName(), value.toString());
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    private Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    public void load() {
        for (Map.Entry<Class<? extends Config>, Config> classConfigEntry : configMap.entrySet()) {

            Class<? extends Config> klass = classConfigEntry.getKey();
            Config config = classConfigEntry.getValue();
            try {
                File file = new File(System.getProperty("."), config.getName() + ".json");
                if (file.exists()) {
                    //Step.1 get json object
                    JsonObject jsonObject = gson().fromJson(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8), JsonObject.class);

                    //Step.2 get all field of the config
                    for (Field configField : klass.getDeclaredFields()) {
                        configField.setAccessible(true);
                        if (!jsonObject.has(configField.getName())) {
                            continue;
                        }

                        //Step.3 deserialize
                        Object setting = gson().fromJson(jsonObject.get(configField.getName()).toString(), configField.getType());

                        //Step.3.1 get all field of the setting
                        for (Field settingField : setting.getClass().getDeclaredFields()) {
                            settingField.setAccessible(true);

                            if (settingField.isAnnotationPresent(Expose.class)) {
                                if (!settingField.getAnnotation(Expose.class).deserialize()) {
                                    Field declaredField = ReflectUtil.getField(setting.getClass(), settingField.getName());
                                    //Step.3.2 use the value from config to set the value of setting
                                    declaredField.set(setting, ReflectUtil.getFieldValue(ReflectUtil.getFieldValue(config, configField.getName()), settingField.getName()));
                                }
                            }
                        }

                        //Step.4 use the value from step 3 to set the value of config
                        configField.set(config, setting);
                    }
                }
            } catch (Throwable e) {
                MessageUtil.error(e);
            }
        }
    }

    public void save() {
        for (Config value : configMap.values()) {
            try {
                Files.write(new File(System.getProperty("."), value.getName() + ".json").toPath(), gson().toJson(value).getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                MessageUtil.error(e);
            }
        }
    }
}
