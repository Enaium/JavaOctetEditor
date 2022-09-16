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

package cn.enaium.joe.gui.component.tree;

import cn.enaium.joe.util.ImageUtil;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

/**
 * @author Enaium
 * @since 1.3.0
 */
public class ClassTreeItem extends PackageTreeItem {
    public final ClassNode classNode;
    public ClassTreeItem(ClassNode classNode) {
        super(classNode.name.substring(classNode.name.lastIndexOf("/") + 1));
        this.classNode = classNode;
        if (classNode.access == (Opcodes.ACC_PUBLIC | Opcodes.ACC_ANNOTATION | Opcodes.ACC_ABSTRACT | Opcodes.ACC_INTERFACE)) {
            setGraphic(ImageUtil.loadSVG("icons/annotation.svg"));
        } else if (classNode.access == (Opcodes.ACC_PUBLIC | Opcodes.ACC_ABSTRACT | Opcodes.ACC_INTERFACE)) {
            setGraphic(ImageUtil.loadSVG("icons/interface.svg"));
        } else if (classNode.access == (Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER | Opcodes.ACC_ABSTRACT)) {
            setGraphic(ImageUtil.loadSVG("icons/abstractClass.svg"));
        } else if (classNode.access == (Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL | Opcodes.ACC_SUPER | Opcodes.ACC_ENUM)) {
            setGraphic(ImageUtil.loadSVG("icons/enum.svg"));
        } else if (classNode.access == (Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL | Opcodes.ACC_SUPER)) {
            setGraphic(ImageUtil.loadSVG("icons/finalClass.svg"));
        } else {
            setGraphic(ImageUtil.loadSVG("icons/class.svg"));
        }
    }
}
