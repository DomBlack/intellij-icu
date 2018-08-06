package com.domblack.icu;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class IcuFileType extends LanguageFileType {
    public static final IcuFileType INSTANCE = new IcuFileType();

    private IcuFileType() {
        super(IcuLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "ICU file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "ICU language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "icu";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return IcuIcons.FILE;
    }
}
