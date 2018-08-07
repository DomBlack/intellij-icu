package com.domblack.icu.psi.impl;

import com.domblack.icu.IcuMessage;
import com.domblack.icu.psi.IcuElementFactory;
import com.domblack.icu.psi.IcuTypes;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

public abstract class IcuNamedMessageImpl extends IcuNamedElementImpl {
    public IcuNamedMessageImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        ASTNode keyNode = this.getNode().findChildByType(IcuTypes.ID);
        if (keyNode != null) {
            IcuMessage message = IcuElementFactory.createMessage(this.getProject(), name);
            ASTNode newKeyNode = message.getFirstChild().getNode();
            this.getNode().replaceChild(keyNode, newKeyNode);
        }
        return this;
    }
}
