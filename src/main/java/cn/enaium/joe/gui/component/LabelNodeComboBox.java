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

package cn.enaium.joe.gui.component;

import cn.enaium.joe.util.OpcodeUtil;
import cn.enaium.joe.wrapper.LabelNodeWrapper;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;

import javax.swing.*;

/**
 * @author Enaium
 * @since 0.8.0
 */
public class LabelNodeComboBox extends JComboBox<LabelNodeWrapper> {
    public LabelNodeComboBox(AbstractInsnNode instruction, LabelNode select) {
        super(new DefaultComboBoxModel<>());
        LabelNodeWrapper selected = null;
        for (AbstractInsnNode abstractInsnNode : OpcodeUtil.getInstructionList(select)) {
            if (abstractInsnNode instanceof LabelNode) {
                LabelNodeWrapper anObject = new LabelNodeWrapper(((LabelNode) abstractInsnNode));
                if (abstractInsnNode.equals(select)) {
                    selected = anObject;
                }
                ((DefaultComboBoxModel<LabelNodeWrapper>) getModel()).addElement(anObject);
            }
        }
        getModel().setSelectedItem(selected);
    }
}
