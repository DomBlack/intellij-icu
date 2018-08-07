package com.domblack.icu;

import com.domblack.icu.psi.IcuNamedElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IcuFoldingBuilder extends FoldingBuilderEx {

    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        List<FoldingDescriptor> descriptors = new ArrayList<>();

        this.getNamedBlocks(root, descriptors);

        return descriptors.toArray(new FoldingDescriptor[0]);
    }

    private void getNamedBlocks(PsiElement node, List<FoldingDescriptor> descriptors) {
        Collection<IcuNamedElement> namedElements =
                PsiTreeUtil.findChildrenOfType(node, IcuNamedElement.class);

        for (final IcuNamedElement namedElement : namedElements) {
            String name = namedElement.getName();
            if (name == null) {
                continue;
            }

            TextRange range = new TextRange(
                    namedElement.getTextRange().getStartOffset() + name.length() + 2,
                    namedElement.getTextRange().getEndOffset() - 1
            );

            if (range.getLength() <= 0) {
                // ignore empty blocks
                continue;
            }

            FoldingGroup group = FoldingGroup.newGroup(name);

            descriptors.add(new FoldingDescriptor(
                    namedElement.getNode(),
                    range,
                    group
            ));
        }
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        return "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return true;
    }
}
