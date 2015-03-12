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

package org.jetbrains.jet.plugin.completion.smart

import com.intellij.codeInsight.lookup.LookupElement
import org.jetbrains.jet.lang.psi.JetExpression
import org.jetbrains.jet.lang.descriptors.ReceiverParameterDescriptor
import com.intellij.codeInsight.lookup.LookupElementBuilder
import org.jetbrains.jet.renderer.DescriptorRenderer
import org.jetbrains.jet.lang.psi.JetFunctionLiteral
import org.jetbrains.jet.lang.psi.JetValueArgument
import org.jetbrains.jet.lang.psi.JetValueArgumentList
import org.jetbrains.jet.lang.psi.JetCallExpression
import org.jetbrains.jet.lang.psi.JetSimpleNameExpression
import org.jetbrains.jet.lang.resolve.BindingContext
import org.jetbrains.jet.plugin.completion.ExpectedInfo
import org.jetbrains.jet.lang.resolve.DescriptorToSourceUtils
import org.jetbrains.jet.lang.psi.JetFunctionLiteralExpression
import org.jetbrains.jet.plugin.util.FuzzyType
import org.jetbrains.jet.plugin.util.getImplicitReceiversWithInstance

class ThisItems(val bindingContext: BindingContext) {
    public fun addToCollection(collection: MutableCollection<LookupElement>, context: JetExpression, expectedInfos: Collection<ExpectedInfo>) {
        val scope = bindingContext[BindingContext.RESOLUTION_SCOPE, context] ?: return

        for ((i, receiver) in scope.getImplicitReceiversWithInstance().withIndex()) {
            val thisType = receiver.getType()
            val fuzzyType = FuzzyType(thisType, listOf())
            val classifier = { (expectedInfo: ExpectedInfo) -> fuzzyType.classifyExpectedInfo(expectedInfo) }
            fun createLookupElement(): LookupElement? {
                //TODO: use this code when KT-4258 fixed
                //val expressionText = if (i == 0) "this" else "this@" + (thisQualifierName(receiver, bindingContext) ?: return null)
                val qualifier = if (i == 0) null else (thisQualifierName(receiver) ?: return null)
                val expressionText = if (qualifier == null) "this" else "this@" + qualifier
                return LookupElementBuilder.create(expressionText)
                        .withTypeText(DescriptorRenderer.SHORT_NAMES_IN_TYPES.renderType(thisType))
                        .assignSmartCompletionPriority(SmartCompletionItemPriority.THIS)
            }
            collection.addLookupElements(null, expectedInfos, classifier) { createLookupElement() }
        }
    }

    private fun thisQualifierName(receiver: ReceiverParameterDescriptor): String? {
        val descriptor = receiver.getContainingDeclaration()
        val name = descriptor.getName()
        if (!name.isSpecial()) {
            return name.asString()
        }

        val functionLiteral = DescriptorToSourceUtils.descriptorToDeclaration(descriptor) as? JetFunctionLiteral
        return (((((functionLiteral?.getParent() as? JetFunctionLiteralExpression)
                    ?.getParent() as? JetValueArgument)
                        ?.getParent() as? JetValueArgumentList)
                            ?.getParent() as? JetCallExpression)
                                ?.getCalleeExpression() as? JetSimpleNameExpression)
                                    ?.getReferencedName()
    }

}