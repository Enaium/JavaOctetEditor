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

package cn.enaium.joe.service.decompiler;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.config.extend.CFRConfig;
import org.benf.cfr.reader.PluginRunner;
import org.benf.cfr.reader.api.ClassFileSource;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.Pair;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author Enaium
 * @since 0.7.0
 */
public class CFRDecompiler implements IDecompiler {
    @Override
    public String decompile(ClassNode classNode) {
        ClassFileSource cfs = new ClassFileSource() {
            @Override
            public void informAnalysisRelativePathDetail(String a, String b) {
            }

            @Override
            public String getPossiblyRenamedPath(String path) {
                return path;
            }

            @Override
            public Pair<byte[], String> getClassFileContent(String path) throws IOException {
                String name = path.substring(0, path.length() - 6);
                if (name.equals(classNode.name)) {
                    ClassWriter classWriter = new ClassWriter(0);
                    classNode.accept(classWriter);
                    return Pair.make(classWriter.toByteArray(), name);
                }
                return null;
            }

            @Override
            public Collection<String> addJar(String arg0) {
                throw new RuntimeException();
            }
        };
        return new PluginRunner(JavaOctetEditor.getInstance().configManager.getConfigMap(CFRConfig.class), cfs).getDecompilationFor(classNode.name);
    }
}
