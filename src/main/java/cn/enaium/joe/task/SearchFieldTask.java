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

package cn.enaium.joe.task;

import cn.enaium.joe.gui.panel.search.ResultNode;
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.ColorUtil;
import cn.enaium.joe.util.HtmlUtil;
import cn.enaium.joe.util.StringUtil;
import org.objectweb.asm.tree.FieldInsnNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enaium
 * @since 0.10.0
 */
public class SearchFieldTask extends SearchInstructionTask<List<ResultNode>> {

    private final String owner;
    private final String name;
    private final String description;


    public SearchFieldTask(Jar jar, String owner, String name, String description) {
        super("SearchField", jar);
        this.owner = owner;
        this.name = name;
        this.description = description;
    }

    @Override
    public List<ResultNode> get() {
        List<ResultNode> resultNodes = new ArrayList<>();
        searchInstruction((classNode, instruction) -> {
            if (instruction instanceof FieldInsnNode) {
                FieldInsnNode fieldInsnNode = (FieldInsnNode) instruction;
                if ((fieldInsnNode.owner.contains(owner) || StringUtil.isBlank(owner)) &&
                        (fieldInsnNode.name.contains(name) || StringUtil.isBlank(name)) &&
                        (fieldInsnNode.desc.contains(description) || StringUtil.isBlank(description))
                ) {
                    resultNodes.add(new ResultNode(classNode, HtmlUtil.setColor(fieldInsnNode.name, ColorUtil.name) + HtmlUtil.setColor(":", ColorUtil.opcode) + HtmlUtil.setColor(fieldInsnNode.desc, ColorUtil.desc)));
                }
            }
        });
        return resultNodes;
    }
}
