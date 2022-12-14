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
import cn.enaium.joe.core.util.ASMUtil;
import cn.enaium.joe.core.util.ZipUtil;
import org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Input jar task
 *
 * @author Enaium
 * @since 2.0.0
 */
public class InputJarTask extends AbstractTask<JarModel> {

    private final File file;

    public InputJarTask(File file) {
        super("Input Jar");
        this.file = file;
    }

    @Override
    public JarModel get() {
        var jarModel = new JarModel();

        try {
            var jarFile = new JarFile(file);
            float loaded = 0;
            float files = ZipUtil.countFiles(jarFile);


            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if (jarEntry.getName().endsWith(".class")) {
                    ClassReader classReader = new ClassReader(jarFile.getInputStream(new JarEntry(jarEntry.getName())));
                    jarModel.classes.put(jarEntry.getName(), ASMUtil.acceptClassNode(classReader));
                } else if (!jarEntry.isDirectory()) {
                    jarModel.resources.put(jarEntry.getName(), jarFile.getInputStream(new JarEntry(jarEntry.getName())).readAllBytes());
                }
                setProgress((int) ((loaded++ / files) * 100f));
            }
            jarFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return jarModel;
    }
}
