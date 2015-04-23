public fun box() : String {
    var i : Int?
    i = 10
    // Postfix increment on a smart cast should work
    val j = i++

    return if (j == 10 && 11 == i) "OK" else "fail j = $j i = $i"
}
