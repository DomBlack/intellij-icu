package com.domblack.icu.psi;

import com.domblack.icu.IcuLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class IcuElementType extends IElementType {
    public IcuElementType(@NotNull @NonNls String debugName) {
        super(debugName, IcuLanguage.INSTANCE);
    }
}
