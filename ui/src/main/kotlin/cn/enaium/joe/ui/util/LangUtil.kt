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

package cn.enaium.joe.ui.util

import cn.enaium.joe.core.config.extend.ApplicationConfig
import cn.enaium.joe.core.config.value.ModeValue
import cn.enaium.joe.ui.JavaOctetEditor
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.pmw.tinylog.Logger
import java.io.IOException
import java.util.*

/**
 * @author Enaium
 * @since 2.0.0
 */
fun i18n(key: String, vararg arguments: Any): String {
    val locale: Locale = Locale.getDefault()
    var lang: String = locale.language + "_" + locale.country
    val language: ModeValue = JavaOctetEditor.config.getByClass(
        ApplicationConfig::class.java).language
    if (!language.value.equals("System")) {
        lang = language.value
    }
    try {
        val text: String
        val url = object {}::class.java.getResource("/i18n/$lang.json")
        text = if (url != null) {
            getString(url.openStream())
        } else {
            getString(object {}::class.java.getResourceAsStream("/i18n/en_US.json")!!)
        }
        val jsonObject: JsonObject = Gson().fromJson(text, JsonObject::class.java)
        try {
            return java.lang.String.format(jsonObject.get(key).asString, arguments)
        } catch (e: NullPointerException) {
            Logger.warn(java.lang.String.format("Lang not found \" %s \" ", key))
            try {
                return java.lang.String.format(
                    Gson().fromJson(text, JsonObject::class.java).get(key).asString,
                    arguments
                )
            } catch (ex: NullPointerException) {
                error(NullPointerException(java.lang.String.format("not found key ' %s ' in en_us", key)))
            }
        }
    } catch (e: IOException) {
        error(e)
    }
    return key
}