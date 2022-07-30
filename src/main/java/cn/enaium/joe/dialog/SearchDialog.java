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

package cn.enaium.joe.dialog;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.gui.panel.search.ResultNode;
import cn.enaium.joe.gui.panel.search.ResultPanel;
import cn.enaium.joe.jar.Jar;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Enaium
 */
public class SearchDialog extends Dialog {
    public ResultPanel resultPanel = new ResultPanel();

    public SearchDialog() {
        super("Search");
        setLayout(new BorderLayout());
        setSize(700, 400);
        add(resultPanel, BorderLayout.CENTER);
    }

    public void searchInstruction(BiConsumer<ClassNode, AbstractInsnNode> consumer) {
        Jar jar = JavaOctetEditor.getInstance().jar;
        float loaded = 0;
        float total = 0;
        for (Map.Entry<String, ClassNode> stringClassNodeEntry : jar.classes.entrySet()) {
            for (MethodNode method : stringClassNodeEntry.getValue().methods) {
                total += method.instructions.size();
            }
        }

        for (Map.Entry<String, ClassNode> stringClassNodeEntry : jar.classes.entrySet()) {
            for (MethodNode method : stringClassNodeEntry.getValue().methods) {
                for (AbstractInsnNode instruction : method.instructions) {
                    consumer.accept(stringClassNodeEntry.getValue(), instruction);
                    JavaOctetEditor.getInstance().bottomPanel.setProcess((int) ((loaded++ / total) * 100f));
                }
            }
        }
        JavaOctetEditor.getInstance().bottomPanel.setProcess(0);
    }
}
