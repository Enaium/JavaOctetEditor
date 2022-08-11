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

package cn.enaium.joe.asm;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.AbstractInsnNode;

import java.lang.reflect.Field;

/**
 * @author Enaium
 */
class GenerateInstructionTypeSwitchTest {
    @Test
    public void test() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("switch (i) {").append("\n");
        for (Field field : AbstractInsnNode.class.getFields()) {
            stringBuilder.append(String.format("case AbstractInsnNode.%s: break;", field.getName()));
        }
        stringBuilder.append("}");
        System.out.println(stringBuilder);
    }
}
