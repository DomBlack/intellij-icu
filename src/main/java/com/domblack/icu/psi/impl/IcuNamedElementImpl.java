package com.domblack.icu.psi.impl;

import com.domblack.icu.IcuIcons;
import com.domblack.icu.psi.IcuNamedElement;
import com.domblack.icu.psi.IcuTypes;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public abstract class IcuNamedElementImpl extends ASTWrapperPsiElement implements IcuNamedElement {
    public IcuNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable
    public String getName() {
        ASTNode keyNode = this.getNode().findChildByType(IcuTypes.ID);

        if (keyNode != null) {
            return keyNode.getText();
        } else {
            return null;
        }
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        return null;
    }

    @Nullable
    @Override
    public PsiElement getNameIdentifier() {
        ASTNode keyNode = this.getNode().findChildByType(IcuTypes.ID);
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }

    @Override
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return IcuNamedElementImpl.this.getName();
            }

            @NotNull
            @Override
            public String getLocationString() {
                return IcuNamedElementImpl.this.getContainingFile().getName();
            }

            @NotNull
            @Override
            public Icon getIcon(boolean unused) {
                return IcuIcons.FILE;
            }
        };
    }
}
