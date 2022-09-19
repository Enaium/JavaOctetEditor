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

import cn.enaium.joe.gui.panel.InheritPanel;
import cn.enaium.joe.gui.panel.MemberListPanel;
import cn.enaium.joe.util.LangUtil;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;

/**
 * @author Enaium
 * @since 1.3.0
 */
public class RightTabBar extends TabPane {
    public RightTabBar() {
        super(TabPane.RIGHT);
        addTab(LangUtil.i18n("sideTab.member"), new FlatSVGIcon("icons/structure.svg"), new MemberListPanel());
        addTab(LangUtil.i18n("sideTab.inherit"), new FlatSVGIcon("icons/inherit.svg"), new InheritPanel());
        cancelSelect();
        setVerticalLabel();
    }
}
