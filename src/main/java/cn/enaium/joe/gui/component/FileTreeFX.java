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
import cn.enaium.joe.gui.component.tree.*;
import cn.enaium.joe.gui.panel.file.tabbed.tab.classes.ClassTabPane;
import cn.enaium.joe.gui.panel.file.tabbed.tab.resources.FileTablePane;
import cn.enaium.joe.jar.Jar;
import cn.enaium.joe.util.ImageUtil;
import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.objectweb.asm.tree.ClassNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Enaium
 * @since 0.1.0
 */
public class FileTreeFX extends TreeView<Object> {
    public static final ObjectTreeItem classesRoot = new ObjectTreeItem("classes", ImageUtil.loadSVG("icons/classesRoot.svg"));
    public static final ObjectTreeItem resourceRoot = new ObjectTreeItem("resources", ImageUtil.loadSVG("icons/resourceRoot.svg"));

    public FileTreeFX() {
        setShowRoot(false);
        setRoot(new TreeItem<>());
        getRoot().getChildren().add(classesRoot);
        getRoot().getChildren().add(resourceRoot);
        getRoot().setExpanded(true);
        setOnMouseClicked(mouseEvent -> {
            Platform.runLater(() -> {
                if (mouseEvent.getClickCount() == 2) {
                    TreeItem<Object> item = getSelectionModel().getSelectedItem();
                    if (item != null) {
                        Tab tab = null;
                        if (item instanceof ClassTreeItem) {
                            ClassTreeItem classTreeItem = (ClassTreeItem) item;
                            tab = new Tab(classTreeItem.classNode.name.substring(classTreeItem.classNode.name.lastIndexOf("/") + 1), new ClassTabPane(classTreeItem.classNode));
                        } else if (item instanceof FileTreeItem) {
                            FileTreeItem fileTreeItem = (FileTreeItem) item;
                            tab = new Tab(fileTreeItem.toString().substring(fileTreeItem.toString().lastIndexOf("/") + 1), new FileTablePane(fileTreeItem));
                        }
                        if (tab != null) {
                            MainFX.getInstance().fileTab.getTabs().add(tab);
                            MainFX.getInstance().fileTab.getSelectionModel().select(tab);
                        }
                    }
                }
            });
        });
    }

    public void refresh(Jar jar) {
        classesRoot.getChildren().clear();
        resourceRoot.getChildren().clear();

        Map<String, ObjectTreeItem> hasMap = new HashMap<>();

        for (ClassNode classNode : jar.classes.values()) {
            String[] split = classNode.name.split("/");
            ObjectTreeItem prev = null;
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            for (String s : split) {
                stringBuilder.append(s);
                PackageTreeItem packageItem = new PackageTreeItem(s);

                if (split.length == i + 1) {
                    packageItem = new ClassTreeItem(classNode);
                }

                if (prev == null) {
                    if (!hasMap.containsKey(stringBuilder.toString())) {
                        classesRoot.getChildren().add(packageItem);
                        hasMap.put(stringBuilder.toString(), packageItem);
                        prev = packageItem;
                    } else {
                        prev = hasMap.get(stringBuilder.toString());
                    }
                } else {
                    if (!hasMap.containsKey(stringBuilder.toString())) {
                        prev.getChildren().add(packageItem);
                        hasMap.put(stringBuilder.toString(), packageItem);
                        prev = packageItem;
                    } else {
                        prev = hasMap.get(stringBuilder.toString());
                    }
                }
                i++;
            }
        }
//        sort(classesRoot);
        hasMap.clear();

        for (Map.Entry<String, byte[]> stringEntry : jar.resources.entrySet()) {
            String[] split = stringEntry.getKey().split("/");
            ObjectTreeItem prev = null;
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            for (String s : split) {
                stringBuilder.append(s);
                FolderTreeItem folderTreeItem = new FolderTreeItem(s);

                if (split.length == i + 1) {
                    folderTreeItem = new FileTreeItem(s, stringEntry.getValue());
                }

                if (prev == null) {
                    if (!hasMap.containsKey(stringBuilder.toString())) {
                        resourceRoot.getChildren().add(folderTreeItem);
                        hasMap.put(stringBuilder.toString(), folderTreeItem);
                        prev = folderTreeItem;
                    } else {
                        prev = hasMap.get(stringBuilder.toString());
                    }
                } else {
                    if (!hasMap.containsKey(stringBuilder.toString())) {
                        prev.getChildren().add(folderTreeItem);
                        hasMap.put(stringBuilder.toString(), folderTreeItem);
                        prev = folderTreeItem;
                    } else {
                        prev = hasMap.get(stringBuilder.toString());
                    }
                }
                i++;
            }
        }
//        sort(resourceRoot);
    }

    public void sort(TreeItem<Object> objectItem) {
        if (!objectItem.isLeaf()) {

            objectItem.getChildren().sort((o1, o2) -> {
                boolean class1 = o1 instanceof ClassTreeItem;
                boolean class2 = o2 instanceof ClassTreeItem;

                boolean file1 = o1 instanceof FileTreeItem;
                boolean file2 = o2 instanceof FileTreeItem;

                if (class1 && !class2) {
                    return 1;
                }
                if (!class1 && class2) {
                    return -1;
                }

                if (file1 && !file2) {
                    return 1;
                }
                if (!file1 && file2) {
                    return -1;
                }
                return o1.toString().compareTo(o2.toString());
            });

            for (TreeItem<Object> child : objectItem.getChildren()) {
                sort(child);
            }
        }
    }
}
