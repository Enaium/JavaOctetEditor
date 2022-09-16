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

package cn.enaium.joe.util;

import cn.enaium.joe.JavaOctetEditor;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * @author Enaium
 */
public class JFileChooserUtil {

    private JFileChooserUtil() {
        throw new IllegalAccessError("Utility");
    }

    public static File show(Type type) {
        return show(type, new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".zip") || file.getName().endsWith(".jar") || file.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Zip File(*.zip,*.jar)";
            }
        });
    }

    public static File show(Type type, FileFilter fileFilter) {
        return show(type, fileFilter, JFileChooser.FILES_ONLY);
    }

    public static File show(Type type, FileFilter fileFilter, int mode) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
        jFileChooser.setFileSelectionMode(mode);
        jFileChooser.removeChoosableFileFilter(jFileChooser.getAcceptAllFileFilter());
        jFileChooser.addChoosableFileFilter(fileFilter);

        if (type.equals(Type.OPEN)) {
            jFileChooser.showOpenDialog(null);
        } else if (type.equals(Type.SAVE)) {
            jFileChooser.showSaveDialog(null);
        }

        return jFileChooser.getSelectedFile();
    }

    public enum Type {
        OPEN,
        SAVE
    }
}
