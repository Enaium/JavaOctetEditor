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

package cn.enaium.joe.core.util;

import cn.enaium.joe.core.config.ConfigManager;
import cn.enaium.joe.core.config.extend.ApplicationConfig;
import cn.enaium.joe.core.decompiler.CFRDecompiler;
import cn.enaium.joe.core.decompiler.FernFlowerDecompiler;
import cn.enaium.joe.core.decompiler.ProcyonDecompiler;
import cn.enaium.joe.core.model.JarModel;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;

/**
 * @author Enaium
 * @since 2.0.0
 */
public class DecompileUtil {
    public static String get(JarModel jar, ConfigManager config, ClassNode classNode) {
        switch (config.getByClass(ApplicationConfig.class).decompilerMode.getValue()) {
            case "CFR" -> {
                return new CFRDecompiler(jar, config, classNode).decompile();
            }
            case "Procyon" -> {
                return new ProcyonDecompiler(jar, config, classNode).decompile();
            }
            case "FernFlower" -> {
                try {
                    return new FernFlowerDecompiler(jar, config, classNode).decompile();
                } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
                    return e.getMessage();
                }
            }
        }
        return "NULL";
    }
}
