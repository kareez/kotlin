/*
 * Copyright 2010-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.plugin.conversion.copy;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.jet.JUnit3RunnerWithInners;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/testData/copyPaste/conversion")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class JavaToKotlinCopyPasteConversionTestGenerated extends AbstractJavaToKotlinCopyPasteConversionTest {
    public void testAllFilesPresentInConversion() throws Exception {
        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/copyPaste/conversion"), Pattern.compile("^(.+)\\.java$"), true);
    }

    @TestMetadata("Arithmetic.java")
    public void testArithmetic() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/copyPaste/conversion/Arithmetic.java");
        doTest(fileName);
    }

    @TestMetadata("Constructor.java")
    public void testConstructor() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/copyPaste/conversion/Constructor.java");
        doTest(fileName);
    }

    @TestMetadata("HalfTheWhiteSpace.java")
    public void testHalfTheWhiteSpace() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/copyPaste/conversion/HalfTheWhiteSpace.java");
        doTest(fileName);
    }

    @TestMetadata("Imports1.java")
    public void testImports1() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/copyPaste/conversion/Imports1.java");
        doTest(fileName);
    }

    @TestMetadata("Imports2.java")
    public void testImports2() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/copyPaste/conversion/Imports2.java");
        doTest(fileName);
    }

    @TestMetadata("Imports3.java")
    public void testImports3() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/copyPaste/conversion/Imports3.java");
        doTest(fileName);
    }

    @TestMetadata("Indentation.java")
    public void testIndentation() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/copyPaste/conversion/Indentation.java");
        doTest(fileName);
    }

    @TestMetadata("MethodReferenceWithoutQualifier.java")
    public void testMethodReferenceWithoutQualifier() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/copyPaste/conversion/MethodReferenceWithoutQualifier.java");
        doTest(fileName);
    }

    @TestMetadata("OnlyClosingBrace.java")
    public void testOnlyClosingBrace() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/copyPaste/conversion/OnlyClosingBrace.java");
        doTest(fileName);
    }

    @TestMetadata("OnlyOneBraceFromBlock.java")
    public void testOnlyOneBraceFromBlock() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/copyPaste/conversion/OnlyOneBraceFromBlock.java");
        doTest(fileName);
    }

    @TestMetadata("OnlyQualifier.java")
    public void testOnlyQualifier() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/copyPaste/conversion/OnlyQualifier.java");
        doTest(fileName);
    }

    @TestMetadata("SampleBlock.java")
    public void testSampleBlock() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/copyPaste/conversion/SampleBlock.java");
        doTest(fileName);
    }

    @TestMetadata("SeveralMethodsSample.java")
    public void testSeveralMethodsSample() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/copyPaste/conversion/SeveralMethodsSample.java");
        doTest(fileName);
    }

    @TestMetadata("SingleWordFromIdentifier.java")
    public void testSingleWordFromIdentifier() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/copyPaste/conversion/SingleWordFromIdentifier.java");
        doTest(fileName);
    }
}