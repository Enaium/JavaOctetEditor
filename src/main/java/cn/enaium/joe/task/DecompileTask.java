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

package cn.enaium.joe.task;

import cn.enaium.joe.annotation.Repeatable;
import cn.enaium.joe.service.DecompileService;
import org.objectweb.asm.tree.ClassNode;
import org.pmw.tinylog.Logger;

/**
 * @author Enaium
 * @since 0.10.0
 */
@Repeatable
public class DecompileTask extends AbstractTask<String> {

    private final ClassNode classNode;

    public DecompileTask(ClassNode classNode) {
        super("Decompile");
        this.classNode = classNode;
    }

    @Override
    public String get() {
        Logger.info("DECOMPILE:{}", classNode.name);
        return DecompileService.getService().decompile(classNode);
    }
}
