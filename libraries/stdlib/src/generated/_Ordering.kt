package kotlin

//
// NOTE THIS FILE IS AUTO-GENERATED by the GenerateStandardLib.kt
// See: https://github.com/JetBrains/kotlin/tree/master/libraries/stdlib
//

import kotlin.platform.*
import java.util.*

import java.util.Collections // TODO: it's temporary while we have java.util.Collections in js

/**
 * Returns a list with elements in reversed order
 */
public fun <T> Array<out T>.reverse(): List<T> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
}

/**
 * Returns a list with elements in reversed order
 */
public fun BooleanArray.reverse(): List<Boolean> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
}

/**
 * Returns a list with elements in reversed order
 */
public fun ByteArray.reverse(): List<Byte> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
}

/**
 * Returns a list with elements in reversed order
 */
public fun CharArray.reverse(): List<Char> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
}

/**
 * Returns a list with elements in reversed order
 */
public fun DoubleArray.reverse(): List<Double> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
}

/**
 * Returns a list with elements in reversed order
 */
public fun FloatArray.reverse(): List<Float> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
}

/**
 * Returns a list with elements in reversed order
 */
public fun IntArray.reverse(): List<Int> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
}

/**
 * Returns a list with elements in reversed order
 */
public fun LongArray.reverse(): List<Long> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
}

/**
 * Returns a list with elements in reversed order
 */
public fun ShortArray.reverse(): List<Short> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
}

/**
 * Returns a list with elements in reversed order
 */
public fun <T> Iterable<T>.reverse(): List<T> {
    val list = toArrayList()
    Collections.reverse(list)
    return list
}

/**
 * Returns a string with characters in reversed order
 */
public fun String.reverse(): String {
    return StringBuilder().append(this).reverse().toString()
}

/**
 * Returns a sorted list of all elements
 */
public fun <T : Comparable<T>> Iterable<T>.sort(): List<T> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList)
    return sortedList
}

/**
 * Returns a list of all elements, sorted by the specified *comparator*
 */
public fun <T> Array<out T>.sortBy(comparator: Comparator<in T>): List<T> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList, comparator)
    return sortedList
}

/**
 * Returns a list of all elements, sorted by the specified *comparator*
 */
public fun <T> Iterable<T>.sortBy(comparator: Comparator<in T>): List<T> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList, comparator)
    return sortedList
}

/**
 * Returns a sorted list of all elements, ordered by results of specified *order* function.
 */
public inline fun <T, R : Comparable<R>> Array<out T>.sortBy(inlineOptions(InlineOption.ONLY_LOCAL_RETURN) order: (T) -> R): List<T> {
    val sortedList = toArrayList()
    val sortBy: Comparator<T> = compareBy(order)
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
}

/**
 * Returns a sorted list of all elements, ordered by results of specified *order* function.
 */
public inline fun <T, R : Comparable<R>> Iterable<T>.sortBy(inlineOptions(InlineOption.ONLY_LOCAL_RETURN) order: (T) -> R): List<T> {
    val sortedList = toArrayList()
    val sortBy: Comparator<T> = compareBy(order)
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
}

/**
 * Returns a sorted list of all elements, in descending order
 */
public fun <T : Comparable<T>> Iterable<T>.sortDescending(): List<T> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList, comparator { x, y -> y.compareTo(x) })
    return sortedList
}

/**
 * Returns a sorted list of all elements, in descending order by results of specified *order* function.
 */
public inline fun <T, R : Comparable<R>> Array<out T>.sortDescendingBy(inlineOptions(InlineOption.ONLY_LOCAL_RETURN) order: (T) -> R): List<T> {
    val sortedList = toArrayList()
    val sortBy: Comparator<T> = compareByDescending(order)
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
}

/**
 * Returns a sorted list of all elements, in descending order by results of specified *order* function.
 */
public inline fun <T, R : Comparable<R>> Iterable<T>.sortDescendingBy(inlineOptions(InlineOption.ONLY_LOCAL_RETURN) order: (T) -> R): List<T> {
    val sortedList = toArrayList()
    val sortBy: Comparator<T> = compareByDescending(order)
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
}

/**
 * Returns a sorted list of all elements
 */
public fun <T : Comparable<T>> Array<out T>.toSortedList(): List<T> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList)
    return sortedList
}

/**
 * Returns a sorted list of all elements
 */
public fun BooleanArray.toSortedList(): List<Boolean> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList)
    return sortedList
}

/**
 * Returns a sorted list of all elements
 */
public fun ByteArray.toSortedList(): List<Byte> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList)
    return sortedList
}

/**
 * Returns a sorted list of all elements
 */
public fun CharArray.toSortedList(): List<Char> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList)
    return sortedList
}

/**
 * Returns a sorted list of all elements
 */
public fun DoubleArray.toSortedList(): List<Double> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList)
    return sortedList
}

/**
 * Returns a sorted list of all elements
 */
public fun FloatArray.toSortedList(): List<Float> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList)
    return sortedList
}

/**
 * Returns a sorted list of all elements
 */
public fun IntArray.toSortedList(): List<Int> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList)
    return sortedList
}

/**
 * Returns a sorted list of all elements
 */
public fun LongArray.toSortedList(): List<Long> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList)
    return sortedList
}

/**
 * Returns a sorted list of all elements
 */
public fun ShortArray.toSortedList(): List<Short> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList)
    return sortedList
}

/**
 * Returns a sorted list of all elements
 */
public fun <T : Comparable<T>> Iterable<T>.toSortedList(): List<T> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList)
    return sortedList
}

/**
 * Returns a sorted list of all elements
 */
public fun <T : Comparable<T>> Sequence<T>.toSortedList(): List<T> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList)
    return sortedList
}


deprecated("Migrate to using Sequence<T> and respective functions")
/**
 * Returns a sorted list of all elements
 */
public fun <T : Comparable<T>> Stream<T>.toSortedList(): List<T> {
    val sortedList = toArrayList()
    java.util.Collections.sort(sortedList)
    return sortedList
}

/**
 * Returns a sorted list of all elements, ordered by results of specified *order* function.
 */
public fun <T, V : Comparable<V>> Array<out T>.toSortedListBy(order: (T) -> V): List<T> {
    val sortedList = toArrayList()
    val sortBy: Comparator<T> = compareBy(order)
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
}

/**
 * Returns a sorted list of all elements, ordered by results of specified *order* function.
 */
public fun <V : Comparable<V>> BooleanArray.toSortedListBy(order: (Boolean) -> V): List<Boolean> {
    val sortedList = toArrayList()
    val sortBy: Comparator<Boolean> = compareBy(order)
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
}

/**
 * Returns a sorted list of all elements, ordered by results of specified *order* function.
 */
public fun <V : Comparable<V>> ByteArray.toSortedListBy(order: (Byte) -> V): List<Byte> {
    val sortedList = toArrayList()
    val sortBy: Comparator<Byte> = compareBy(order)
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
}

/**
 * Returns a sorted list of all elements, ordered by results of specified *order* function.
 */
public fun <V : Comparable<V>> CharArray.toSortedListBy(order: (Char) -> V): List<Char> {
    val sortedList = toArrayList()
    val sortBy: Comparator<Char> = compareBy(order)
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
}

/**
 * Returns a sorted list of all elements, ordered by results of specified *order* function.
 */
public fun <V : Comparable<V>> DoubleArray.toSortedListBy(order: (Double) -> V): List<Double> {
    val sortedList = toArrayList()
    val sortBy: Comparator<Double> = compareBy(order)
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
}

/**
 * Returns a sorted list of all elements, ordered by results of specified *order* function.
 */
public fun <V : Comparable<V>> FloatArray.toSortedListBy(order: (Float) -> V): List<Float> {
    val sortedList = toArrayList()
    val sortBy: Comparator<Float> = compareBy(order)
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
}

/**
 * Returns a sorted list of all elements, ordered by results of specified *order* function.
 */
public fun <V : Comparable<V>> IntArray.toSortedListBy(order: (Int) -> V): List<Int> {
    val sortedList = toArrayList()
    val sortBy: Comparator<Int> = compareBy(order)
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
}

/**
 * Returns a sorted list of all elements, ordered by results of specified *order* function.
 */
public fun <V : Comparable<V>> LongArray.toSortedListBy(order: (Long) -> V): List<Long> {
    val sortedList = toArrayList()
    val sortBy: Comparator<Long> = compareBy(order)
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
}

/**
 * Returns a sorted list of all elements, ordered by results of specified *order* function.
 */
public fun <V : Comparable<V>> ShortArray.toSortedListBy(order: (Short) -> V): List<Short> {
    val sortedList = toArrayList()
    val sortBy: Comparator<Short> = compareBy(order)
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
}

/**
 * Returns a sorted list of all elements, ordered by results of specified *order* function.
 */
public fun <T, V : Comparable<V>> Iterable<T>.toSortedListBy(order: (T) -> V): List<T> {
    val sortedList = toArrayList()
    val sortBy: Comparator<T> = compareBy(order)
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
}

/**
 * Returns a sorted list of all elements, ordered by results of specified *order* function.
 */
public fun <T, V : Comparable<V>> Sequence<T>.toSortedListBy(order: (T) -> V): List<T> {
    val sortedList = toArrayList()
    val sortBy: Comparator<T> = compareBy(order)
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
}


deprecated("Migrate to using Sequence<T> and respective functions")
/**
 * Returns a sorted list of all elements, ordered by results of specified *order* function.
 */
public fun <T, V : Comparable<V>> Stream<T>.toSortedListBy(order: (T) -> V): List<T> {
    val sortedList = toArrayList()
    val sortBy: Comparator<T> = compareBy(order)
    java.util.Collections.sort(sortedList, sortBy)
    return sortedList
}

