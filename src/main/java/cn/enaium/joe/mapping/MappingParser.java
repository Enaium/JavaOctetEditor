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

package cn.enaium.joe.mapping;

import cn.enaium.joe.util.Pair;
import net.fabricmc.mappingio.MappedElementKind;
import net.fabricmc.mappingio.MappingReader;
import net.fabricmc.mappingio.MappingVisitor;
import net.fabricmc.mappingio.format.MappingFormat;
import org.objectweb.asm.Type;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Enaium
 * @since 1.0.0
 */
public class MappingParser {

    public static Mapping read(Path path, MappingFormat mappingFormat) throws IOException {
        Mapping mapping = new Mapping();
        MappingReader.read(path, mappingFormat, new MappingVisitor() {
            String klass;

            @Override
            public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {

            }

            @Override
            public boolean visitClass(String srcName) throws IOException {
                klass = srcName;
                return true;
            }

            @Override
            public boolean visitField(String srcName, String srcDesc) throws IOException {
                return false;
            }

            @Override
            public boolean visitMethod(String srcName, String srcDesc) throws IOException {
                return false;
            }

            @Override
            public boolean visitMethodArg(int argPosition, int lvIndex, String srcName) throws IOException {
                return false;
            }

            @Override
            public boolean visitMethodVar(int lvtRowIndex, int lvIndex, int startOpIdx, String srcName) throws IOException {
                return false;
            }

            @Override
            public void visitDstName(MappedElementKind targetKind, int namespace, String name) throws IOException {
                if (targetKind == MappedElementKind.CLASS) {
                    if (isProGuard(mappingFormat)) {
                        mapping.CLEAN.put(klass, name);
                    } else {
                        mapping.CLEAN.put(name, klass);
                    }
                }
            }

            @Override
            public void visitComment(MappedElementKind targetKind, String comment) throws IOException {

            }
        });


        MappingReader.read(path, mappingFormat, new MappingVisitor() {
            String klass;
            String field;
            final Pair<String, String> method = new Pair<>("", "");

            @Override
            public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {

            }

            @Override
            public boolean visitClass(String srcName) throws IOException {
                klass = srcName;
                return true;
            }

            @Override
            public boolean visitField(String srcName, String srcDesc) throws IOException {
                field = srcName;
                return true;
            }

            @Override
            public boolean visitMethod(String srcName, String srcDesc) throws IOException {
                method.setKey(srcName);
                method.setValue(srcDesc);
                return true;
            }

            @Override
            public boolean visitMethodArg(int argPosition, int lvIndex, String srcName) throws IOException {
                return false;
            }

            @Override
            public boolean visitMethodVar(int lvtRowIndex, int lvIndex, int startOpIdx, String srcName) throws IOException {
                return false;
            }

            @Override
            public void visitDstName(MappedElementKind targetKind, int namespace, String name) throws IOException {
                switch (targetKind) {
                    case CLASS:
                        if (isProGuard(mappingFormat)) {
                            mapping.MAP.put(name, klass);
                        } else {
                            mapping.MAP.put(klass, name);
                        }
                        break;
                    case FIELD:
                        if (isProGuard(mappingFormat)) {
                            mapping.MAP.put(String.format("%s.%s", mapping.CLEAN.getOrDefault(klass, klass), name), field);
                        } else {
                            mapping.MAP.put(String.format("%s.%s", mapping.CLEAN.getOrDefault(klass, klass), field), name);
                        }
                        break;
                    case METHOD:
                        String cleanName = method.getKey();
                        String cleanArgument = method.getValue();
                        StringBuilder argumentBuilder = new StringBuilder();
                        Type methodType = Type.getMethodType(cleanArgument);
                        argumentBuilder.append("(");
                        for (Type argumentType : methodType.getArgumentTypes()) {
                            String argument = argumentType.getDescriptor();
                            if (mapping.CLEAN.containsKey(argumentType.getInternalName())) {
                                argument = Type.getObjectType(mapping.CLEAN.get(argumentType.getInternalName())).getDescriptor();
                            }
                            argumentBuilder.append(argument);
                        }
                        argumentBuilder.append(")");

                        String ret = methodType.getReturnType().getDescriptor();
                        if (mapping.CLEAN.containsKey(methodType.getReturnType().getInternalName())) {
                            ret = Type.getObjectType(mapping.CLEAN.get(methodType.getReturnType().getInternalName())).getDescriptor();
                        }

                        if (isProGuard(mappingFormat)) {
                            mapping.MAP.put(String.format("%s.%s%s%s", mapping.CLEAN.getOrDefault(klass, klass), name, argumentBuilder, ret), cleanName);
                        } else {
                            mapping.MAP.put(String.format("%s.%s%s%s", mapping.CLEAN.getOrDefault(klass, klass), cleanName, argumentBuilder, ret), name);
                        }
                        break;
                }
            }

            @Override
            public void visitComment(MappedElementKind targetKind, String comment) throws IOException {

            }
        });
        return mapping;
    }

    private static boolean isProGuard(MappingFormat mappingFormat) {
        return mappingFormat == MappingFormat.PROGUARD;
    }
}
