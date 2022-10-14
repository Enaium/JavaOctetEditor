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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class ASMUtil {
    /**
     * converts a string value to a value of that type
     *
     * @param type value type
     * @param text value text
     * @param <T>  value type
     * @return value of type
     */
    public static <T> Object valueOf(Class<T> type, String text) {
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


    /**
     * accept as class node
     *
     * @param classReader class reader
     * @return class node
     */
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

    /**
     * get all parents, include interfaces
     *
     * @param classNode class node
     * @return all interfaces and superclasses
     */
    public static Set<String> getParentClass(ClassNode classNode) {
        Set<String> parent = new HashSet<>();
        if (classNode.superName != null && !classNode.superName.equals(Object.class.getName().replace(".", "/"))) {
            parent.add(classNode.superName);
        }
        parent.addAll(classNode.interfaces);
        return parent;
    }

    /**
     * replace all `/` to `.`
     *
     * @param classNode class node
     * @return reference name
     */
    public static String getReferenceName(ClassNode classNode) {
        return classNode.name.replace("/", ".");
    }
}
