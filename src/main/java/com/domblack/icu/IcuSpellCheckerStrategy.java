package com.domblack.icu;

import com.domblack.icu.psi.IcuTypes;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy;
import com.intellij.spellchecker.tokenizer.Tokenizer;
import org.jetbrains.annotations.NotNull;

public class IcuSpellCheckerStrategy extends SpellcheckingStrategy implements IcuTypes {
    @NotNull
    @Override
    public Tokenizer getTokenizer(PsiElement element) {
        final ASTNode node = element.getNode();
        if (node != null) {
            final IElementType type = node.getElementType();
            if (type == MESSAGE_STRING || type == COMMENT || type == ID) {
                return TEXT_TOKENIZER;
            }
        }

        return super.getTokenizer(element);
    }
}
