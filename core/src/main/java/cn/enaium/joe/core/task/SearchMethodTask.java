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

package cn.enaium.joe.core.task;

import cn.enaium.joe.core.model.JarModel;
import cn.enaium.joe.core.model.SearchResultModel;
import org.objectweb.asm.tree.MethodInsnNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Enaium
 * @since 2.0.0
 */
public class SearchMethodTask extends SearchInstructionTask<List<SearchResultModel>> {

    private final String owner;
    private final String name;
    private final String description;

    private final boolean itf;


    public SearchMethodTask(JarModel jar, String owner, String name, String description, boolean itf) {
        super("SearchMethod", jar);
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.itf = itf;
    }

    @Override
    public List<SearchResultModel> get() {
        var resultNodes = new ArrayList<SearchResultModel>();
        searchInstruction((classNode, instruction) -> {
            if (instruction instanceof MethodInsnNode methodInsnNode) {
                if ((methodInsnNode.owner.contains(owner) || owner.isBlank()) &&
                        (methodInsnNode.name.contains(name) || name.isBlank()) &&
                        (methodInsnNode.desc.contains(description) || description.isBlank()) &&
                        (methodInsnNode.itf || !itf)
                ) {
                    resultNodes.add(new SearchResultModel(classNode, instruction));
                }
            }
        });
        return resultNodes;
    }
}
