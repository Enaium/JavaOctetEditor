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

import javafx.scene.image.Image;
import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;

import java.io.ByteArrayInputStream;

/**
 * @author Enaium
 * @since 1.3.0
 */
public class ImageUtil {
    public static SVGImage loadSVG(String file) {
        return SVGLoader.load(ImageUtil.class.getClassLoader().getResource(file), 12);
    }
    public static Image load(byte[] data) {
        return new Image(new ByteArrayInputStream(data));
    }
}
