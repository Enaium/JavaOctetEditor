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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
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
        configMap.put(ApplicationConfig.class, new ApplicationConfig());
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

    public void load() {
        for (Config value : configMap.values()) {
            try {
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
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
