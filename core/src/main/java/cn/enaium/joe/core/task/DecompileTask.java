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

package cn.enaium.joe.core.task;

import cn.enaium.joe.core.annotation.Repeatable;
import cn.enaium.joe.core.config.ConfigManager;
import cn.enaium.joe.core.config.extend.ApplicationConfig;
import cn.enaium.joe.core.decompiler.CFRDecompiler;
import cn.enaium.joe.core.decompiler.FernFlowerDecompiler;
import cn.enaium.joe.core.decompiler.ProcyonDecompiler;
import cn.enaium.joe.core.model.JarModel;

import cn.enaium.joe.core.util.DecompileUtil;
import org.objectweb.asm.tree.ClassNode;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author Enaium
 * @since 2.0.0
 */
@Repeatable
public class DecompileTask extends AbstractTask<String> {

    private final JarModel jar;
    private final ConfigManager config;
    private final ClassNode classNode;

    public DecompileTask(JarModel jar, ConfigManager config, ClassNode classNode) {
        super("Decompile");
        this.jar = jar;
        this.config = config;
        this.classNode = classNode;
    }

    @Override
    public String get() {
        Logger.info("DECOMPILE:{}", classNode.name);
        return DecompileUtil.get(jar, config, classNode);
    }
}
