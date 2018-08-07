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

                .after(IcuTypes.ARG_LEFT_BRACE)
                .none()

                .before(IcuTypes.ARG_RIGHT_BRACE)
                .none()

                .before(IcuTypes.COMMA)
                .none()

                .after(IcuTypes.COMMA)
                .spaces(1)

                .between(IcuTypes.COMMA, IcuTypes.QUOTE)
                .none()

                .between(IcuTypes.MESSAGE, IcuTypes.BLOCK)
                .blankLines(1)

                .between(IcuTypes.BLOCK, IcuTypes.MESSAGE)
                .blankLines(1)

                .between(IcuTypes.BLOCK, IcuTypes.BLOCK)
                .blankLines(1)

                .between(IcuTypes.MESSAGE, IcuTypes.MESSAGE)
                .spacing(0, 1, 0, true, 1)

                .between(IcuTypes.ID, IcuTypes.LEFT_BRACE)
                .spacing(1, 1, 0, false, 0)

                ;
    }

    public Alignment computeAlignment(@NotNull ASTNode node) {
        IElementType type = PsiUtilCore.getElementType(node);

        // Align option text within formats
        if (type == IcuTypes.ARG_LEFT_BRACE && node.getTreeParent().getElementType() == IcuTypes.OPTIONAL_FORMAT_PATTERN) {
            final ASTNode grandParent = node.getTreeParent().getTreeParent();

            // Use the alignment of the grandparent
            return myChildIndentAlignments.get(grandParent);
        }

        // If we get a multiline option contents, stop the alignment
        if (type == IcuTypes.OPTION_CONTENTS_PER_LINE) {
            final ASTNode optionsList = node
                    .getTreeParent() // OPTION_CONTENTS
                    .getTreeParent() // OPTIONAL_FORMAT_PATTERN
                    .getTreeParent(); // OPTIONS_LIST

            myChildIndentAlignments.remove(optionsList);
        }

        // Align message blocks
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
