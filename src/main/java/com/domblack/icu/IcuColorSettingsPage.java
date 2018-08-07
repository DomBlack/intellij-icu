package com.domblack.icu;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

public class IcuColorSettingsPage implements ColorSettingsPage {
    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[] {
            new AttributesDescriptor("Language ID", IcuSyntaxHighlighter.LANG_ID),
            new AttributesDescriptor("Key", IcuSyntaxHighlighter.ID),
            new AttributesDescriptor("Message", IcuSyntaxHighlighter.STRING),
            new AttributesDescriptor("Parameter", IcuSyntaxHighlighter.PARAMETER),
            new AttributesDescriptor("Comma", IcuSyntaxHighlighter.COMMA),
            new AttributesDescriptor("Format Type", IcuSyntaxHighlighter.FORMAT_TYPE),
            new AttributesDescriptor("Operator", IcuSyntaxHighlighter.OPERATOR),
            new AttributesDescriptor("Placeholder", IcuSyntaxHighlighter.PLACEHOLDER),
            new AttributesDescriptor("Number", IcuSyntaxHighlighter.NUMBER),
            new AttributesDescriptor("Option Case", IcuSyntaxHighlighter.OPTION_NAME),
            new AttributesDescriptor("Option Keyword", IcuSyntaxHighlighter.OPTION_KEYWORD),
            new AttributesDescriptor("Valid Escape String", IcuSyntaxHighlighter.VALID_ESCAPE),
            new AttributesDescriptor("Invalid Escape String", IcuSyntaxHighlighter.INVALID_ESCAPE),
            new AttributesDescriptor("Comment", IcuSyntaxHighlighter.COMMENT),
    };

    @Nullable
    @Override
    public Icon getIcon() {
        return IcuIcons.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new IcuSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return "en {\n" +
                "  messages {\n" +
                "    hello {\n" +
                "      // This is a line comment\n" +
                "      \"Welcome back {userLevel, select,\"\n"+
                "        \"admin {Administrator} other {}\"\n"+
                "      } {userName}, you have \"\n" +
                "      \"{unread, plural, \"\n" +
                "      \"  =0 {no messages}\"\n" +
                "      \"  one {# message}\"\n" +
                "      \"  other {# messages}\"\n" +
                "      \"}.\"\n" +
                "    }\n" +
                "\n" +
                "    copyright { \"\\u00A9 {year, number} \\i \" }\n" +
                "  }\n" +
                "}";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "ICU";
    }
}
