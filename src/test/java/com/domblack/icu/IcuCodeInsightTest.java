package com.domblack.icu;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.CodeStyleSettingsManager;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import com.intellij.util.containers.ContainerUtil;

public class IcuCodeInsightTest extends LightCodeInsightFixtureTestCase {
    @Override
    protected String getTestDataPath() {
        return "src/test/testData";
    }

    public void testFormatter() {
        myFixture.configureByFiles("FormatterTestData.icu");
        CodeStyleSettingsManager.getSettings(getProject()).SPACE_AROUND_ASSIGNMENT_OPERATORS = true;
        CodeStyleSettingsManager.getSettings(getProject()).KEEP_BLANK_LINES_IN_CODE = 2;
        new WriteCommandAction.Simple(getProject()) {
            @Override
            protected void run() throws Throwable {
                CodeStyleManager.getInstance(getProject()).reformatText(myFixture.getFile(),
                        ContainerUtil.newArrayList(myFixture.getFile().getTextRange()));
            }
        }.execute();
        myFixture.checkResultByFile("DefaultTestData.icu");
    }
}
