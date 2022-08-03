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

import org.jd.core.v1.ClassFileToJavaSourceDecompiler;
import org.jd.core.v1.api.loader.Loader;
import org.jd.core.v1.api.loader.LoaderException;
import org.jd.core.v1.api.printer.Printer;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

/**
 * @author Enaium
 * @since 0.7.0
 */
public class JDCoreDecompiler implements IDecompiler {
    @Override
    public String decompile(ClassNode classNode) {
        ClassWriter classWriter = new ClassWriter(0);
        classNode.accept(classWriter);

        Loader c = new Loader() {
            @Override
            public boolean canLoad(String internalName) {
                return internalName.equals(classNode.name);
            }

            @Override
            public byte[] load(String internalName) throws LoaderException {
                return classWriter.toByteArray();
            }
        };

        ClassFileToJavaSourceDecompiler decompiler = new ClassFileToJavaSourceDecompiler();
        try {
            Printer printer = new Printer() {
                private static final String TAB = "  ";
                private static final String NEWLINE = "\n";

                private int indentationCount = 0;
                private StringBuilder sb = new StringBuilder();

                @Override
                public String toString() {
                    return sb.toString();
                }

                @Override
                public void start(int maxLineNumber, int majorVersion, int minorVersion) {
                }

                @Override
                public void end() {
                }

                @Override
                public void printText(String text) {
                    sb.append(text);
                }

                @Override
                public void printNumericConstant(String constant) {
                    sb.append(constant);
                }

                @Override
                public void printStringConstant(String constant, String ownerInternalName) {
                    sb.append(constant);
                }

                @Override
                public void printKeyword(String keyword) {
                    sb.append(keyword);
                }

                @Override
                public void printDeclaration(int type, String internalTypeName, String name, String descriptor) {
                    sb.append(name);
                }

                @Override
                public void printReference(int type, String internalTypeName, String name, String descriptor, String ownerInternalName) {
                    sb.append(name);
                }

                @Override
                public void indent() {
                    this.indentationCount++;
                }

                @Override
                public void unindent() {
                    this.indentationCount--;
                }

                @Override
                public void startLine(int lineNumber) {
                    for (int i = 0; i < indentationCount; i++) sb.append(TAB);
                }

                @Override
                public void endLine() {
                    sb.append(NEWLINE);
                }

                @Override
                public void extraLine(int count) {
                    while (count-- > 0) sb.append(NEWLINE);
                }

                @Override
                public void startMarker(int type) {
                }

                @Override
                public void endMarker(int type) {
                }
            };
            decompiler.decompile(c, printer, "null");
            return printer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
