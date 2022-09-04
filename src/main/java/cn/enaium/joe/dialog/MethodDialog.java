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

import cn.enaium.joe.gui.panel.method.MethodTabPanel;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.awt.*;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class MethodDialog extends Dialog {
    public MethodDialog(ClassNode classNode, MethodNode methodNode) {
        super(classNode.name + "#" + methodNode.name);
        setLayout(new BorderLayout());
        add(new MethodTabPanel(methodNode), BorderLayout.CENTER);
    }
}
