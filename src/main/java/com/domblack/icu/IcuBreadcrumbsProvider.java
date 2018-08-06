package com.domblack.icu;

import com.intellij.lang.Language;
import com.intellij.psi.PsiElement;
import com.intellij.ui.breadcrumbs.BreadcrumbsProvider;
import org.jetbrains.annotations.NotNull;

public class IcuBreadcrumbsProvider implements BreadcrumbsProvider {
    private final static Language[] LANGUAGES = new Language[]{IcuLanguage.INSTANCE};

    @Override
    public Language[] getLanguages() {
        return LANGUAGES;
    }

    @Override
    public boolean acceptElement(@NotNull PsiElement element) {
        return element instanceof IcuBlock | element instanceof IcuMessage;
    }

    @NotNull
    @Override
    public String getElementInfo(@NotNull PsiElement element) {
        if (element instanceof IcuBlock) {
            return ((IcuBlock) element).getID();
        } else if (element instanceof IcuMessage) {
            return ((IcuMessage) element).getID();
        }
        throw new IllegalArgumentException("This element should not pass #acceptElement");
    }
}
