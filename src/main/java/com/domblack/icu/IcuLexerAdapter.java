package com.domblack.icu;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class IcuLexerAdapter extends FlexAdapter {
    public IcuLexerAdapter() {
        super(new IcuLexer((Reader)null));
    }
}
