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

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.config.extend.ApplicationConfig;
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.IOUtil;
import cn.enaium.joe.util.MessageUtil;
import cn.enaium.joe.util.Util;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.tinylog.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Enaium
 * @since 0.10.0
 */
public class InputJarTask extends AbstractTask<Jar> {
    private final File file;

    public InputJarTask(File file) {
        super("InputJar");
        this.file = file;
    }

    @Override
    public Jar get() {
        Logger.info("LOAD:{}", file.getAbsolutePath());
        Jar jar = new Jar();
        try {
            JarFile jarFile = new JarFile(file);
            float loaded = 0;
            float files = Util.countFiles(jarFile);


            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if (jarEntry.getName().endsWith(".class")) {
                    ClassReader classReader = new ClassReader(IOUtil.getBytes(jarFile.getInputStream(new JarEntry(jarEntry.getName()))));
                    ClassNode classNode = new ClassNode();
                    try {
                        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);
                    } catch (Throwable throwable) {
                        try {
                            classReader.accept(classNode, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);
                        } catch (Throwable last) {
                            Logger.error("FILE:{},{}", jarEntry.getName(), last);
                        }
                    }
                    jar.classes.put(jarEntry.getName(), classNode);
                } else if (!jarEntry.isDirectory()) {
                    jar.resources.put(jarEntry.getName(), IOUtil.getBytes(jarFile.getInputStream(new JarEntry(jarEntry.getName()))));
                }
                setProgress((int) ((loaded++ / files) * 100f));
            }
            jarFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JavaOctetEditor.getInstance().config.getByClass(ApplicationConfig.class).loadRecent.getValue().add(file.getAbsolutePath());
        JavaOctetEditor.getInstance().setJar(jar);
        JavaOctetEditor.getInstance().window.setTitle(JavaOctetEditor.TITLE + "-" + file.getName());
        return jar;
    }
}
