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

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

/**
 * @author Enaium
 * @since 1.3.0
 */
public class ColumnPane extends BorderPane {
    private final GridPane gridPane = new GridPane();
    private int row = 0;

    public ColumnPane() {
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        setCenter(scrollPane);
        ColumnConstraints left = new ColumnConstraints();
        ColumnConstraints right = new ColumnConstraints();
        right.setFillWidth(true);
        right.setHgrow(Priority.ALWAYS);
        right.setHgrow(Priority.ALWAYS);
        right.setHalignment(HPos.RIGHT);
        gridPane.getColumnConstraints().addAll(left, right);
    }

    public void add(Node left, Node right) {
        gridPane.add(left, 0, row);
        gridPane.add(right, 1, row);
        if (right instanceof Region)
            ((Region) right).setMaxWidth(Double.MAX_VALUE);
        row++;
    }
}
