package com.domblack.icu;

import com.domblack.icu.psi.IcuFile;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.FileBasedIndex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class IcuUtil {
    public static List<IcuMessage> findMessages(Project project, String key) {
        List<IcuMessage> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(IcuFileType.INSTANCE, GlobalSearchScope.allScope(project));

        for (VirtualFile virtualFile : virtualFiles) {
            PsiFile icuFile = PsiManager.getInstance(project).findFile(virtualFile);
            if (icuFile != null && IcuFileNameMatcher.INSTANCE.accept(icuFile.getName())) {
                Collection<IcuMessage> ids = PsiTreeUtil.findChildrenOfType(icuFile, IcuMessage.class);

                for (IcuMessage block : ids) {
                    if (key.equals(block.getName())) {
                        result.add(block);
                    }
                }
            }
        }

        return result;
    }

    public static List<IcuMessage> findMessages(Project project) {
        List<IcuMessage> result = new ArrayList<>();

        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(IcuFileType.INSTANCE, GlobalSearchScope.allScope(project));

        for (VirtualFile virtualFile : virtualFiles) {
            PsiFile icuFile = PsiManager.getInstance(project).findFile(virtualFile);
            if (icuFile != null) {
                if (IcuFileNameMatcher.INSTANCE.accept(icuFile.getName())) {
                    Collection<IcuMessage> ids = PsiTreeUtil.findChildrenOfType(icuFile, IcuMessage.class);
                    Collections.addAll(result, ids.toArray(new IcuMessage[0]));
                }
            }
        }

        return result;
    }

    public static String[] LANG_IDS = new String[] {
            "en",
            "ab",
            "aa",
            "af",
            "sq",
            "am",
            "ar",
            "hy",
            "as",
            "ay",
            "az",
            "ba",
            "eu",
            "bn",
            "dz",
            "bh",
            "bi",
            "br",
            "bg",
            "my",
            "be",
            "km",
            "ca",
            "zh",
            "co",
            "hr",
            "cs",
            "da",
            "nl",
            "eo",
            "et",
            "fo",
            "fj",
            "fi",
            "fr",
            "fy",
            "gd",
            "gl",
            "ka",
            "de",
            "el",
            "kl",
            "gn",
            "gu",
            "ha",
            "iw",
            "hi",
            "hu",
            "is",
            "in",
            "ia",
            "ie",
            "ik",
            "ga",
            "it",
            "ja",
            "jw",
            "kn",
            "ks",
            "kk",
            "rw",
            "ky",
            "rn",
            "ko",
            "ku",
            "lo",
            "la",
            "lv",
            "ln",
            "lt",
            "mk",
            "mg",
            "ms",
            "ml",
            "mt",
            "mi",
            "mr",
            "mo",
            "mn",
            "na",
            "ne",
            "no",
            "oc",
            "or",
            "om",
            "ps",
            "fa",
            "pl",
            "pt",
            "pa",
            "qu",
            "rm",
            "ro",
            "ru",
            "sm",
            "sg",
            "sa",
            "sr",
            "sh",
            "st",
            "tn",
            "sn",
            "sd",
            "si",
            "ss",
            "sk",
            "sl",
            "so",
            "es",
            "su",
            "sw",
            "sv",
            "tl",
            "tg",
            "ta",
            "tt",
            "te",
            "th",
            "bo",
            "ti",
            "to",
            "ts",
            "tr",
            "tk",
            "tw",
            "uk",
            "ur",
            "uz",
            "vi",
            "vo",
            "cy",
            "wo",
            "xh",
            "ji",
            "yo",
            "zu",
    };
}
