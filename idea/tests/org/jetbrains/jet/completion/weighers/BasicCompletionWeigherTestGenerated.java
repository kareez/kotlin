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

package org.jetbrains.jet.completion.weighers;

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
@TestMetadata("idea/testData/completion/weighers/basic")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class BasicCompletionWeigherTestGenerated extends AbstractBasicCompletionWeigherTest {
    @TestMetadata("AfterNullable.kt")
    public void testAfterNullable() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/weighers/basic/AfterNullable.kt");
        doTest(fileName);
    }

    public void testAllFilesPresentInBasic() throws Exception {
        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/completion/weighers/basic"), Pattern.compile("^([^\\.]+)\\.kt$"), true);
    }

    @TestMetadata("Callables.kt")
    public void testCallables() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/weighers/basic/Callables.kt");
        doTest(fileName);
    }

    @TestMetadata("DeprecatedFun.kt")
    public void testDeprecatedFun() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/weighers/basic/DeprecatedFun.kt");
        doTest(fileName);
    }

    @TestMetadata("ExactMatchForKeyword.kt")
    public void testExactMatchForKeyword() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/weighers/basic/ExactMatchForKeyword.kt");
        doTest(fileName);
    }

    @TestMetadata("ImportedFirst.kt")
    public void testImportedFirst() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/weighers/basic/ImportedFirst.kt");
        doTest(fileName);
    }

    @TestMetadata("ImportedFirstForJavaClass.kt")
    public void testImportedFirstForJavaClass() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/weighers/basic/ImportedFirstForJavaClass.kt");
        doTest(fileName);
    }

    @TestMetadata("ImportedOrder.kt")
    public void testImportedOrder() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/weighers/basic/ImportedOrder.kt");
        doTest(fileName);
    }

    @TestMetadata("KeywordsLast.kt")
    public void testKeywordsLast() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/weighers/basic/KeywordsLast.kt");
        doTest(fileName);
    }

    @TestMetadata("LocalFileBeforeImported.kt")
    public void testLocalFileBeforeImported() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/weighers/basic/LocalFileBeforeImported.kt");
        doTest(fileName);
    }

    @TestMetadata("LocalValuesAndParams.kt")
    public void testLocalValuesAndParams() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/weighers/basic/LocalValuesAndParams.kt");
        doTest(fileName);
    }

    @TestMetadata("LocalsBeforeKeywords.kt")
    public void testLocalsBeforeKeywords() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/weighers/basic/LocalsBeforeKeywords.kt");
        doTest(fileName);
    }

    @TestMetadata("LocalsPropertiesKeywords.kt")
    public void testLocalsPropertiesKeywords() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/weighers/basic/LocalsPropertiesKeywords.kt");
        doTest(fileName);
    }

    @TestMetadata("NamedParameters.kt")
    public void testNamedParameters() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/weighers/basic/NamedParameters.kt");
        doTest(fileName);
    }

    @TestMetadata("ParametersBeforeKeywords.kt")
    public void testParametersBeforeKeywords() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/weighers/basic/ParametersBeforeKeywords.kt");
        doTest(fileName);
    }

    @TestMetadata("PropertiesBeforeKeywords.kt")
    public void testPropertiesBeforeKeywords() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/weighers/basic/PropertiesBeforeKeywords.kt");
        doTest(fileName);
    }
}