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
import org.objectweb.asm.tree.LdcInsnNode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enaium
 * @since 0.10.0
 */
public class SearchLdcTask extends SearchInstructionTask<List<ResultNode>> {

    private final Object ldc;

    public SearchLdcTask(Jar jar, Object ldc) {
        super("SearchLdc", jar);
        this.ldc = ldc;
    }

    @Override
    public List<ResultNode> get() {
        List<ResultNode> resultNodes = new ArrayList<>();
        searchInstruction((classNode, instruction) -> {
            if (instruction instanceof LdcInsnNode) {
                String ldc = ((LdcInsnNode) instruction).cst.toString();
                if (ldc.contains(this.ldc.toString())) {
                    resultNodes.add(new ResultNode(classNode, HtmlUtil.setColor(ldc, ColorUtil.string)));
                }
            }
        });
        return resultNodes;
    }
}
