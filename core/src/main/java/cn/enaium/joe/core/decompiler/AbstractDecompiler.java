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

package cn.enaium.joe.core.decompiler;

import cn.enaium.joe.core.config.ConfigManager;
import cn.enaium.joe.core.model.JarModel;

import org.objectweb.asm.tree.ClassNode;

import java.util.Map;

public abstract class AbstractDecompiler {

    public final JarModel jar;
    public final ConfigManager config;
    public final ClassNode classNode;

    public AbstractDecompiler(JarModel jar, ConfigManager config, ClassNode classNode) {
        this.jar = jar;
        this.config = config;
        this.classNode = classNode;
    }

    abstract String decompile() throws Exception;
}
