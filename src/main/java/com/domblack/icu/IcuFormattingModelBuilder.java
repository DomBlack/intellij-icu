package com.domblack.icu;

import com.domblack.icu.formatting.IcuFormattingBlock;
import com.domblack.icu.formatting.IcuFormattingContext;
import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.jetbrains.annotations.*;


public class IcuFormattingModelBuilder implements FormattingModelBuilder {
    @NotNull
    @Override
    public FormattingModel createModel(PsiElement element, CodeStyleSettings settings) {
        return FormattingModelProvider
                .createFormattingModelForPsiFile(
                        element.getContainingFile(),
                        createBlock(element.getNode(), new IcuFormattingContext(settings)),
                        settings
                );
    }

    public static Block createBlock(@NotNull ASTNode node, @NotNull IcuFormattingContext context) {
        return new IcuFormattingBlock(
                node,
                context,
                Wrap.createWrap(WrapType.NONE, false));
    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}
