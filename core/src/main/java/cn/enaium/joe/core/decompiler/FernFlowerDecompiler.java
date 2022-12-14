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

package cn.enaium.joe.core.decompiler;

import cn.enaium.joe.core.config.ConfigManager;
import cn.enaium.joe.core.config.extend.FernFlowerConfig;

import cn.enaium.joe.core.model.JarModel;
import cn.enaium.joe.core.util.ReflectUtil;
import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.main.extern.IResultSaver;
import org.jetbrains.java.decompiler.struct.ContextUnit;
import org.jetbrains.java.decompiler.struct.StructClass;
import org.jetbrains.java.decompiler.struct.StructContext;
import org.jetbrains.java.decompiler.struct.lazy.LazyLoader;
import org.jetbrains.java.decompiler.util.DataInputFullStream;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Manifest;

/**
 * @author Enaium
 * @since 2.0.0
 */
public class FernFlowerDecompiler extends AbstractDecompiler implements IBytecodeProvider, IResultSaver {

    private byte[] bytes;
    private String returned;

    public FernFlowerDecompiler(JarModel jar, ConfigManager config, ClassNode classNode) {
        super(jar, config, classNode);
    }


    @Override
    public String decompile() throws IOException, NoSuchFieldException, IllegalAccessException {
        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        bytes = classWriter.toByteArray();

        Fernflower fernflower = new Fernflower(this, this, new HashMap<String, Object>() {{
            config.getConfigMap(FernFlowerConfig.class).forEach((k, v) -> {
                if (v.equals("true")) {
                    v = "1";
                } else if (v.equals("false")) {
                    v = "0";
                }
                this.put(k, v);
            });
        }}, new IFernflowerLogger() {
            @Override
            public void writeMessage(String message, Severity severity) {

            }

            @Override
            public void writeMessage(String message, Severity severity, Throwable t) {

            }
        });

        File file = new File("class.class");
        fernflower.addSource(file);
        StructContext structContext = ReflectUtil.getFieldValue(fernflower, "structContext");
        LazyLoader loader = ReflectUtil.getFieldValue(structContext, "loader");
        loader.addClassLink(file.getName(), new LazyLoader.Link(file.getName(), null));

        StructClass structClass = StructClass.create(new DataInputFullStream(bytes), true, loader);
        ContextUnit contextUnit = new ContextUnit(ContextUnit.TYPE_FOLDER, null, file.getName(), true, this, fernflower);
        contextUnit.addClass(structClass, file.getName());
        fernflower.decompileContext();
        return returned;
    }

    @Override
    public byte[] getBytecode(String externalPath, String internalPath) throws IOException {
        return bytes;
    }

    @Override
    public void saveFolder(String path) {

    }

    @Override
    public void copyFile(String source, String path, String entryName) {

    }

    @Override
    public void saveClassFile(String path, String qualifiedName, String entryName, String content, int[] mapping) {
        returned = content;
    }

    @Override
    public void createArchive(String path, String archiveName, Manifest manifest) {

    }

    @Override
    public void saveDirEntry(String path, String archiveName, String entryName) {

    }

    @Override
    public void copyEntry(String source, String path, String archiveName, String entry) {

    }

    @Override
    public void saveClassEntry(String path, String archiveName, String qualifiedName, String entryName, String content) {

    }

    @Override
    public void closeArchive(String path, String archiveName) {

    }
}
