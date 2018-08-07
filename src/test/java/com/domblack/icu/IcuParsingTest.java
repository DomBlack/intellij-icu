package com.domblack.icu;

import com.intellij.testFramework.ParsingTestCase;

public class IcuParsingTest extends ParsingTestCase {
    public IcuParsingTest() {
        super("", "icu", new IcuParserDefinition());
    }

    public void testParsingTestData() {
        doTest(true);
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/testData";
    }

    @Override
    protected boolean skipSpaces() {
        return false;
    }

    @Override
    protected boolean includeRanges() {
        return true;
    }
}
