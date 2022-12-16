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

import cn.enaium.joe.core.model.JarModel;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author Enaium
 * @since 2.0.0
 */
public abstract class SearchInstructionTask<T> extends AbstractTask<T> {

    private final JarModel jar;

    public SearchInstructionTask(String name, JarModel jar) {
        super(name);
        this.jar = jar;
    }

    public void searchInstruction(BiConsumer<ClassNode, AbstractInsnNode> consumer) {
        float loaded = 0;
        float total = 0;
        for (Map.Entry<String, ClassNode> stringClassNodeEntry : jar.classes.entrySet()) {
            for (MethodNode method : stringClassNodeEntry.getValue().methods) {
                total += method.instructions.size();
            }
        }

        for (Map.Entry<String, ClassNode> stringClassNodeEntry : jar.classes.entrySet()) {
            for (MethodNode method : stringClassNodeEntry.getValue().methods) {
                for (AbstractInsnNode instruction : method.instructions) {
                    consumer.accept(stringClassNodeEntry.getValue(), instruction);
                    setProgress((int) ((loaded++ / total) * 100f));
                }
            }
        }
    }
}
