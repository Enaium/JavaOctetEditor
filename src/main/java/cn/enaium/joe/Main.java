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

package cn.enaium.joe;

import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.IOUtil;
import cn.enaium.joe.util.MessageUtil;
import cn.enaium.joe.util.ReflectUtil;
import com.formdev.flatlaf.FlatDarkLaf;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Enaium
 */
public final class Main {
    public static void main(String[] args) {
        loadTools();
        launch();
    }


    private static void agent(Instrumentation inst) throws IOException {
        launch();
        Jar jar = new Jar();
        for (Class<?> allLoadedClass : inst.getAllLoadedClasses()) {
            String name = allLoadedClass.getName().replace('.', '/');
            if (name.contains("$$") || name.contains("[") || !inst.isModifiableClass(allLoadedClass)
                    || allLoadedClass.getClassLoader() == Main.class.getClassLoader()
                    || name.matches("(java|sun|javax|com.sun|jdk|javafx).+")) {
                continue;
            }

            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(IOUtil.getBytes(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream(name + ".class"))));
            classReader.accept(classNode, 0);
            jar.classes.put(allLoadedClass.getName(), classNode);
        }
        JavaOctetEditor.getInstance().setJar(jar);
    }

    private static void launch() {
        Logger.info("DIR:{}", System.getProperty("user.dir"));
        FlatDarkLaf.setup();
        new JavaOctetEditor().run();
    }

    private static void loadTools() {
        if (!ReflectUtil.classHas("com.sun.tools.attach.VirtualMachine")) {
            Path toolsPath = Paths.get("lib", "tools.jar");
            Path jrePath = Paths.get(System.getProperty("java.home"));
            Path tool = jrePath.getParent().resolve(toolsPath);
            if (Files.notExists(tool)) {
                Logger.warn("Please use jdk to run");
                return;
            }
            try {
                Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                addURL.setAccessible(true);
                addURL.invoke(Main.class.getClassLoader(), tool.toUri().toURL());
            } catch (NoSuchMethodException | MalformedURLException | InvocationTargetException |
                     IllegalAccessException e) {
                MessageUtil.error(e);
            }
        }
    }
}
