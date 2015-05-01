// IS_APPLICABLE: false
fun foo(p: List<String?>): String? {
    val v = p[0]
    <caret>if (v == null) return v
    return ""
}