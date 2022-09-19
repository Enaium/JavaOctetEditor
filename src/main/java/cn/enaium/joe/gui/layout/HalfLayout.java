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

package cn.enaium.joe.gui.layout;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class HalfLayout extends AbstractLayout {
    private final Map<String, Component> componentMap = new HashMap<>();

    private int minWidth = 0, minHeight = 0;
    private int preferredWidth = 0, preferredHeight = 0;

    public static final String LEFT = "LEFT";
    public static final String RIGHT = "RIGHT";
    public static final String TOP = "TOP";
    public static final String BOTTOM = "BOTTOM";
    public static final int LEFT_AND_RIGHT = 0;
    public static final int TOP_AND_BOTTOM = 1;

    private final int side;

    public HalfLayout() {
        this(LEFT_AND_RIGHT);
    }

    public HalfLayout(int side) {
        this.side = side;
    }

    private void setSizes(Container parent) {
        int nComps = parent.getComponentCount();
        Dimension d;

        preferredWidth = 0;
        preferredHeight = 0;
        minWidth = 0;
        minHeight = 0;

        for (int i = 0; i < nComps; i++) {
            Component c = parent.getComponent(i);
            d = c.getPreferredSize();

            if (i > 0) {
                preferredWidth += d.width / 2;
                preferredHeight += 5;
            } else {
                preferredWidth = d.width;
                if (side == LEFT_AND_RIGHT) {
                    preferredHeight += d.height;
                }
            }

            if (side == TOP_AND_BOTTOM) {
                preferredHeight += d.height;
            }

            minWidth = Math.max(c.getMinimumSize().width, minWidth);
            minHeight = preferredHeight;
        }
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);

        setSizes(parent);

        Insets insets = parent.getInsets();
        dim.width = preferredWidth + insets.left + insets.right;
        dim.height = preferredHeight + insets.top + insets.bottom;
        return dim;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);

        Insets insets = parent.getInsets();
        dim.width = minWidth + insets.left + insets.right;
        dim.height = minHeight + insets.top + insets.bottom;

        return dim;
    }

    @Override
    public void layoutContainer(Container parent) {
        Component left = componentMap.get(LEFT);
        Component right = componentMap.get(RIGHT);

        if (left == null) {
            left = componentMap.get(TOP);
        }

        if (right == null) {
            right = componentMap.get(BOTTOM);
        }


        if (side == LEFT_AND_RIGHT) {
            left.setBounds(0, 0, right.isVisible() ? parent.getWidth() / 2 : parent.getWidth(), parent.getHeight());
            right.setBounds(left.isVisible() ? parent.getWidth() / 2 : 0, 0, left.isVisible() ? parent.getWidth() / 2 : parent.getWidth(), parent.getHeight());
        } else if (side == TOP_AND_BOTTOM) {
            left.setBounds(0, 0, parent.getWidth(), right.isVisible() ? parent.getHeight() / 2 : parent.getHeight());
            right.setBounds(0, left.isVisible() ? left.getHeight() : 0, parent.getWidth(), left.isVisible() ? parent.getHeight() / 2 : parent.getHeight());
        }
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        componentMap.put(constraints.toString(), comp);
    }

    public boolean isAllHide() {
        boolean hide = true;
        for (Map.Entry<String, Component> stringComponentEntry : componentMap.entrySet()) {
            if (stringComponentEntry.getValue().isVisible()) {
                hide = false;
                break;
            }
        }
        return hide;
    }
}
