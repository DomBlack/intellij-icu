package com.domblack.icu;

import com.domblack.icu.psi.impl.IcuBlockImpl;
import com.domblack.icu.psi.impl.IcuMessageImpl;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IcuStructureViewElement implements StructureViewTreeElement, SortableTreeElement {
    private NavigatablePsiElement element;

    public IcuStructureViewElement(NavigatablePsiElement element) {
        this.element = element;
    }

    @Override
    public Object getValue() {
        return element;
    }

    @Override
    public void navigate(boolean requestFocus) {
        element.navigate(requestFocus);
    }

    @Override
    public boolean canNavigate() {
        return element.canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return element.canNavigateToSource();
    }

    @NotNull
    @Override
    public String getAlphaSortKey() {
        String name = element.getName();
        return name != null ? name : "";
    }

    @NotNull
    @Override
    public ItemPresentation getPresentation() {
        ItemPresentation presentation = element.getPresentation();
        return presentation != null ? presentation : new PresentationData();
    }

    @NotNull
    @Override
    public TreeElement[] getChildren() {
        List<TreeElement> treeElements = new ArrayList<>();

        IcuBlock[] blocks = PsiTreeUtil.getChildrenOfType(element, IcuBlock.class);
        if (blocks != null) {
            for (IcuBlock block : blocks) {
                treeElements.add(new IcuStructureViewElement((IcuBlockImpl) block));
            }
        }

        IcuMessage[] messages = PsiTreeUtil.getChildrenOfType(element, IcuMessage.class);
        if (messages != null) {
            for (IcuMessage message : messages) {
                treeElements.add(new IcuStructureViewElement((IcuMessageImpl) message));
            }
        }

        return treeElements.toArray(new TreeElement[0]);
    }
}
