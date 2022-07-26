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

/**
 * @author Enaium
 * @since 0.5.0
 */
public class HtmlUtil {
    public static String toHtml(String text) {
        return String.format("<html>%s</html>", text);
    }

    public static String setColor(String text, Color color) {
        return String.format("<font color=rgb(%d,%d,%d)>%s</font>", color.getRed(), color.getGreen(), color.getBlue(), text);
    }
}
