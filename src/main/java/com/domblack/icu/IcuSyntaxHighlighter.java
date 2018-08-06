package com.domblack.icu;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.domblack.icu.psi.IcuTypes;
import com.thaiopensource.xml.dtd.om.Def;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class IcuSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey LANG_ID =
            createTextAttributesKey("ICU_LANG_ID", DefaultLanguageHighlighterColors.KEYWORD);

    public static final TextAttributesKey BRACES =
            createTextAttributesKey("ICU_BRACES", DefaultLanguageHighlighterColors.BRACES);

    public static final TextAttributesKey ID =
            createTextAttributesKey("ICU_ID", DefaultLanguageHighlighterColors.INSTANCE_FIELD);

    public static final TextAttributesKey STRING =
            createTextAttributesKey("ICU_STRING", DefaultLanguageHighlighterColors.STRING);

    public static final TextAttributesKey VALID_ESCAPE =
            createTextAttributesKey("VALID_ESCAPE", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE);

    public static final TextAttributesKey INVALID_ESCAPE =
            createTextAttributesKey("INVALID_ESCAPE", DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE);

    public static final TextAttributesKey COMMENT =
            createTextAttributesKey("ICU_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);

    public static final TextAttributesKey BAD_CHARACTER =
            createTextAttributesKey("ICU_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] LANG_ID_KEYS = new TextAttributesKey[]{LANG_ID};
    private static final TextAttributesKey[] BRACES_KEYS = new TextAttributesKey[]{BRACES};
    private static final TextAttributesKey[] ID_KEYS = new TextAttributesKey[]{ID};
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    private static final TextAttributesKey[] VALID_ESCAPE_KEYS = new TextAttributesKey[]{VALID_ESCAPE};
    private static final TextAttributesKey[] INVALID_ESCAPE_KEYS = new TextAttributesKey[]{INVALID_ESCAPE};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new IcuLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(IcuTypes.LANG_ID)) {
            return LANG_ID_KEYS;
        } else if (tokenType.equals(IcuTypes.LEFT_BRACE) || tokenType.equals(IcuTypes.RIGHT_BRACE)) {
            return BRACES_KEYS;
        } else if (tokenType.equals(IcuTypes.ID)) {
            return ID_KEYS;
        } else if (tokenType.equals(IcuTypes.STRING) || tokenType.equals(IcuTypes.QUOTE)) {
            return STRING_KEYS;
        } else if (tokenType.equals(IcuTypes.VALID_ESCAPE_STRING)) {
            return VALID_ESCAPE_KEYS;
        } else if (tokenType.equals(IcuTypes.INVALID_ESCAPE_STRING)) {
            return INVALID_ESCAPE_KEYS;
        } else if (tokenType.equals(IcuTypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}
