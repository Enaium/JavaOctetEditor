/**
 * Copyright 2022 Enaium
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.joe.util;

import cn.enaium.joe.dialog.OptionDialog;
import cn.enaium.joe.gui.panel.confirm.ConfirmPanel;
import org.pmw.tinylog.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Enaium
 * @since 0.7.0
 */
public class MessageUtil {

    public static void error(Throwable e) {
        new OptionDialog(LangUtil.i18n("error"), new JScrollPane(new JTextArea() {{
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            setText(out.toString());
            setEditable(false);
        }}), JOptionPane.ERROR_MESSAGE).setVisible(true);
    }

    public static void confirm(Object message, String title, Runnable yes, Runnable no) {
        OptionDialog optionDialog = new OptionDialog(title, message, JOptionPane.INFORMATION_MESSAGE, yes, no);
        optionDialog.setVisible(true);
    }

    public static void confirm(ConfirmPanel confirmPanel, String title) {
        confirm(confirmPanel, title, confirmPanel.getConfirm(), confirmPanel.getCancel());
    }

    public static void confirm(ConfirmPanel confirmPanel, String title, Runnable yes) {
        confirm(confirmPanel, title, () -> {
            confirmPanel.getConfirm().run();
            yes.run();
        }, null);
    }

    public static void confirm(Object message, String title, Runnable yes) {
        confirm(message, title, yes, null);
    }


    public static void info(String message) {
        OptionDialog info = new OptionDialog(LangUtil.i18n("info"), message, JOptionPane.INFORMATION_MESSAGE);
        info.setVisible(true);
        Logger.info(message);
    }

    public static void warning(String message) {
        OptionDialog warning = new OptionDialog(LangUtil.i18n("warning"), message, JOptionPane.WARNING_MESSAGE);
        warning.setVisible(true);
        Logger.warn(message);
    }
}
