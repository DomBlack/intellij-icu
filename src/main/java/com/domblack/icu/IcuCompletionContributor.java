package com.domblack.icu;

import com.domblack.icu.psi.IcuTypes;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.TokenType;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class IcuCompletionContributor extends CompletionContributor {
    public IcuCompletionContributor() {
        // Language keys
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiFile(),
//                PlatformPatterns.psiElement(IcuTypes.LANG_ID),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet resultSet) {
                        for (String id : IcuUtil.LANG_IDS) {
                            resultSet.addElement(LookupElementBuilder.create(id));
                        }
                    }
                }
        );
    }

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        if (parameters.getPosition().getNode().getElementType() == TokenType.BAD_CHARACTER) {
            PsiElement parent = parameters.getPosition().getParent();

            if (!(parent instanceof PsiErrorElement)) {
                if (parent.getPrevSibling() instanceof PsiErrorElement) {
                    parent = parent.getPrevSibling();
                }
            }

            if (parent instanceof PsiErrorElement) {
                final PsiErrorElement error = (PsiErrorElement)parent;


                if (error.getErrorDescription().contains("plural, select or selectordinal expected")) {
                    result.addElement(LookupElementBuilder.create("number"));
                    result.addElement(LookupElementBuilder.create("date"));
                    result.addElement(LookupElementBuilder.create("time"));
                    result.addElement(LookupElementBuilder.create("plural"));
                    result.addElement(LookupElementBuilder.create("select"));
                    result.addElement(LookupElementBuilder.create("selectordinal"));
                    return;
                }
            }
        }
        super.fillCompletionVariants(parameters, result);
    }
}
