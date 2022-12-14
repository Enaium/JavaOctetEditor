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

package cn.enaium.joe.ui.control

import cn.enaium.joe.ui.util.addStyle
import cn.enaium.joe.ui.util.runLater
import com.google.gson.Gson
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory
import org.fxmisc.richtext.model.StyleSpans
import org.fxmisc.richtext.model.StyleSpansBuilder
import java.io.IOException
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * @author Enaium
 */
class CodeEditor(type: Language.Type) : CodeArea() {
    init {
        paragraphGraphicFactory = LineNumberFactory.get(this)
        if (type != Language.Type.UNKNOWN) {
            richChanges().subscribe {
                runLater({ computeHighlighting(type, text) }, { result -> setStyleSpans(0, result) })
            }
            addStyle(this, "style/highlight.css")
        }
    }

    private fun computeHighlighting(type: Language.Type, text: String): StyleSpans<Collection<String>> {
        val name: String = type.lang
        val language = Gson().fromJson(CodeEditor::class.java.classLoader.getResource("language/$name.json")!!.readText(), Language::class.java)
        val stringBuilder = StringBuilder()
        for (i in language.rules.indices) {
            val rule = language.rules[i]
            if (i > 0) {
                stringBuilder.append("|")
            }
            stringBuilder.append("(?<%s>%s)".format(rule.name, rule.pattern))
        }
        val matcher: Matcher = Pattern.compile(stringBuilder.toString()).matcher(text)
        var lastKwEnd = 0
        val spansBuilder = StyleSpansBuilder<Collection<String>>()
        while (matcher.find()) {
            var styleClass: String? = null
            for (rule in language.rules) {
                if (matcher.group(rule.name) != null) {
                    styleClass = rule.name;
                }
            }
            if (styleClass != null) {
                spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd)
                spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start())
                lastKwEnd = matcher.end()
            }
        }
        spansBuilder.add(Collections.emptyList(), text.length - lastKwEnd)
        return spansBuilder.create()
    }

    class Language(val name: String, val rules: List<Rule>) {
        class Rule(val name: String, val pattern: String)

        enum class Type(val lang: String) {
            UNKNOWN("unknown"),
            JAVA("java"),
            BYTECODE("bytecode");
        }
    }
}