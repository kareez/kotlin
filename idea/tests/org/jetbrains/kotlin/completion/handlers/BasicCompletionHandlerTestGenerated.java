/*
 * Copyright 2010-2015 JetBrains s.r.o.
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

package org.jetbrains.kotlin.completion.handlers;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.InnerTestClasses;
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.test.JetTestUtils;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/testData/completion/handlers/basic")
@TestDataPath("$PROJECT_ROOT")
@InnerTestClasses({
        BasicCompletionHandlerTestGenerated.StringTemplate.class,
})
@RunWith(JUnit3RunnerWithInners.class)
public class BasicCompletionHandlerTestGenerated extends AbstractBasicCompletionHandlerTest {
    public void testAllFilesPresentInBasic() throws Exception {
        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/completion/handlers/basic"), Pattern.compile("^(.+)\\.kt$"), true);
    }

    @TestMetadata("ClassWithClassObject.kt")
    public void testClassWithClassObject() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/handlers/basic/ClassWithClassObject.kt");
        doTest(fileName);
    }

    @TestMetadata("DoNotUseParenthesisOnNextLine.kt")
    public void testDoNotUseParenthesisOnNextLine() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/handlers/basic/DoNotUseParenthesisOnNextLine.kt");
        doTest(fileName);
    }

    @TestMetadata("ExtensionReceiverTypeArg.kt")
    public void testExtensionReceiverTypeArg() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/handlers/basic/ExtensionReceiverTypeArg.kt");
        doTest(fileName);
    }

    @TestMetadata("GenericFunctionWithTab.kt")
    public void testGenericFunctionWithTab() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/handlers/basic/GenericFunctionWithTab.kt");
        doTest(fileName);
    }

    @TestMetadata("NestedTypeArg.kt")
    public void testNestedTypeArg() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/handlers/basic/NestedTypeArg.kt");
        doTest(fileName);
    }

    @TestMetadata("SecondTypeArg.kt")
    public void testSecondTypeArg() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/handlers/basic/SecondTypeArg.kt");
        doTest(fileName);
    }

    @TestMetadata("SuperTypeArg.kt")
    public void testSuperTypeArg() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/handlers/basic/SuperTypeArg.kt");
        doTest(fileName);
    }

    @TestMetadata("idea/testData/completion/handlers/basic/stringTemplate")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class StringTemplate extends AbstractBasicCompletionHandlerTest {
        @TestMetadata("1.kt")
        public void test1() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/handlers/basic/stringTemplate/1.kt");
            doTest(fileName);
        }

        @TestMetadata("2.kt")
        public void test2() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/handlers/basic/stringTemplate/2.kt");
            doTest(fileName);
        }

        @TestMetadata("3.kt")
        public void test3() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/handlers/basic/stringTemplate/3.kt");
            doTest(fileName);
        }

        @TestMetadata("4.kt")
        public void test4() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/handlers/basic/stringTemplate/4.kt");
            doTest(fileName);
        }

        public void testAllFilesPresentInStringTemplate() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/completion/handlers/basic/stringTemplate"), Pattern.compile("^(.+)\\.kt$"), true);
        }

        @TestMetadata("BackingField.kt")
        public void testBackingField() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/handlers/basic/stringTemplate/BackingField.kt");
            doTest(fileName);
        }

        @TestMetadata("NotEmptyPrefix.kt")
        public void testNotEmptyPrefix() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/handlers/basic/stringTemplate/NotEmptyPrefix.kt");
            doTest(fileName);
        }

        @TestMetadata("Replace.kt")
        public void testReplace() throws Exception {
            String fileName = JetTestUtils.navigationMetadata("idea/testData/completion/handlers/basic/stringTemplate/Replace.kt");
            doTest(fileName);
        }
    }
}