import A.Nested

class A {
    class Nested {
        companion object {
            fun invoke(i: Int) = i
        }
    }
}

fun box() = if (Nested(42) == 42) "OK" else "fail"
