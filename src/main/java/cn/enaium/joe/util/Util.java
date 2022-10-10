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

import java.awt.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Enaium
 */
public class Util {
    public static int countFiles(JarFile zipFile) {
        final Enumeration<? extends JarEntry> entries = zipFile.entries();
        int c = 0;
        while (entries.hasMoreElements()) {
            entries.nextElement();
            ++c;
        }
        return c;
    }

    public static boolean isText(byte[] bytes) {
        int total = bytes.length;
        if (total >= 8000) {
            total = 8000;
        }
        for (int i = 0; i < total; i++) {
            if (((char) bytes[i]) == '\0') {
                return false;
            }
        }
        return true;
    }

    public static Dimension screenSize(int width, int height) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return new Dimension((int) (width * screenSize.getWidth() / 1920), (int) (height * screenSize.getHeight() / 1080));
    }
}
