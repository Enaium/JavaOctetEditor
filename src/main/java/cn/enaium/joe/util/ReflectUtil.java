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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Enaium
 * @since 1.0.0
 */
public class ReflectUtil {
    public static <T> void setAll(T old, T t) throws NoSuchFieldException, IllegalAccessException {
        for (Field oldField : old.getClass().getDeclaredFields()) {
            oldField.setAccessible(true);
            Field declaredField = t.getClass().getDeclaredField(oldField.getName());
            oldField.set(old, declaredField.get(t));
        }
    }

    public static Field getField(Class<?> klass, String name) throws NoSuchFieldException {
        Field declaredField = klass.getDeclaredField(name);
        declaredField.setAccessible(true);
        return declaredField;
    }

    public static Method getMethod(Class<?> klass, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method declaredMethod = klass.getDeclaredMethod(name, parameterTypes);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Object o, String name) throws IllegalAccessException, NoSuchFieldException {
        return (T) getField(o.getClass(), name).get(o);
    }

    public static boolean classHas(String name) {
        try {
            Class.forName(name);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}
