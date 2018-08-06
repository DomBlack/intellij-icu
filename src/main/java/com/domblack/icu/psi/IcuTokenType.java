package com.domblack.icu.psi;

import com.domblack.icu.IcuLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class IcuTokenType extends IElementType {
    public IcuTokenType(@NotNull @NonNls String debugName) {
        super(debugName, IcuLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "IcuTokenType." + super.toString();
    }
}
