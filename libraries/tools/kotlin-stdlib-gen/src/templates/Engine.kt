package templates

import templates.Family.*
import templates.Family.Collections
import java.io.StringReader
import java.util.*
import kotlin.properties.Delegates

enum class Family {
    Sequences
    Iterables
    Collections
    Lists
    Maps
    ArraysOfObjects
    ArraysOfPrimitives
    Strings
    RangesOfPrimitives
    ProgressionsOfPrimitives
    Primitives
    Generic

    val isPrimitiveSpecialization: Boolean by Delegates.lazy { this in listOf(ArraysOfPrimitives, RangesOfPrimitives, ProgressionsOfPrimitives, Primitives) }
}

enum class PrimitiveType(val name: String) {
    Boolean : PrimitiveType("Boolean")
    Byte : PrimitiveType("Byte")
    Char : PrimitiveType("Char")
    Short : PrimitiveType("Short")
    Int : PrimitiveType("Int")
    Long : PrimitiveType("Long")
    Float : PrimitiveType("Float")
    Double : PrimitiveType("Double")
}



class GenericFunction(val signature: String, val keyword: String = "fun") : Comparable<GenericFunction> {
    val defaultFamilies = array(Iterables, Sequences, ArraysOfObjects, ArraysOfPrimitives, Strings)
    val defaultPrimitives = PrimitiveType.values()
    val numericPrimitives = array(PrimitiveType.Int, PrimitiveType.Long, PrimitiveType.Byte, PrimitiveType.Short, PrimitiveType.Double, PrimitiveType.Float)

    var toNullableT: Boolean = false

    var defaultInline = false
    var receiverAsterisk = false
    val inlineFamilies = HashMap<Family, Boolean>()

    val buildFamilies = LinkedHashSet(defaultFamilies.toList())
    private val buildPrimitives = LinkedHashSet(defaultPrimitives.toList())

    var deprecate: String = ""
    val deprecates = hashMapOf<Family, String>()

    var doc: String = ""
    val docs = HashMap<Family, String>()

    var defaultBody: String = ""
    val bodies = HashMap<Family, String>()
    val customPrimitiveBodies = HashMap<Pair<Family, PrimitiveType>, String>()

    var defaultReturnType = ""
    val returnTypes = HashMap<Family, String>()

    val typeParams = ArrayList<String>()

    fun body(vararg families: Family, b: () -> String) {
        if (families.isEmpty())
            defaultBody = b()
        else {
            for (f in families) {
                include(f)
                bodies[f] = b()
            }
        }
    }

    fun bodyForTypes(family: Family, vararg primitiveTypes: PrimitiveType, b: () -> String) {
        include(family)
        for (f in primitiveTypes) {
            customPrimitiveBodies.put(family to f, b())
        }
    }

    fun doc(vararg families: Family, b: () -> String) {
        if (families.isEmpty())
            doc = b()
        else {
            for (f in families) {
                docs[f] = b()
            }
        }
    }

    fun deprecate(vararg families: Family, b: () -> String) {
        if (families.isEmpty())
            deprecate = b()
        else {
            for (f in families) {
                deprecates[f] = b()
            }
        }
    }

    fun returns(vararg families: Family, b: () -> String) {
        if (families.isEmpty())
            defaultReturnType = b()
        else {
            for (f in families) {
                returnTypes[f] = b()
            }
        }
    }

    fun returns(r: String) {
        defaultReturnType = r
    }

    fun typeParam(t: String) {
        typeParams.add(t)
    }

    fun receiverAsterisk(v: Boolean) {
        receiverAsterisk = v
    }

    fun inline(value: Boolean, vararg families: Family) {
        if (families.isEmpty())
            defaultInline = value
        else
            for (f in families)
                inlineFamilies.put(f, value)
    }

    fun exclude(vararg families: Family) {
        buildFamilies.removeAll(families.toList())
    }

    fun only(vararg families: Family) {
        buildFamilies.clear()
        buildFamilies.addAll(families.toList())
    }

    fun only(vararg primitives: PrimitiveType) {
        buildPrimitives.clear()
        buildPrimitives.addAll(primitives.toList())
    }

    fun include(vararg families: Family) {
        buildFamilies.addAll(families.toList())
    }

    fun exclude(vararg p: PrimitiveType) {
        buildPrimitives.removeAll(p.toList())
    }

    fun include(vararg p: PrimitiveType) {
        buildPrimitives.addAll(p.toList())
    }


    fun build(vararg families: Family = Family.values()): String {
        val builder = StringBuilder()
        for (family in families.sortBy { it.name() }) {
            if (buildFamilies.contains(family))
                build(builder, family)
        }
        return builder.toString()
    }

    fun build(builder: StringBuilder, f: Family) {
        if (f.isPrimitiveSpecialization) {
            for (primitive in buildPrimitives.sortBy { it.name() })
                build(builder, f, primitive)
        } else {
            build(builder, f, null)
        }
    }

    fun build(builder: StringBuilder, f: Family, primitive: PrimitiveType?) {
        if (f == Sequences) {
            val text = StringBuilder {
                doBuild(this, f, primitive)
            }.toString()
            builder.append(text)
            builder.appendln()
            if (deprecates[f] == null && deprecate.isEmpty())
                builder.appendln("deprecated(\"Migrate to using Sequence<T> and respective functions\")")
            val streamText = text
                    .replace("Sequence", "Stream")
                    .replace("sequence", "stream")
                    .replace("MultiStream", "Multistream")
            builder.append(streamText)
        } else
            doBuild(builder, f, primitive)
    }

    fun doBuild(builder: StringBuilder, f: Family, primitive: PrimitiveType?) {
        val returnType = returnTypes[f] ?: defaultReturnType
        if (returnType.isEmpty())
            throw RuntimeException("No return type specified for $signature")

        val isAsteriskOrT = if (receiverAsterisk) "*" else "T"
        val receiver = when (f) {
            Iterables -> "Iterable<$isAsteriskOrT>"
            Collections -> "Collection<$isAsteriskOrT>"
            Lists -> "List<$isAsteriskOrT>"
            Maps -> "Map<K, V>"
            Sequences -> "Sequence<$isAsteriskOrT>"
            ArraysOfObjects -> "Array<$isAsteriskOrT>"
            Strings -> "String"
            ArraysOfPrimitives -> primitive?.let { it.name() + "Array" } ?: throw IllegalArgumentException("Primitive array should specify primitive type")
            RangesOfPrimitives -> primitive?.let { it.name() + "Range" } ?: throw IllegalArgumentException("Primitive range should specify primitive type")
            ProgressionsOfPrimitives -> primitive?.let { it.name() + "Progression" } ?: throw IllegalArgumentException("Primitive progression should specify primitive type")
            Primitives -> primitive?.let { it.name } ?: throw IllegalArgumentException("Primitive should specify primitive type")
            Generic -> "T"
            else -> throw IllegalStateException("Invalid family")
        }


        fun String.renderType(): String {
            val t = StringTokenizer(this, " \t\n,:()<>?.", true)
            val answer = StringBuilder()

            while (t.hasMoreTokens()) {
                val token = t.nextToken()
                answer.append(when (token) {
                                  "SELF" -> if (receiver == "Array<T>") "Array<out T>" else receiver
                                  "PRIMITIVE" -> primitive?.name() ?: token
                                  "SUM" -> {
                                      when (primitive) {
                                          PrimitiveType.Byte, PrimitiveType.Short, PrimitiveType.Char -> "Int"
                                          else -> primitive
                                      }
                                  }
                                  "ZERO" -> when (primitive) {
                                      PrimitiveType.Double -> "0.0"
                                      PrimitiveType.Float -> "0.0f"
                                      else -> "0"
                                  }
                                  "ONE" -> when (primitive) {
                                      PrimitiveType.Double -> "1.0"
                                      PrimitiveType.Float -> "1.0f"
                                      PrimitiveType.Long -> "1.toLong()"
                                      else -> "1"
                                  }
                                  "-ONE" -> when (primitive) {
                                      PrimitiveType.Double -> "-1.0"
                                      PrimitiveType.Float -> "-1.0f"
                                      PrimitiveType.Long -> "-1.toLong()"
                                      else -> "-1"
                                  }
                                  "TCollection" -> {
                                      when (f) {
                                          Strings -> "Appendable"
                                          else -> "MutableCollection<in T>".renderType()
                                      }
                                  }
                                  "T" -> {
                                      when (f) {
                                          Generic -> "T"
                                          Strings -> "Char"
                                          Maps -> "Map.Entry<K, V>"
                                          else -> primitive?.name() ?: token
                                      }
                                  }
                                  "TRange" -> {
                                      when (f) {
                                          Generic -> "Range<T>"
                                          else -> primitive!!.name + "Range"
                                      }
                                  }
                                  "TProgression" -> {
                                      when (f) {
                                          Generic -> "Progression<out T>"
                                          else -> primitive!!.name + "Progression"
                                      }
                                  }
                                  else -> token
                              })
            }

            return answer.toString()
        }

        fun effectiveTypeParams(): List<String> {
            // TODO: Model for type parameter
            val types = ArrayList(typeParams)
            if (f == Generic) {
                // ensure type parameter T, if it's not added to typeParams before
                if (!types.any { it == "T" || it.startsWith("T:")}) {
                    types.add("T")
                }
                return types
            }
            else if (primitive == null && f != Strings) {
                val implicitTypeParameters = receiver.dropWhile { it != '<' }.drop(1).filterNot { it == ' ' }.takeWhile { it != '>' }.split(",")
                for (implicit in implicitTypeParameters.reverse()) {
                    if (implicit != "*" && !types.any { it.startsWith(implicit) || it.startsWith("reified " + implicit) }) {
                        types.add(0, implicit)
                    }
                }

                return types
            } else {
                // primitive type arrays should drop constraints
                return typeParams.filter { !it.startsWith("T") }
            }
        }

        fun getPlatformName(primitive: PrimitiveType): String {
            val name = signature.substringBefore('(').trim()
            return "${name}Of${primitive.name}"
        }

        val methodDoc = docs[f] ?: doc
        if (methodDoc != "") {
            builder.append("/**\n")
            StringReader(methodDoc).forEachLine {
                val line = it.trim()
                if (!line.isEmpty()) {
                    builder.append(" * ").append(line).append("\n")
                }
            }
            builder.append(" */\n")
        }
        val deprecated = deprecates[f] ?: deprecate
        if (deprecated != "") {
            builder.append("deprecated(\"$deprecated\")\n")
        }

        // heuristics to define that platform overloads are likely to clash
        if (!f.isPrimitiveSpecialization && primitive != null && ((returnType == returnType.renderType() && returnType != "T") || returnType == "SUM"))
            builder.append("platformName(\"${getPlatformName(primitive!!)}\")\n")

        builder.append("public ")
        if (inlineFamilies[f] ?: defaultInline)
            builder.append("inline ")

        builder.append("$keyword ")

        val types = effectiveTypeParams()
        if (!types.isEmpty()) {
            builder.append(types.join(separator = ", ", prefix = "<", postfix = "> ").renderType())
        }

        val receiverType = (when (receiver) {
            "Array<T>" -> if (toNullableT) "Array<out T?>" else "Array<out T>"
            else -> if (toNullableT) receiver.replace("T>", "T?>") else receiver
        }).renderType()


        builder.append(receiverType)
        builder.append(".${signature.renderType()}: ${returnType.renderType()}")
        if (keyword == "fun") builder.append(" {")

        val body = (customPrimitiveBodies[f to primitive] ?: bodies[f] ?: defaultBody).trim("\n")
        val indent: Int = body.takeWhile { it == ' ' }.length()

        builder.append('\n')
        StringReader(body).forEachLine {
            var count = indent
            val line = it.dropWhile { count-- > 0 && it == ' ' }.renderType()
            if (line.isNotEmpty()) {
                builder.append("    ").append(line)
                builder.append("\n")
            }
        }
        if (keyword == "fun") builder.append("}\n")
        builder.append("\n")
    }

    public override fun compareTo(other: GenericFunction): Int = this.signature.compareTo(other.signature)
}

fun String.trimTrailingSpaces(): String {
    var answer = this;
    while (answer.endsWith(' ') || answer.endsWith('\n')) answer = answer.substring(0, answer.length() - 1)
    return answer
}

fun f(signature: String, init: GenericFunction.() -> Unit): GenericFunction {
    val gf = GenericFunction(signature)
    gf.init()
    return gf
}

fun pval(signature: String, init: GenericFunction.() -> Unit): GenericFunction {
    val gf = GenericFunction(signature, "val")
    gf.init()
    return gf
}

fun pvar(signature: String, init: GenericFunction.() -> Unit): GenericFunction {
    val gf = GenericFunction(signature, "var")
    gf.init()
    return gf
}
