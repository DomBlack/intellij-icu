package com.domblack.icu;

import com.domblack.icu.psi.IcuTypes;
import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IcuPairedBraceMatcher implements PairedBraceMatcher, IcuTypes {
    private static final BracePair[] PAIRS = new BracePair[] {
            new BracePair(LEFT_BRACE, RIGHT_BRACE, true),
            new BracePair(ARG_LEFT_BRACE, ARG_RIGHT_BRACE, true),
    };

    @NotNull
    @Override
    public BracePair[] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return true;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}
