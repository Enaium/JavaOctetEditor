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

import org.pmw.tinylog.Logger;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author Enaium
 * @since 1.3.0
 */
public class TinyLogPrintStream extends PrintStream {

    private final Type type;

    public TinyLogPrintStream(OutputStream out, Type type) {
        super(out);
        this.type = type;
    }

    @Override
    public void println(String x) {
        log(x);
    }

    @Override
    public void println(Object x) {
        log(String.valueOf(x));
    }

    private void log(String log) {
        if (type == Type.STDOUT) {
            Logger.info(log);
        } else if (type == Type.STDERR) {
            Logger.error(log);
        }
    }

    public enum Type {
        STDOUT, STDERR
    }
}
