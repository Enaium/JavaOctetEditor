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

import cn.enaium.joe.core.annotation.Indeterminate;
import cn.enaium.joe.core.mapping.Mapping;
import cn.enaium.joe.core.mapping.MappingParser;
import cn.enaium.joe.core.model.JarModel;
import net.fabricmc.mappingio.format.MappingFormat;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.SimpleRemapper;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Enaium
 * @since 2.0.0
 */
@Indeterminate
public class RemappingTask extends AbstractTask<JarModel> {
    public final Map<String, Set<String>> superHashMap = new HashMap<>();

    private final JarModel jar;
    private final File mapping;
    private final MappingFormat mappingFormat;

    public RemappingTask(JarModel jar, File mapping, MappingFormat mappingFormat) {
        super("Remapping");
        this.jar = jar;
        this.mapping = mapping;
        this.mappingFormat = mappingFormat;
    }

    @Override
    public JarModel get() {

        if (mappingFormat == MappingFormat.ENIGMA) {
            if (!mapping.getName().endsWith(".mapping")) {
                throw new RuntimeException("File suffix must be mapping");
            }
        }

        try {
            Mapping read = MappingParser.read(mapping.toPath(), mappingFormat);
            JarModel newJar = new JarModel();
            newJar.resources.putAll(jar.resources);

            jar.classes.values().forEach(this::analyze);

            for (Map.Entry<String, ClassNode> stringClassNodeEntry : jar.classes.entrySet()) {
                ClassNode classNode = new ClassNode();
                ClassRemapper classRemapper = new ClassRemapper(new ClassVisitor(Opcodes.ASM9, classNode) {
                }, new SimpleRemapper(read.MAP) {
                    @Override
                    public String mapFieldName(String owner, String name, String descriptor) {
                        String remappedName = map(owner + '.' + name);
                        if (remappedName == null) {
                            if (superHashMap.containsKey(owner)) {
                                for (String s : superHashMap.get(owner)) {
                                    String rn = mapFieldName(s, name, descriptor);
                                    if (rn != null) {
                                        return rn;
                                    }
                                }
                            }
                        }

                        return remappedName == null ? name : remappedName;
                    }

                    @Override
                    public String mapMethodName(String owner, String name, String descriptor) {
                        String remappedName = map(owner + '.' + name + descriptor);
                        if (remappedName == null) {
                            if (superHashMap.containsKey(owner)) {
                                for (String s : superHashMap.get(owner)) {
                                    String rn = mapMethodName(s, name, descriptor);
                                    if (rn != null) {
                                        return rn;
                                    }
                                }
                            }
                        }
                        return remappedName == null ? name : remappedName;
                    }
                });
                stringClassNodeEntry.getValue().accept(classRemapper);
                newJar.classes.put(stringClassNodeEntry.getKey(), classNode);
            }
            return newJar;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void analyze(ClassNode classNode) {
        classNode.accept(new ClassVisitor(Opcodes.ASM9) {
            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                Set<String> strings = new HashSet<>();
                if (superHashMap.containsKey(name)) {
                    if (superName != null) {
                        if (!superHashMap.get(name).contains(superName)) {
                            strings.add(superName);
                        }
                    }

                    if (interfaces != null) {
                        for (String inter : interfaces) {
                            if (!superHashMap.get(name).contains(inter)) {
                                strings.add(inter);
                            }
                        }
                    }
                    superHashMap.get(name).addAll(strings);
                } else {
                    if (superName != null) {
                        strings.add(superName);
                    }

                    if (interfaces != null) {
                        Collections.addAll(strings, interfaces);
                    }
                    superHashMap.put(name, strings);
                }
                super.visit(version, access, name, signature, superName, interfaces);
            }
        });
    }
}
