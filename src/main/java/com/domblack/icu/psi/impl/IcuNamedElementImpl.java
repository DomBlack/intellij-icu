package com.domblack.icu.psi.impl;

import com.domblack.icu.psi.IcuNamedElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class IcuNamedElementImpl extends ASTWrapperPsiElement implements IcuNamedElement {
    public IcuNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
