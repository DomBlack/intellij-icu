package com.domblack.icu;

import com.intellij.openapi.fileTypes.FileNameMatcher;
import org.jetbrains.annotations.NotNull;

public class IcuFileNameMatcher implements FileNameMatcher {
    public static IcuFileNameMatcher INSTANCE = new IcuFileNameMatcher();

    @Override
    public boolean accept(@NotNull String fileName) {
        if (fileName.endsWith(".icu")) {
            return true;
        }

        if (fileName.endsWith(".txt")) {
            for (String id : IcuUtil.LANG_IDS) {
                if (fileName.equals(id + ".txt")) {
                    return true;
                }
            }
        }

        return false;
    }

    @NotNull
    @Override
    public String getPresentableString() {
        return "ICU";
    }
}
