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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Enaium
 * @since 0.9.0
 */
public class IOUtil {
    public static String getString(InputStream inputStream) throws IOException {
        return new String(getBytes(inputStream), StandardCharsets.UTF_8);
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n;
        while (-1 != (n = inputStream.read(buffer))) {
            byteOutputStream.write(buffer, 0, n);
        }
        inputStream.close();
        return byteOutputStream.toByteArray();
    }

    public static Icon getIcon(byte[] data) throws IOException {
        return new ImageIcon(ImageIO.read(new ByteArrayInputStream(data)));
    }

    public static boolean isImage(byte[] data) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        try {
            BufferedImage read = ImageIO.read(byteArrayInputStream);
            return read != null;
        } catch (Throwable ignore) {
            return false;
        } finally {
            try {
                byteArrayInputStream.close();
            } catch (IOException e) {
                MessageUtil.error(e);
            }
        }
    }
}
