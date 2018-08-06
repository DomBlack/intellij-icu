package com.domblack.icu.psi.impl;

import com.domblack.icu.IcuBlock;
import com.domblack.icu.IcuIcons;
import com.domblack.icu.IcuMessage;
import com.domblack.icu.psi.IcuElementFactory;
import com.domblack.icu.psi.IcuTypes;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class IcuImplUtil {
    public static String getID(final IcuMessage element) {
        ASTNode keyNode = element.getNode().findChildByType(IcuTypes.ID);

        if (keyNode != null) {
            return keyNode.getText();
        } else {
            return null;
        }
    }

    public static String getName(IcuMessage message) {
        return getID(message);
    }

    public static PsiElement setName(IcuMessage element, String newName) {
        ASTNode keyNode = element.getNode().findChildByType(IcuTypes.ID);
        if (keyNode != null) {
            IcuMessage message = IcuElementFactory.createMessage(element.getProject(), newName);
            ASTNode newKeyNode = message.getFirstChild().getNode();
            element.getNode().replaceChild(keyNode, newKeyNode);
        }
        return element;
    }

    public static PsiElement getNameIdentifier(IcuMessage element) {
        ASTNode keyNode = element.getNode().findChildByType(IcuTypes.ID);
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }

    public static ItemPresentation getPresentation(final IcuMessage element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getID();
            }

            @Nullable
            @Override
            public String getLocationString() {
                return element.getContainingFile().getName();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return IcuIcons.FILE;
            }
        };
    }

    // <editor-fold desc="Icu Block">

    public static String getID(final IcuBlock element) {
        ASTNode keyNode = element.getNode().findChildByType(IcuTypes.ID);

        if (keyNode != null) {
            return keyNode.getText();
        } else {
            return null;
        }
    }

    public static String getName(IcuBlock message) {
        return getID(message);
    }

    public static PsiElement setName(IcuBlock element, String newName) {
        ASTNode keyNode = element.getNode().findChildByType(IcuTypes.ID);
        if (keyNode != null) {
            IcuBlock message = IcuElementFactory.createBlock(element.getProject(), newName);
            ASTNode newKeyNode = message.getFirstChild().getNode();
            element.getNode().replaceChild(keyNode, newKeyNode);
        }
        return element;
    }

    public static PsiElement getNameIdentifier(IcuBlock element) {
        ASTNode keyNode = element.getNode().findChildByType(IcuTypes.ID);
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }

    public static ItemPresentation getPresentation(final IcuBlock element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getID();
            }

            @Nullable
            @Override
            public String getLocationString() {
                return element.getContainingFile().getName();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return IcuIcons.FILE;
            }
        };
    }

    // </editor-fold>
}
