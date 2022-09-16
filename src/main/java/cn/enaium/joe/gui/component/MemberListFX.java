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

import cn.enaium.joe.MainFX;
import cn.enaium.joe.event.events.FileTabSelectEvent;
import cn.enaium.joe.gui.panel.MemberInfo;
import cn.enaium.joe.gui.panel.file.tabbed.tab.classes.ClassNodeTabPane;
import cn.enaium.joe.gui.panel.file.tabbed.tab.classes.ClassTabPane;
import cn.enaium.joe.util.Pair;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.function.Consumer;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class MemberListFX extends ListView<MemberInfo> {
    public MemberListFX() {
        MainFX.getInstance().event.register(FileTabSelectEvent.class, (Consumer<FileTabSelectEvent>) fileTabSelectEvent -> {
            Node content = fileTabSelectEvent.getSelect().getContent();
            getItems().clear();
            if (content instanceof ClassTabPane) {
                ClassNode classNode = ((ClassTabPane) content).classNode;
                for (FieldNode field : classNode.fields) {
                    getItems().add(new MemberInfo(classNode, field));
                }

                for (MethodNode method : classNode.methods) {
                    getItems().add(new MemberInfo(classNode, method));
                }
            }
        });
    }
}
