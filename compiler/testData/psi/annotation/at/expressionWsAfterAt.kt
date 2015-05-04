fun foo() {
    return @ ann 1
    return (@ ann 2)

    @ ann foo("")

    @ ann 3 + @ ann 4 * @ ann("") 5 infix @ ann 6

    foo.bar(@ ann fun(x: Int) {

    })

    if (@ ann true || true) {

    }
    else {}

    label@ @ ann while (true) {
        @ ann break@label
    }

    return@label @ ann 1

    // multiline
    @
    ann
    1
}
