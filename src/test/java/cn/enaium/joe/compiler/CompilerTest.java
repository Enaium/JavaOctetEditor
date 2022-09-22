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

import cn.enaium.joe.util.ASMUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Enaium
 */
class CompilerTest {
    @Test
    public void compile() throws IOException {
        Compiler compiler = new Compiler();
        compiler.addSource("Test", "public class Test { public static void main(String[] args) { System.out.println(0xCAFEBABE); } }");
        Assertions.assertTrue(compiler.compile());
        ClassNode classNode = ASMUtil.acceptClassNode(new ClassReader(compiler.getClasses().get("Test")));
        StringWriter out = new StringWriter();
        classNode.accept(new TraceClassVisitor(new PrintWriter(out)));
        System.out.println(out);
    }
}