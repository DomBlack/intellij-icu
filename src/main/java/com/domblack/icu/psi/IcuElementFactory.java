package com.domblack.icu.psi;

import com.domblack.icu.IcuBlock;
import com.domblack.icu.IcuFileType;
import com.domblack.icu.IcuMessage;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;

public class IcuElementFactory {
    public static IcuMessage createMessage(Project project, String name) {
        final IcuFile file = createFile(project, "en { " + name + " { \"\" } }");
        return file.findChildByClass(IcuMessage.class);
    }

    public static IcuBlock createBlock(Project project, String name) {
        final IcuFile file = createFile(project, "en { " + name + " { } }");
        return file.findChildByClass(IcuBlock.class);
    }

    public static IcuFile createFile(Project project, String text) {
        String name = "dummy.icu";
        return (IcuFile) PsiFileFactory.getInstance(project)
                .createFileFromText(name, IcuFileType.INSTANCE, text);
    }
}
