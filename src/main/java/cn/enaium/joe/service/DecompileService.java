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

package cn.enaium.joe.service;

import cn.enaium.joe.JavaOctetEditor;
import cn.enaium.joe.MainFX;
import cn.enaium.joe.config.extend.ApplicationConfig;
import cn.enaium.joe.config.value.ModeValue;
import cn.enaium.joe.service.decompiler.*;

/**
 * @author Enaium
 * @since 0.7.0
 */
public class DecompileService {
    public static IDecompiler getService() {
        ModeValue decompilerMode = MainFX.getInstance().config.getByClass(ApplicationConfig.class).decompilerMode;
        switch (decompilerMode.getValue()) {
            case "CFR": return new CFRDecompiler();
            case "Procyon": return new ProcyonDecompiler();
            case "FernFlower": return new FernFlowerDecompiler();
        }
        throw new NullPointerException("Not found decompiler");
    }
}
