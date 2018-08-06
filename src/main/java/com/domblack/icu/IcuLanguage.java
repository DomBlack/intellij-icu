package com.domblack.icu;

import com.intellij.lang.Language;

public class IcuLanguage extends Language {
    public static final IcuLanguage INSTANCE = new IcuLanguage();

    private IcuLanguage() {
        super("ICU");
    }
}
