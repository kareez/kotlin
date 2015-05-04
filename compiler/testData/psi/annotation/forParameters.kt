fun foo() {
    for (@volatile x in z) {}

    for ([ann]) {}
    for (@ in z) {}

    for ((x, private data @ann [ann] y) in x) {}

    for (([ann], x) in pair) {}
}
