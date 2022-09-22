package cn.enaium.joe.asm;

import cn.enaium.joe.compiler.Compiler;
import cn.enaium.joe.util.ASMUtil;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Enaium
 */
class VisitorTest {
    @Test
    public void test() throws IOException {
        StringWriter stringWriter = new StringWriter();
        ClassReader classReader = new ClassReader(this.getClass().getName());
        classReader.accept(new TraceClassVisitor(null, new ASMifier(), new PrintWriter(stringWriter)), 0);
        Compiler compiler = new Compiler();
        String name = "asm." + this.getClass().getName() + "Dump";
        compiler.addSource(name, stringWriter.toString());
        assertTrue(compiler.compile());
        ClassNode classNode = ASMUtil.acceptClassNode(new ClassReader(compiler.getClasses().get(name)));
        StringWriter out = new StringWriter();
        classNode.accept(new TraceClassVisitor(new PrintWriter(out)));
        System.out.println(out);
    }
}