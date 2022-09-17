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

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.pmw.tinylog.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class ASMUtil {
    public static <T> Object toType(Class<T> type, String text) {
        try {
            Method valueOf = type.getMethod("valueOf", String.class);
            return valueOf.invoke(null, text);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            if (type == Type.class) {
                return Type.getType(text);
            } else {
                return text;
            }
        }
    }

    public static ClassNode acceptClassNode(ClassReader classReader) {
        ClassNode classNode = new ClassNode();
        try {
            classReader.accept(classNode, ClassReader.EXPAND_FRAMES);
        } catch (Throwable throwable) {
            try {
                classReader.accept(classNode, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);
            } catch (Throwable ignored) {

            }
        }
        return classNode;
    }
}
