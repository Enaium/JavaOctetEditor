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

package cn.enaium.joe.config.extend;

import cn.enaium.joe.annotation.NoUI;
import cn.enaium.joe.config.Config;
import cn.enaium.joe.config.value.EnableValue;
import cn.enaium.joe.config.value.IntegerValue;
import cn.enaium.joe.config.value.ModeValue;
import cn.enaium.joe.config.value.StringSetValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Enaium
 * @since 0.7.0
 */
public class ApplicationConfig extends Config {
    public ModeValue decompilerMode = new ModeValue("Decompiler", "CFR", "Java Decompiler", Arrays.asList("CFR", "Procyon", "FernFlower"));
    public ModeValue language = new ModeValue("Language", "System", "UI language", Arrays.asList("System", "zh_CN", "en_US"));
    public ModeValue packagePresentation = new ModeValue("Package Presentation", "Hierarchical", "Package Mode", Arrays.asList("Flat", "Hierarchical"));
    public EnableValue compactMiddlePackage = new EnableValue("Compact Middle Package", true, "Only Hierarchical Mode");
    @NoUI
    public final StringSetValue loadRecent = new StringSetValue("Load Recent", new HashSet<>(), "");
    public IntegerValue scale = new IntegerValue("Scale", 0, "UI scale,it doesn't scale if value 0");

    public ApplicationConfig() {
        super("Application");
    }
}
