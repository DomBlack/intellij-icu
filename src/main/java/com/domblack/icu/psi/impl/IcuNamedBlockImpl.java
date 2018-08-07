package com.domblack.icu.psi.impl;

import com.domblack.icu.IcuBlock;
import com.domblack.icu.psi.IcuElementFactory;
import com.domblack.icu.psi.IcuTypes;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

public abstract class IcuNamedBlockImpl extends IcuNamedElementImpl {
    public IcuNamedBlockImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        ASTNode keyNode = this.getNode().findChildByType(IcuTypes.ID);
        if (keyNode != null) {
            IcuBlock message = IcuElementFactory.createBlock(this.getProject(), name);
            ASTNode newKeyNode = message.getFirstChild().getNode();
            this.getNode().replaceChild(keyNode, newKeyNode);
        }
        return this;
    }
}
