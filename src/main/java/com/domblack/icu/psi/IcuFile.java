package com.domblack.icu.psi;

import com.domblack.icu.IcuFileType;
import com.domblack.icu.IcuLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class IcuFile extends PsiFileBase {
    public IcuFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, IcuLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return IcuFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "ICU File";
    }

    @Nullable
    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}
