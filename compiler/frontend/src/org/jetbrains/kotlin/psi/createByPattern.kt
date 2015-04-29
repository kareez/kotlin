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

package org.jetbrains.kotlin.psi

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.SmartPointerManager
import com.intellij.psi.SmartPsiElementPointer
import com.intellij.psi.codeStyle.CodeStyleManager
import org.jetbrains.kotlin.psi.psiUtil.parents
import java.util.*

public fun JetPsiFactory.createExpressionByPattern(pattern: String, vararg args: PsiElement): JetExpression {
    val data = processPattern(pattern)

    if (args.size() != data.ranges.size()) {
        throw IllegalArgumentException("Wrong number of arguments, expected: ${data.ranges.size()}, passed: ${args.size()}")
    }

    var expression = createExpression(data.processedText)
    val project = expression.getProject()

    val start = expression.getTextRange().getStartOffset()

    val pointerManager = SmartPointerManager.getInstance(project)

    val pointers = HashMap<SmartPsiElementPointer<PsiElement>, Int>()
    for ((n, placeholders) in data.ranges) {
        for ((range, text) in placeholders) {
            val token = expression.findElementAt(range.getStartOffset())!!
            for (element in token.parents()) {
                val elementRange = element.getTextRange().shiftRight(-start)
                if (elementRange == range) {
                    // take maximal element with such range
                    val elementToUse = element.parents(withItself = false).lastOrNull { it.getTextLength() == elementRange.getLength() } ?: element
                    val pointer = pointerManager.createSmartPsiElementPointer(elementToUse)
                    pointers.put(pointer, n)
                    break
                }
                else if (!range.contains(elementRange)) {
                    throw IllegalArgumentException("Invalid pattern - no PsiElement found for $$n")
                }
            }
        }
    }


    expression = CodeStyleManager.getInstance(project).reformat(expression, true) as JetExpression

    for ((pointer, n) in pointers) {
        val element = pointer.getElement()!!
        element.replace(args[n])
    }

    return expression
}

private data class Placeholder(val range: TextRange, val text: String)
private data class PatternData(val processedText: String, val ranges: Map<Int, List<Placeholder>>)

private fun processPattern(pattern: String): PatternData {
    val ranges = LinkedHashMap<Int, MutableList<Placeholder>>()

    fun charOrNull(i: Int) = if (i < pattern.length()) pattern[i] else null

    fun check(condition: Boolean, message: String) {
        if (!condition) {
            throw IllegalArgumentException("Invalid pattern '$pattern' - $message")
        }
    }

    val text = StringBuilder {
        var i = 0
        while (i < pattern.length()) {
            var c = pattern[i]

            if (c == '$') {
                val nextChar = charOrNull(++i)
                if (nextChar == '$') {
                    append(nextChar)
                }
                else {
                    check(nextChar?.isDigit() ?: false, "unclosed '$'")

                    val lastIndex = (i..pattern.length() - 1).firstOrNull { !pattern[it].isDigit() } ?: pattern.length()
                    val n = pattern.substring(i, lastIndex).toInt()
                    check(n >= 0, "invalid placeholder number: $n")
                    i = lastIndex

                    val placeholderText = if (charOrNull(i) != '=') {
                        "xxx"
                    }
                    else {
                        i++
                        val endIndex = pattern.indexOf('$', i)
                        check(endIndex >= 0, "unclosed placeholder text")
                        check(endIndex > i, "empty placeholder text")
                        val text = pattern.substring(i, endIndex)
                        i = endIndex + 1
                        text
                    }

                    append(placeholderText)
                    val range = TextRange(length() - placeholderText.length(), length())
                    ranges.getOrPut(n, { ArrayList() }).add(Placeholder(range, placeholderText))
                    continue
                }
            }
            else {
                append(c)
            }
            i++
        }
    }.toString()

    check(!ranges.isEmpty(), "no placeholders found")

    val max = ranges.keySet().max()!!
    for (i in 0..max) {
        check(ranges.contains(i), "no '$$i' placeholder")
    }

    return PatternData(text, ranges)
}
