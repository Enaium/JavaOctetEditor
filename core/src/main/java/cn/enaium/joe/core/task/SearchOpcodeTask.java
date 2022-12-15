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
import cn.enaium.joe.core.util.OpcodeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Enaium
 */
public class SearchOpcodeTask extends SearchInstructionTask<List<SearchResultModel>> {

    private final String opcode;

    public SearchOpcodeTask(JarModel jar, String opcode) {
        super("SearchOpcode", jar);
        this.opcode = opcode;
    }

    @Override
    public List<SearchResultModel> get() {
        var resultNodes = new ArrayList<SearchResultModel>();
        searchInstruction((classNode, instruction) -> {
            if (String.valueOf(instruction.getOpcode()).equals(opcode) || opcode.equals(OpcodeUtil.OPCODE.get(instruction.getOpcode()))) {
                resultNodes.add(new SearchResultModel(classNode, instruction));
            }
        });
        return resultNodes;
    }
}
