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

import org.fife.ui.rsyntaxtextarea.*;

import javax.swing.*;
import javax.swing.text.Segment;

/**
 * @author Enaium
 */
public class BytecodeTokenMaker extends AbstractTokenMaker {
    @Override
    public TokenMap getWordsToHighlight() {
        TokenMap tokenMap = new TokenMap();
        tokenMap.put("NOP", Token.RESERVED_WORD);
        tokenMap.put("ACONST_NULL", Token.RESERVED_WORD);
        tokenMap.put("ICONST_M1", Token.RESERVED_WORD);
        tokenMap.put("ICONST_0", Token.RESERVED_WORD);
        tokenMap.put("ICONST_1", Token.RESERVED_WORD);
        tokenMap.put("ICONST_2", Token.RESERVED_WORD);
        tokenMap.put("ICONST_3", Token.RESERVED_WORD);
        tokenMap.put("ICONST_4", Token.RESERVED_WORD);
        tokenMap.put("ICONST_5", Token.RESERVED_WORD);
        tokenMap.put("LCONST_0", Token.RESERVED_WORD);
        tokenMap.put("LCONST_1", Token.RESERVED_WORD);
        tokenMap.put("FCONST_0", Token.RESERVED_WORD);
        tokenMap.put("FCONST_1", Token.RESERVED_WORD);
        tokenMap.put("FCONST_2", Token.RESERVED_WORD);
        tokenMap.put("DCONST_0", Token.RESERVED_WORD);
        tokenMap.put("DCONST_1", Token.RESERVED_WORD);
        tokenMap.put("BIPUSH", Token.RESERVED_WORD);
        tokenMap.put("SIPUSH", Token.RESERVED_WORD);
        tokenMap.put("LDC", Token.RESERVED_WORD);
        tokenMap.put("ILOAD", Token.RESERVED_WORD);
        tokenMap.put("LLOAD", Token.RESERVED_WORD);
        tokenMap.put("FLOAD", Token.RESERVED_WORD);
        tokenMap.put("DLOAD", Token.RESERVED_WORD);
        tokenMap.put("ALOAD", Token.RESERVED_WORD);
        tokenMap.put("IALOAD", Token.RESERVED_WORD);
        tokenMap.put("LALOAD", Token.RESERVED_WORD);
        tokenMap.put("FALOAD", Token.RESERVED_WORD);
        tokenMap.put("DALOAD", Token.RESERVED_WORD);
        tokenMap.put("AALOAD", Token.RESERVED_WORD);
        tokenMap.put("BALOAD", Token.RESERVED_WORD);
        tokenMap.put("CALOAD", Token.RESERVED_WORD);
        tokenMap.put("SALOAD", Token.RESERVED_WORD);
        tokenMap.put("ISTORE", Token.RESERVED_WORD);
        tokenMap.put("LSTORE", Token.RESERVED_WORD);
        tokenMap.put("FSTORE", Token.RESERVED_WORD);
        tokenMap.put("DSTORE", Token.RESERVED_WORD);
        tokenMap.put("ASTORE", Token.RESERVED_WORD);
        tokenMap.put("IASTORE", Token.RESERVED_WORD);
        tokenMap.put("LASTORE", Token.RESERVED_WORD);
        tokenMap.put("FASTORE", Token.RESERVED_WORD);
        tokenMap.put("DASTORE", Token.RESERVED_WORD);
        tokenMap.put("AASTORE", Token.RESERVED_WORD);
        tokenMap.put("BASTORE", Token.RESERVED_WORD);
        tokenMap.put("CASTORE", Token.RESERVED_WORD);
        tokenMap.put("SASTORE", Token.RESERVED_WORD);
        tokenMap.put("POP", Token.RESERVED_WORD);
        tokenMap.put("POP2", Token.RESERVED_WORD);
        tokenMap.put("DUP", Token.RESERVED_WORD);
        tokenMap.put("DUP_X1", Token.RESERVED_WORD);
        tokenMap.put("DUP_X2", Token.RESERVED_WORD);
        tokenMap.put("DUP2", Token.RESERVED_WORD);
        tokenMap.put("DUP2_X1", Token.RESERVED_WORD);
        tokenMap.put("DUP2_X2", Token.RESERVED_WORD);
        tokenMap.put("SWAP", Token.RESERVED_WORD);
        tokenMap.put("IADD", Token.RESERVED_WORD);
        tokenMap.put("LADD", Token.RESERVED_WORD);
        tokenMap.put("FADD", Token.RESERVED_WORD);
        tokenMap.put("DADD", Token.RESERVED_WORD);
        tokenMap.put("ISUB", Token.RESERVED_WORD);
        tokenMap.put("LSUB", Token.RESERVED_WORD);
        tokenMap.put("FSUB", Token.RESERVED_WORD);
        tokenMap.put("DSUB", Token.RESERVED_WORD);
        tokenMap.put("IMUL", Token.RESERVED_WORD);
        tokenMap.put("LMUL", Token.RESERVED_WORD);
        tokenMap.put("FMUL", Token.RESERVED_WORD);
        tokenMap.put("DMUL", Token.RESERVED_WORD);
        tokenMap.put("IDIV", Token.RESERVED_WORD);
        tokenMap.put("LDIV", Token.RESERVED_WORD);
        tokenMap.put("FDIV", Token.RESERVED_WORD);
        tokenMap.put("DDIV", Token.RESERVED_WORD);
        tokenMap.put("IREM", Token.RESERVED_WORD);
        tokenMap.put("LREM", Token.RESERVED_WORD);
        tokenMap.put("FREM", Token.RESERVED_WORD);
        tokenMap.put("DREM", Token.RESERVED_WORD);
        tokenMap.put("INEG", Token.RESERVED_WORD);
        tokenMap.put("LNEG", Token.RESERVED_WORD);
        tokenMap.put("FNEG", Token.RESERVED_WORD);
        tokenMap.put("DNEG", Token.RESERVED_WORD);
        tokenMap.put("ISHL", Token.RESERVED_WORD);
        tokenMap.put("LSHL", Token.RESERVED_WORD);
        tokenMap.put("ISHR", Token.RESERVED_WORD);
        tokenMap.put("LSHR", Token.RESERVED_WORD);
        tokenMap.put("IUSHR", Token.RESERVED_WORD);
        tokenMap.put("LUSHR", Token.RESERVED_WORD);
        tokenMap.put("IAND", Token.RESERVED_WORD);
        tokenMap.put("LAND", Token.RESERVED_WORD);
        tokenMap.put("IOR", Token.RESERVED_WORD);
        tokenMap.put("LOR", Token.RESERVED_WORD);
        tokenMap.put("IXOR", Token.RESERVED_WORD);
        tokenMap.put("LXOR", Token.RESERVED_WORD);
        tokenMap.put("IINC", Token.RESERVED_WORD);
        tokenMap.put("I2L", Token.RESERVED_WORD);
        tokenMap.put("I2F", Token.RESERVED_WORD);
        tokenMap.put("I2D", Token.RESERVED_WORD);
        tokenMap.put("L2I", Token.RESERVED_WORD);
        tokenMap.put("L2F", Token.RESERVED_WORD);
        tokenMap.put("L2D", Token.RESERVED_WORD);
        tokenMap.put("F2I", Token.RESERVED_WORD);
        tokenMap.put("F2L", Token.RESERVED_WORD);
        tokenMap.put("F2D", Token.RESERVED_WORD);
        tokenMap.put("D2I", Token.RESERVED_WORD);
        tokenMap.put("D2L", Token.RESERVED_WORD);
        tokenMap.put("D2F", Token.RESERVED_WORD);
        tokenMap.put("I2B", Token.RESERVED_WORD);
        tokenMap.put("I2C", Token.RESERVED_WORD);
        tokenMap.put("I2S", Token.RESERVED_WORD);
        tokenMap.put("LCMP", Token.RESERVED_WORD);
        tokenMap.put("FCMPL", Token.RESERVED_WORD);
        tokenMap.put("FCMPG", Token.RESERVED_WORD);
        tokenMap.put("DCMPL", Token.RESERVED_WORD);
        tokenMap.put("DCMPG", Token.RESERVED_WORD);
        tokenMap.put("IFEQ", Token.RESERVED_WORD);
        tokenMap.put("IFNE", Token.RESERVED_WORD);
        tokenMap.put("IFLT", Token.RESERVED_WORD);
        tokenMap.put("IFGE", Token.RESERVED_WORD);
        tokenMap.put("IFGT", Token.RESERVED_WORD);
        tokenMap.put("IFLE", Token.RESERVED_WORD);
        tokenMap.put("IF_ICMPEQ", Token.RESERVED_WORD);
        tokenMap.put("IF_ICMPNE", Token.RESERVED_WORD);
        tokenMap.put("IF_ICMPLT", Token.RESERVED_WORD);
        tokenMap.put("IF_ICMPGE", Token.RESERVED_WORD);
        tokenMap.put("IF_ICMPGT", Token.RESERVED_WORD);
        tokenMap.put("IF_ICMPLE", Token.RESERVED_WORD);
        tokenMap.put("IF_ACMPEQ", Token.RESERVED_WORD);
        tokenMap.put("IF_ACMPNE", Token.RESERVED_WORD);
        tokenMap.put("GOTO", Token.RESERVED_WORD);
        tokenMap.put("JSR", Token.RESERVED_WORD);
        tokenMap.put("RET", Token.RESERVED_WORD);
        tokenMap.put("TABLESWITCH", Token.RESERVED_WORD);
        tokenMap.put("LOOKUPSWITCH", Token.RESERVED_WORD);
        tokenMap.put("IRETURN", Token.RESERVED_WORD);
        tokenMap.put("LRETURN", Token.RESERVED_WORD);
        tokenMap.put("FRETURN", Token.RESERVED_WORD);
        tokenMap.put("DRETURN", Token.RESERVED_WORD);
        tokenMap.put("ARETURN", Token.RESERVED_WORD);
        tokenMap.put("RETURN", Token.RESERVED_WORD);
        tokenMap.put("GETSTATIC", Token.RESERVED_WORD);
        tokenMap.put("PUTSTATIC", Token.RESERVED_WORD);
        tokenMap.put("GETFIELD", Token.RESERVED_WORD);
        tokenMap.put("PUTFIELD", Token.RESERVED_WORD);
        tokenMap.put("INVOKEVIRTUAL", Token.RESERVED_WORD);
        tokenMap.put("INVOKESPECIAL", Token.RESERVED_WORD);
        tokenMap.put("INVOKESTATIC", Token.RESERVED_WORD);
        tokenMap.put("INVOKEINTERFACE", Token.RESERVED_WORD);
        tokenMap.put("INVOKEDYNAMIC", Token.RESERVED_WORD);
        tokenMap.put("NEW", Token.RESERVED_WORD);
        tokenMap.put("NEWARRAY", Token.RESERVED_WORD);
        tokenMap.put("ANEWARRAY", Token.RESERVED_WORD);
        tokenMap.put("ARRAYLENGTH", Token.RESERVED_WORD);
        tokenMap.put("ATHROW", Token.RESERVED_WORD);
        tokenMap.put("CHECKCAST", Token.RESERVED_WORD);
        tokenMap.put("INSTANCEOF", Token.RESERVED_WORD);
        tokenMap.put("MONITORENTER", Token.RESERVED_WORD);
        tokenMap.put("MONITOREXIT", Token.RESERVED_WORD);
        tokenMap.put("MULTIANEWARRAY", Token.RESERVED_WORD);
        tokenMap.put("IFNULL", Token.RESERVED_WORD);
        tokenMap.put("IFNONNULL", Token.RESERVED_WORD);


        tokenMap.put("LDC", Token.RESERVED_WORD);
        tokenMap.put("NEW", Token.RESERVED_WORD);
        tokenMap.put("LINENUMBER", Token.RESERVED_WORD);
        tokenMap.put("DEFINE", Token.RESERVED_WORD);
        tokenMap.put("FIELD", Token.RESERVED_WORD);
        tokenMap.put("METHOD", Token.RESERVED_WORD);
        tokenMap.put("METHOD_START", Token.RESERVED_WORD);
        tokenMap.put("METHOD_END", Token.RESERVED_WORD);
        tokenMap.put("LABEL", Token.RESERVED_WORD);
        tokenMap.put("JUMP", Token.RESERVED_WORD);
        tokenMap.put("OPCODE", Token.RESERVED_WORD);
        tokenMap.put("VER", Token.RESERVED_WORD);
        tokenMap.put("TYPE", Token.RESERVED_WORD);
        tokenMap.put("INT", Token.RESERVED_WORD);
        tokenMap.put("VER", Token.RESERVED_WORD);


        tokenMap.put("public", Token.RESERVED_WORD);
        tokenMap.put("private", Token.RESERVED_WORD);
        tokenMap.put("static", Token.RESERVED_WORD);
        tokenMap.put("final", Token.RESERVED_WORD);
        tokenMap.put("interface", Token.RESERVED_WORD);
        tokenMap.put("class", Token.RESERVED_WORD);
        tokenMap.put("abstract", Token.RESERVED_WORD);
        tokenMap.put("LOCALVARIABLE", Token.RESERVED_WORD);
        tokenMap.put("MAXSTACK", Token.RESERVED_WORD);
        tokenMap.put("MAXLOCALS", Token.RESERVED_WORD);
        tokenMap.put("FRAME", Token.RESERVED_WORD);
        return tokenMap;
    }

    @Override
    public void addToken(Segment segment, int start, int end, int tokenType, int startOffset) {
        // This assumes all keywords, etc. were parsed as "identifiers."
        if (tokenType == Token.IDENTIFIER) {
            int value = wordsToHighlight.get(segment, start, end);
            if (value != -1) {
                tokenType = value;
            }
        }
        super.addToken(segment, start, end, tokenType, startOffset);
    }

    /**
     * Returns a list of tokens representing the given text.
     *
     * @param text           The text to break into tokens.
     * @param startTokenType The token with which to start tokenizing.
     * @param startOffset    The offset at which the line of tokens begins.
     * @return A linked list of tokens representing <code>text</code>.
     */
    public Token getTokenList(Segment text, int startTokenType, int startOffset) {

        resetTokenList();

        char[] array = text.array;
        int offset = text.offset;
        int count = text.count;
        int end = offset + count;

        // Token starting offsets are always of the form:
        // 'startOffset + (currentTokenStart-offset)', but since startOffset and
        // offset are constant, tokens' starting positions become:
        // 'newStartOffset+currentTokenStart'.
        int newStartOffset = startOffset - offset;

        int currentTokenStart = offset;
        int currentTokenType = startTokenType;

        for (int i = offset; i < end; i++) {

            char c = array[i];

            switch (currentTokenType) {

                case Token.NULL:

                    currentTokenStart = i;   // Starting a new token here.

                    switch (c) {

                        case ' ':
                        case '\t':
                            currentTokenType = Token.WHITESPACE;
                            break;

                        case '"':
                            currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
                            break;

                        case '#':
                            currentTokenType = Token.COMMENT_EOL;
                            break;

                        default:
                            if (RSyntaxUtilities.isDigit(c)) {
                                currentTokenType = Token.LITERAL_NUMBER_DECIMAL_INT;
                                break;
                            } else if (RSyntaxUtilities.isLetter(c) || c == '/' || c == '_') {
                                currentTokenType = Token.IDENTIFIER;
                                break;
                            }

                            // Anything not currently handled - mark as an identifier
                            currentTokenType = Token.IDENTIFIER;
                            break;

                    } // End of switch (c).

                    break;

                case Token.WHITESPACE:

                    switch (c) {

                        case ' ':
                        case '\t':
                            break;   // Still whitespace.

                        case '"':
                            addToken(text, currentTokenStart, i - 1, Token.WHITESPACE, newStartOffset + currentTokenStart);
                            currentTokenStart = i;
                            currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
                            break;

                        case '#':
                            addToken(text, currentTokenStart, i - 1, Token.WHITESPACE, newStartOffset + currentTokenStart);
                            currentTokenStart = i;
                            currentTokenType = Token.COMMENT_EOL;
                            break;

                        default:   // Add the whitespace token and start anew.

                            addToken(text, currentTokenStart, i - 1, Token.WHITESPACE, newStartOffset + currentTokenStart);
                            currentTokenStart = i;

                            if (RSyntaxUtilities.isDigit(c)) {
                                currentTokenType = Token.LITERAL_NUMBER_DECIMAL_INT;
                                break;
                            } else if (RSyntaxUtilities.isLetter(c) || c == '/' || c == '_') {
                                currentTokenType = Token.IDENTIFIER;
                                break;
                            }

                            // Anything not currently handled - mark as identifier
                            currentTokenType = Token.IDENTIFIER;

                    } // End of switch (c).

                    break;

                default: // Should never happen
                case Token.IDENTIFIER:

                    switch (c) {

                        case ' ':
                        case '\t':
                            addToken(text, currentTokenStart, i - 1, Token.IDENTIFIER, newStartOffset + currentTokenStart);
                            currentTokenStart = i;
                            currentTokenType = Token.WHITESPACE;
                            break;

                        case '"':
                            addToken(text, currentTokenStart, i - 1, Token.IDENTIFIER, newStartOffset + currentTokenStart);
                            currentTokenStart = i;
                            currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
                            break;

                        default:
                            if (RSyntaxUtilities.isLetterOrDigit(c) || c == '/' || c == '_') {
                                break;   // Still an identifier of some type.
                            }
                            // Otherwise, we're still an identifier (?).

                    } // End of switch (c).

                    break;

                case Token.LITERAL_NUMBER_DECIMAL_INT:

                    switch (c) {

                        case ' ':
                        case '\t':
                            addToken(text, currentTokenStart, i - 1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset + currentTokenStart);
                            currentTokenStart = i;
                            currentTokenType = Token.WHITESPACE;
                            break;

                        case '"':
                            addToken(text, currentTokenStart, i - 1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset + currentTokenStart);
                            currentTokenStart = i;
                            currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
                            break;

                        default:

                            if (RSyntaxUtilities.isDigit(c)) {
                                break;   // Still a literal number.
                            }

                            // Otherwise, remember this was a number and start over.
                            addToken(text, currentTokenStart, i - 1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset + currentTokenStart);
                            i--;
                            currentTokenType = Token.NULL;

                    } // End of switch (c).

                    break;

                case Token.COMMENT_EOL:
                    i = end - 1;
                    addToken(text, currentTokenStart, i, currentTokenType, newStartOffset + currentTokenStart);
                    // We need to set token type to null so at the bottom we don't add one more token.
                    currentTokenType = Token.NULL;
                    break;

                case Token.LITERAL_STRING_DOUBLE_QUOTE:
                    if (c == '"') {
                        addToken(text, currentTokenStart, i, Token.LITERAL_STRING_DOUBLE_QUOTE, newStartOffset + currentTokenStart);
                        currentTokenType = Token.NULL;
                    }
                    break;

            } // End of switch (currentTokenType).

        } // End of for (int i=offset; i<end; i++).

        switch (currentTokenType) {

            // Remember what token type to begin the next line with.
            case Token.LITERAL_STRING_DOUBLE_QUOTE:
                addToken(text, currentTokenStart, end - 1, currentTokenType, newStartOffset + currentTokenStart);
                break;

            // Do nothing if everything was okay.
            case Token.NULL:
                addNullToken();
                break;

            // All other token types don't continue to the next line...
            default:
                addToken(text, currentTokenStart, end - 1, currentTokenType, newStartOffset + currentTokenStart);
                addNullToken();

        }

        // Return the first token in our linked list.
        return firstToken;

    }
}
