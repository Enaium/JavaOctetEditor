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

import com.strobel.decompiler.languages.java.JavaFormattingOptions;
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Enaium
 * @since 1.1.0
 */
public class GenerateDecompilerConfigTest {
    @Test
    public void fernFlower() throws IllegalAccessException {
        Map<String, Object> defaults = IFernflowerPreferences.getDefaults();
        for (Field field : IFernflowerPreferences.class.getFields()) {
            if (field.isAnnotationPresent(IFernflowerPreferences.Name.class) && field.isAnnotationPresent(IFernflowerPreferences.Description.class)) {
                String f = field.getName();
                String name = field.getAnnotation(IFernflowerPreferences.Name.class).value();
                String description = field.getAnnotation(IFernflowerPreferences.Description.class).value();
                Object value = field.get(null);
                Object o = defaults.get(value);

                if (o != null) {
                    if (o.equals("1")) {
                        o = true;
                    } else if (o.equals("0")) {
                        o = false;
                    } else if (o instanceof String) {
                        o = "\"" + o + "\"";
                    }
                } else {
                    System.out.println("NULL:" + f);
                }


                System.out.printf("public EnableValue %s = new EnableValue(\"%s\", %s,\"%s\");%n", value, name, o, description);
            }
        }
    }

    @Test
    public void procyon() throws IllegalAccessException {
        JavaFormattingOptions aDefault = JavaFormattingOptions.createDefault();
        for (Field field : JavaFormattingOptions.class.getFields()) {
            String type = null;
            Object value = field.get(aDefault);
            if (value instanceof Boolean) {
                type = "EnableValue";
            } else if (value instanceof Enum<?>) {
                type = "ModeValue";
            } else if (value instanceof Integer) {
                type = "IntegerValue";
            }

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("public");
            stringBuilder.append(" ");
            stringBuilder.append(type);
            stringBuilder.append(" ");
            stringBuilder.append(field.getName());
            stringBuilder.append("=");
            stringBuilder.append("new");
            stringBuilder.append(" ");
            stringBuilder.append(type);
            stringBuilder.append("(");
            stringBuilder.append("\"").append(field.getName()).append("\"").append(",");
            if (value instanceof Enum<?>) {
                stringBuilder.append("\"").append(value).append("\"").append(",");
            } else {
                stringBuilder.append(value).append(",");
            }
            stringBuilder.append("\"").append(field.getName()).append("\"");
            if (value instanceof Enum<?>) {
                stringBuilder.append(",");
                stringBuilder.append("Arrays.asList").append("(");
                int index = 0;
                Field[] eFields = ((Enum<?>) value).getDeclaringClass().getFields();
                for (Field e : eFields) {
                    stringBuilder.append("\"").append(e.getName()).append("\"");

                    if (index != eFields.length - 1) {
                        stringBuilder.append(",");
                    }

                    index++;
                }
                stringBuilder.append(")");
            }
            stringBuilder.append(");");
            System.out.println(stringBuilder);
        }
    }
}
