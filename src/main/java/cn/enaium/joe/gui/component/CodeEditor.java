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

import cn.enaium.joe.util.ASyncUtil;
import cn.enaium.joe.util.IOUtil;
import cn.enaium.joe.util.MessageUtil;
import cn.enaium.joe.util.StyleUtil;
import com.google.gson.Gson;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Enaium
 * @since 1.3.0
 */
public class CodeEditor extends CodeArea {

    public CodeEditor(Language.Type type) {
        setParagraphGraphicFactory(LineNumberFactory.get(this));
        if (type != Language.Type.UNKNOWN) {
            richChanges().subscribe(collectionStringCollectionRichTextChange -> ASyncUtil.execute(() -> computeHighlighting(type, getText()), result -> setStyleSpans(0, result)));
            StyleUtil.add(this, "style/highlight.css");
        }
    }


    private StyleSpans<Collection<String>> computeHighlighting(Language.Type type, String text) {
        String name = type.getName();
        String string = null;
        try {
            string = IOUtil.getString(CodeEditor.class.getClassLoader().getResourceAsStream("language/" + name + ".json"));
        } catch (IOException e) {
            MessageUtil.error(e);
        }

        Language language = new Gson().fromJson(string, Language.class);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < language.rules.size(); i++) {
            Language.Rule rule = language.rules.get(i);
            if (i > 0) {
                stringBuilder.append("|");
            }

            stringBuilder.append(String.format("(?<%s>%s)", rule.name, rule.pattern));
        }

        Matcher matcher = Pattern.compile(stringBuilder.toString()).matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass = null;
            for (Language.Rule rule : language.rules) {
                if (matcher.group(rule.name) != null) {
                    styleClass = rule.name;
                }
            }
            if (styleClass != null) {
                spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
                spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
                lastKwEnd = matcher.end();
            }
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    public static class Language {

        public String name;
        public List<Rule> rules;


        public static class Rule {
            public String name;
            public String pattern;
        }

        public enum Type {
            UNKNOWN("unknown"),
            JAVA("java"),
            BYTECODE("bytecode");

            private final String name;

            Type(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }
        }
    }
}
