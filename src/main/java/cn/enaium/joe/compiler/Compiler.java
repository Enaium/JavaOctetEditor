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

package cn.enaium.joe.compiler;

import javax.tools.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Enaium
 * @since 1.4.0
 */
public class Compiler {
    private final Map<String, VirtualJavaFileObject> javaFileObjectMap = new HashMap<>();

    private DiagnosticListener<VirtualJavaFileObject> listener;

    public void addSource(String name, String content) {
        javaFileObjectMap.put(name, new VirtualJavaFileObject(name, content));
    }

    public Map<String, byte[]> getClasses() {
        return javaFileObjectMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().getBytecode()));
    }

    @SuppressWarnings("unchecked")
    public boolean compile() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        VirtualFileManager fileManager = new VirtualFileManager(compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8));
        try {
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, ((DiagnosticListener<? super JavaFileObject>) (Object) listener), null, null, javaFileObjectMap.values());
            Boolean b = task.call();
            return b != null && b;
        } catch (Exception e) {
            return false;
        }
    }

    public void setListener(DiagnosticListener<VirtualJavaFileObject> listener) {
        this.listener = listener;
    }

    private final class VirtualFileManager extends ForwardingJavaFileManager<JavaFileManager> {
        public VirtualFileManager(JavaFileManager fileManager) {
            super(fileManager);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
            if (JavaFileObject.Kind.CLASS == kind) {
                VirtualJavaFileObject virtualJavaFileObject = new VirtualJavaFileObject(className, null);
                javaFileObjectMap.put(className, virtualJavaFileObject);
                return virtualJavaFileObject;
            } else {
                return super.getJavaFileForOutput(location, className, kind, sibling);
            }
        }
    }
}
