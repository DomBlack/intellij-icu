package com.domblack.icu;

import com.domblack.icu.psi.IcuTypes;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class IcuCompletionContributor extends CompletionContributor {
    public IcuCompletionContributor() {
        // Language keys
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement(IcuTypes.LANG_ID).withLanguage(IcuLanguage.INSTANCE),
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
}
