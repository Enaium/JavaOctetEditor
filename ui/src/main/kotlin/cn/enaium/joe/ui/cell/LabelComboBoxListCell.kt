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

package cn.enaium.joe.ui.cell

import cn.enaium.joe.core.util.OpcodeUtil
import javafx.scene.control.cell.ComboBoxListCell
import org.objectweb.asm.tree.LabelNode

/**
 * @author Enaium
 * @since 2.0.0
 */
class LabelComboBoxListCell : ComboBoxListCell<LabelNode>() {
    override fun updateItem(item: LabelNode?, empty: Boolean) {
        super.updateItem(item, empty)
        if (empty) {
            graphic = null
            text = null
            contextMenu = null
        } else {
            text = "L ${OpcodeUtil.getLabelIndex(item)}"
        }
    }
}