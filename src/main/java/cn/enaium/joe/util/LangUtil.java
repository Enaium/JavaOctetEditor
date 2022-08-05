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

package cn.enaium.joe.util;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.config.extend.ApplicationConfig;
import cn.enaium.joe.config.value.ModeValue;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.tinylog.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class LangUtil {

    public static String i18n(String key, Object... args) {
        Locale locale = Locale.getDefault();
        String lang = locale.getLanguage() + "_" + locale.getCountry();
        ModeValue language = JavaOctetEditor.getInstance().configManager.getByClass(ApplicationConfig.class).language;
        if (!language.getValue().equals("System")) {
            lang = language.getValue();
        }
        try {
            URL url = LangUtil.class.getResource("/lang/" + lang + ".json");
            if (url == null) {
                url = LangUtil.class.getResource("/lang/en_US.json");
            }

            if (url == null) {
                RuntimeException runtimeException = new RuntimeException("Lang not Found!");
                MessageUtil.error(runtimeException);
                throw runtimeException;
            }
            InputStream inputStream = url.openStream();
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            String text = stringBuilder.toString();
            inputStream.close();
            JsonObject jsonObject = new Gson().fromJson(text, JsonObject.class);
            try {
                return String.format(jsonObject.get(key).getAsString(), args);
            } catch (NullPointerException e) {
                MessageUtil.error(new NullPointerException(String.format("Lang not found \" %s \"", key)));
            }
        } catch (IOException e) {
            MessageUtil.error(e);
        }
        return key;
    }
}