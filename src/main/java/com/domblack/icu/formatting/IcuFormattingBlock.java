package com.domblack.icu.formatting;

import com.domblack.icu.IcuFormattingModelBuilder;
import com.domblack.icu.psi.IcuTypes;
import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.formatter.common.AbstractBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class IcuFormattingBlock extends AbstractBlock {
    private IcuFormattingContext context;

    public IcuFormattingBlock(
            @NotNull ASTNode node,
            @NotNull IcuFormattingContext context,
            @Nullable Wrap wrap
    ) {
        super(node, wrap, context.computeAlignment(node));
        this.context = context;
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> blocks = new ArrayList<>();
        ASTNode child = myNode.getFirstChildNode();

        while (child != null) {
            if (child.getElementType() != TokenType.WHITE_SPACE) {
                Block block = IcuFormattingModelBuilder.createBlock(child, context);
                blocks.add(block);
            }
            child = child.getTreeNext();
        }
        return blocks;
    }

    @Override
    public Indent getIndent() {
        if (  myNode.getElementType() == IcuTypes.BLOCK
            || myNode.getElementType() == IcuTypes.MESSAGE
            || myNode.getElementType() == IcuTypes.MESSAGE_BLOCK
            || myNode.getElementType() == IcuTypes.QUOTE
            || myNode.getElementType() == IcuTypes.COMMENT
        ) {
            return Indent.getNormalIndent();
        } else {
            return Indent.getNoneIndent();
        }
    }

    @Nullable
    @Override
    public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
        return context.spacingBuilder.getSpacing(this, child1, child2);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @NotNull
    @Override
    public ChildAttributes getChildAttributes(int newChildIndex) {
        // When you press enter on a line, we will just want the normal indent
        return new ChildAttributes(Indent.getNormalIndent(), null);
    }
}
