package com.domblack.icu.formatting;

import com.domblack.icu.IcuLanguage;
import com.domblack.icu.psi.IcuTypes;
import com.intellij.formatting.Alignment;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.util.containers.FactoryMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class IcuFormattingContext {
    private final CodeStyleSettings mySettings;
    public final SpacingBuilder spacingBuilder;

    /** This alignments increase partial reformatting stability in case of initially incorrect indents */
    @NotNull
    private final Map<ASTNode, Alignment> myChildIndentAlignments =
            FactoryMap.create(node -> Alignment.createAlignment(true));

    public IcuFormattingContext(@NotNull CodeStyleSettings settings) {
        mySettings = settings;
        spacingBuilder = createSpaceBuilder(settings);
    }

    private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
        return new SpacingBuilder(settings, IcuLanguage.INSTANCE)

                .before(IcuTypes.LANG_ID)
                .none()

                .after(IcuTypes.LANG_ID)
                .spaces(1)

                .around(IcuTypes.ID)
                .spaces(1)

                .around(IcuTypes.LEFT_BRACE)
                .spaces(1)

                .around(IcuTypes.RIGHT_BRACE)
                .spaces(1)

                ;
    }

    public Alignment computeAlignment(@NotNull ASTNode node) {
        IElementType type = PsiUtilCore.getElementType(node);

        if (type == IcuTypes.MESSAGE_BLOCK) {
            final ASTNode grandParent = node.getTreeParent().getTreeParent();

            if (node.textContains('\n')) {
                // If this is a multi line message block, then we break existing alignments
                myChildIndentAlignments.remove(grandParent);
            } else {
                // Use the alignment of the grandparent
                return myChildIndentAlignments.get(grandParent);
            }
        }

        if (type == IcuTypes.BLOCK) {
            // A sub block removes alignments on messages in the tree
            myChildIndentAlignments.remove(node.getTreeParent());
        }

        return null;
    }
}
