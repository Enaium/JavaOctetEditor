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

package cn.enaium.joe.gui.panel.search;

import cn.enaium.joe.util.ColorUtil;
import cn.enaium.joe.util.HtmlUtil;
import org.objectweb.asm.tree.ClassNode;

import java.awt.*;

/**
 * @author Enaium
 */
public class ResultNode {
    private ClassNode classNode;

    private final String text;

    public ResultNode() {
        text = " ";
    }

    public ResultNode(ClassNode classNode, String result) {
        this.classNode = classNode;
        text = HtmlUtil.toHtml(HtmlUtil.setColor(classNode.name, Color.WHITE) + "#" + result);
    }

    public ClassNode getClassNode() {
        return classNode;
    }

    @Override
    public String toString() {
        return text;
    }
}
