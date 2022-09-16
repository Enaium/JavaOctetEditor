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

package cn.enaium.joe.stage;

import cn.enaium.joe.MainFX;
import cn.enaium.joe.annotation.NoUI;
import cn.enaium.joe.config.Config;
import cn.enaium.joe.config.value.*;
import cn.enaium.joe.gui.panel.ColumnPane;
import cn.enaium.joe.util.LangUtil;
import cn.enaium.joe.util.MessageUtil;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.lang.reflect.Field;

/**
 * @author Enaium
 * @since 1.3.0
 */
public class ConfigDialog extends Dialog {
    public ConfigDialog(Config config) {
        super(LangUtil.i18n("menu.config") + ":" + config.getName(), new ColumnPane() {{
            try {
                int row = 0;
                for (Field declaredField : config.getClass().getDeclaredFields()) {
                    declaredField.setAccessible(true);

                    if (declaredField.isAnnotationPresent(NoUI.class)) {
                        continue;
                    }

                    Object o = declaredField.get(config);

                    if (o instanceof Value) {
                        Value<?> value = (Value<?>) o;
                        Label label = new Label(value.getName());
                        Tooltip.install(label, new Tooltip(value.getDescription()));

                        if (o instanceof StringValue) {
                            StringValue stringValue = (StringValue) o;
                            add(label, new TextField(stringValue.getValue()) {{
                                textProperty().addListener((observableValue, s, t1) -> stringValue.setValue(getText()));
                            }});
                        } else if (o instanceof IntegerValue) {
                            IntegerValue integerValue = (IntegerValue) o;
                            add(label, new Spinner<Integer>(0, 256, integerValue.getValue()) {{
                                valueProperty().addListener((observableValue, integer, t1) -> integerValue.setValue(getValue()));
                            }});
                        } else if (o instanceof EnableValue) {
                            EnableValue enableValue = (EnableValue) o;
                            add(label, new CheckBox() {{
                                setAlignment(Pos.CENTER_RIGHT);
                                setSelected(enableValue.getValue());
                                setOnAction(actionEvent -> enableValue.setValue(isSelected()));
                            }});
                        } else if (o instanceof ModeValue) {
                            add(label, new ComboBox<String>() {{
                                ModeValue modeValue = (ModeValue) o;
                                for (String s : modeValue.getMode()) {
                                    getItems().add(s);
                                    getSelectionModel().select(modeValue.getValue());
                                }
                                setOnAction(actionEvent -> {
                                    modeValue.setValue(getSelectionModel().getSelectedItem());
                                });
                            }});
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                MessageUtil.error(e);
            }
        }});
        setOnCloseRequest(windowEvent -> {
            MainFX.getInstance().config.save();
        });
    }
}
