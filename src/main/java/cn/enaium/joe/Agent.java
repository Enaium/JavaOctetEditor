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
import cn.enaium.joe.util.ReflectUtil;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Enaium
 * @since 1.1.0
 */
public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) throws Exception {
        agent(agentArgs, inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) throws Exception {
        agent(agentArgs, inst);
    }

    private static void agent(String agentArgs, Instrumentation inst) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        URLClassLoader loader = new URLClassLoader(new URL[]{Agent.class.getProtectionDomain().getCodeSource().getLocation()}, ClassLoader.getSystemClassLoader().getParent());
        Class<?> main = loader.loadClass("cn.enaium.joe.Main");
        Method agent = ReflectUtil.getMethod(main, "agent", Instrumentation.class);
        agent.invoke(null, inst);
    }
}
