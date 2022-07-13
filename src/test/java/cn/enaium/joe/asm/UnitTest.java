package cn.enaium.joe.asm;

import javassist.*;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Enaium
 */
class UnitTest {
    @Test
    public void test() throws IOException, CannotCompileException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NotFoundException {
        StringWriter stringWriter = new StringWriter();
        ClassReader classReader = new ClassReader(this.getClass().getName());
        classReader.accept(new TraceClassVisitor(null, new ASMifier(), new PrintWriter(stringWriter)), 0);
        ClassPool classPool = ClassPool.getDefault();
        classPool.importPackage("org.objectweb.asm.AnnotationVisitor");
        classPool.importPackage("org.objectweb.asm.Attribute");
        classPool.importPackage("org.objectweb.asm.ClassReader");
        classPool.importPackage("org.objectweb.asm.ClassWriter");
        classPool.importPackage("org.objectweb.asm.ConstantDynamic");
        classPool.importPackage("org.objectweb.asm.FieldVisitor");
        classPool.importPackage("org.objectweb.asm.Handle");
        classPool.importPackage("org.objectweb.asm.Label");
        classPool.importPackage("org.objectweb.asm.MethodVisitor");
        classPool.importPackage("org.objectweb.asm.Opcodes");
        classPool.importPackage("org.objectweb.asm.RecordComponentVisitor");
        classPool.importPackage("org.objectweb.asm.Type");
        classPool.importPackage("org.objectweb.asm.TypePath");
        CtClass ctClass = classPool.makeClass(ASMifier.class.getSimpleName());
        ctClass.addInterface(classPool.get("org.objectweb.asm.Opcodes"));
        String substring = stringWriter.toString().substring(stringWriter.toString().indexOf("{") + 1, stringWriter.toString().lastIndexOf("}"));
        ctClass.addMethod(CtMethod.make(substring, ctClass));
        System.out.println(ctClass.toClass().getMethod("dump").invoke(null));
    }
}