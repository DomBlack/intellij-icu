package com.domblack.icu;

import com.domblack.icu.psi.IcuTypes;
import com.domblack.icu.psi.impl.IcuLanguageKeyImpl;
import com.domblack.icu.psi.impl.IcuMessageStringImpl;
import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class IcuAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof IcuLanguageKeyImpl) {
            ASTNode node = element.getNode().findChildByType(IcuTypes.LANG_ID);
            if (node != null) {
                final String fileName = element.getContainingFile().getName();
                if (fileName.endsWith(".txt") && !fileName.equals(element.getText() + ".txt")) {
                    holder.createErrorAnnotation(node, "Language ID must match the file name. Expected: " + fileName.replace(".txt", ""));
                }
            }
        }

        if (element instanceof IcuMessageStringImpl) {
            ASTNode node = element.getNode().findChildByType(IcuTypes.INVALID_ESCAPE_STRING);
            if (node != null) {
                holder.createErrorAnnotation(node, "Invalid escape string: " + element.getText());
            }
        }
    }
}
