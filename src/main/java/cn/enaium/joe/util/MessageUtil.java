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

import cn.enaium.joe.gui.panel.confirm.ConfirmPanel;
import org.tinylog.Logger;

import javax.swing.*;

/**
 * @author Enaium
 * @since 0.7.0
 */
public class MessageUtil {

    public static void error(Throwable e) {
        Logger.error(e);
        JOptionPane.showMessageDialog(null, e.toString(), LangUtil.i18n("error"), JOptionPane.ERROR_MESSAGE);
    }

    public static void confirm(Object message, String title, Runnable yes, Runnable no) {
        int i = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION);
        if (i == JOptionPane.YES_OPTION) {
            if (yes != null) {
                yes.run();
            }
        } else {
            if (no != null) {
                no.run();
            }
        }
    }

    public static void confirm(ConfirmPanel confirmPanel, String title) {
        confirm(confirmPanel, title, () -> {
            confirmPanel.getConfirm().run();
        }, () -> {
            confirmPanel.getCancel().run();
        });
    }

    public static void confirm(Object message, String title, Runnable yes) {
        confirm(message, title, yes, null);
    }


    public static void info(String message) {
        JOptionPane.showMessageDialog(null, message, LangUtil.i18n("info"), JOptionPane.INFORMATION_MESSAGE);
        Logger.info(message);
    }

    public static void warning(String message) {
        JOptionPane.showMessageDialog(null, message, LangUtil.i18n("warning"), JOptionPane.WARNING_MESSAGE);
        Logger.warn(message);
    }
}
