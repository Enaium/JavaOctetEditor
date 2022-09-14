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

import cn.enaium.joe.MainFX;
import cn.enaium.joe.config.extend.ApplicationConfig;
import cn.enaium.joe.config.value.ModeValue;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

public class LangUtil {

    public static String i18n(String key, Object... args) {
        Locale locale = Locale.getDefault();
        String lang = locale.getLanguage() + "_" + locale.getCountry();
        ModeValue language = MainFX.getInstance().config.getByClass(ApplicationConfig.class).language;
        if (!language.getValue().equals("System")) {
            lang = language.getValue();
        }
        try {

            String en = IOUtil.getString(LangUtil.class.getResourceAsStream("/lang/en_US.json"));

            URL url = LangUtil.class.getResource("/lang/" + lang + ".json");

            if (url == null) {
                RuntimeException runtimeException = new RuntimeException(String.format("lang ' %s ' not Found!", lang));
                MessageUtil.error(runtimeException);
                throw runtimeException;
            }

            JsonObject jsonObject = new Gson().fromJson(IOUtil.getString(url.openStream()), JsonObject.class);
            try {
                return String.format(jsonObject.get(key).getAsString(), args);
            } catch (NullPointerException e) {
                Logger.warn(String.format("Lang not found \" %s \" ", key));
                try {
                    return String.format(new Gson().fromJson(en, JsonObject.class).get(key).getAsString(), args);
                } catch (NullPointerException ex) {
                    MessageUtil.error(new NullPointerException(String.format("not found key ' %s ' in en_us", key)));
                }
            }
        } catch (IOException e) {
            MessageUtil.error(e);
        }
        return key;
    }
}