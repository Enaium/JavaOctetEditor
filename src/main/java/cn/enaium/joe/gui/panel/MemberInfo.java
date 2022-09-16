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

package cn.enaium.joe.gui.panel;

import cn.enaium.joe.util.ImageUtil;
import cn.enaium.joe.util.OpcodeUtil;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import org.girod.javafx.svgimage.SVGImage;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import javax.swing.*;

/**
 * @author Enaium
 * @since 1.3.0
 */
public class MemberInfo extends FlowPane {

    private final ClassNode classNode;
    private final Object member;

    public MemberInfo(ClassNode classNode, Object member) {
        setHgap(5);
        this.classNode = classNode;
        this.member = member;
        String name = null;
        SVGImage icon = null;
        int access = 0;
        if (member instanceof FieldNode) {
            name = ((FieldNode) member).name;
            icon = ImageUtil.loadSVG("icons/field.svg");
            access = ((FieldNode) member).access;
        } else if (member instanceof MethodNode) {
            name = ((MethodNode) member).name;
            icon = ImageUtil.loadSVG("icons/method.svg");
            access = ((MethodNode) member).access;
        }
        SVGImage accessIcon;
        if (OpcodeUtil.isPublic(access)) {
            accessIcon = ImageUtil.loadSVG("icons/public.svg");
        } else if (OpcodeUtil.isPrivate(access)) {
            accessIcon = ImageUtil.loadSVG("icons/private.svg");
        } else if (OpcodeUtil.isProtected(access)) {
            accessIcon = ImageUtil.loadSVG("icons/protected.svg");
        } else if (OpcodeUtil.isStatic(access) && "<clinit>".equals(name)) {
            accessIcon = ImageUtil.loadSVG("icons/static.svg");
        } else {
            accessIcon = ImageUtil.loadSVG("icons/cyan_dot.svg");
        }

        assert icon != null;
        getChildren().add(new ImageView(icon.toImage()));
        getChildren().add(new ImageView(accessIcon.toImage()));
        getChildren().add(new Label(name));
    }
}
