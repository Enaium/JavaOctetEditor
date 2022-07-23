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

package cn.enaium.joe.jar;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.util.Util;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Enaium
 */
public class Jar {
    public Map<String, ClassNode> classes = new LinkedHashMap<>();
    public Map<String, byte[]> resources = new LinkedHashMap<>();

    public void load(File file) {
        JavaOctetEditor.getInstance().jar = this;
        JavaOctetEditor.getInstance().window.setTitle(JavaOctetEditor.TITLE + "-" + file.getName());
        try {
            JarFile jarFile = new JarFile(file);
            float loaded = 0;
            float files = Util.countFiles(jarFile);


            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if (jarEntry.getName().endsWith(".class")) {
                    ClassReader classReader = new ClassReader(jarFile.getInputStream(new JarEntry(jarEntry.getName())));
                    ClassNode classNode = new ClassNode();
                    try {
                        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);
                    } catch (Throwable throwable) {
                        classReader.accept(classNode, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);
                    }
                    classes.put(jarEntry.getName(), classNode);
                } else if (!jarEntry.isDirectory()) {
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int n;
                    InputStream inputStream = jarFile.getInputStream(new JarEntry(jarEntry.getName()));
                    while (-1 != (n = inputStream.read(buffer))) {
                        output.write(buffer, 0, n);
                    }
                    resources.put(jarEntry.getName(), output.toByteArray());
                }

                JavaOctetEditor.getInstance().bottomPanel.setProcess((int) ((loaded++ / files) * 100f));
            }
            jarFile.close();
            JavaOctetEditor.getInstance().bottomPanel.setProcess(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JavaOctetEditor.getInstance().fileTreePanel.refresh(this);
    }

    public Jar copy() {
        Jar jar = new Jar();
        jar.classes.putAll(classes);
        jar.resources.putAll(resources);
        return jar;
    }
}
