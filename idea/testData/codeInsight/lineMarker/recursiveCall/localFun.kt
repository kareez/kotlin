fun f(a: Int) {
    run(fun () {
        f(a - 1)
    })
}

fun ff(a: Int) {
    run1 {
        ff(a - 1)
    }
}

inline fun <T> run1(noinline f: () -> T): T { }

