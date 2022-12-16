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

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import javax.imageio.ImageIO


/**
 * @author Enaium
 * @since 2.0.0
 */

fun getString(inputStream: InputStream): String {
    return String(getBytes(inputStream), StandardCharsets.UTF_8)
}

fun getBytes(inputStream: InputStream): ByteArray {
    val byteOutputStream = ByteArrayOutputStream()
    val buffer = ByteArray(1024 * 4)
    var n: Int
    while (-1 != inputStream.read(buffer).also { n = it }) {
        byteOutputStream.write(buffer, 0, n)
    }
    inputStream.close()
    return byteOutputStream.toByteArray()
}

fun isImage(data: ByteArray): Boolean {
    val byteArrayInputStream = ByteArrayInputStream(data)
    return try {
        val read = ImageIO.read(byteArrayInputStream)
        read != null
    } catch (ignore: Throwable) {
        false
    } finally {
        try {
            byteArrayInputStream.close()
        } catch (e: IOException) {

        }
    }
}

fun isText(byteArray: ByteArray): Boolean {
    var total: Int = byteArray.size
    if (total >= 8000) {
        total = 8000
    }
    for (i in 0 until total) {
        if (byteArray[i].toInt().toChar() == '\u0000') {
            return false
        }
    }
    return true
}