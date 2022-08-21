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
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.MessageUtil;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Enaium
 * @since 0.10.0
 */
public class OutputJarTask extends AbstractTask<Boolean> {
    private final Jar jar;
    private final File out;

    public OutputJarTask(Jar jar, File out) {
        super("OutputJar");
        this.jar = jar;
        this.out = out;
    }

    @Override
    public Boolean get() {
        float loaded = 0;
        float files = jar.classes.size() + jar.resources.size();

        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(out.toPath()));
            for (Map.Entry<String, ClassNode> stringClassNodeEntry : jar.classes.entrySet()) {
                zipOutputStream.putNextEntry(new JarEntry(stringClassNodeEntry.getValue().name + ".class"));
                ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                stringClassNodeEntry.getValue().accept(classWriter);
                zipOutputStream.write(classWriter.toByteArray());
                setProgress((int) ((loaded++ / files) * 100f));
            }

            for (Map.Entry<String, byte[]> stringEntry : jar.resources.entrySet()) {
                zipOutputStream.putNextEntry(new JarEntry(stringEntry.getKey()));
                zipOutputStream.write(stringEntry.getValue());
                setProgress((int) ((loaded++ / files) * 100f));
            }
            zipOutputStream.closeEntry();
            zipOutputStream.close();
        } catch (IOException ex) {
            MessageUtil.error(ex);
            return false;
        }
        return true;
    }
}
