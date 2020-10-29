import java.lang.IllegalArgumentException

// Params are appended
open class ConfigBuilderA() {

    protected val params = mutableListOf<String>()

    fun param(s: String) {
        params.add(s)
    }

    constructor(init: ConfigBuilderA.() -> Unit): this() {
        init()
    }

    open fun build(): String {
        return params.joinToString(" ")
    }
}

// Params are prepended
open class ConfigBuilderB() {

    protected val params = mutableListOf<String>()

    fun param(s: String) {
        params.add(0, s)
    }

    constructor(init: ConfigBuilderB.() -> Unit): this() {
        init()
    }

    fun build(): String {
        return params.joinToString(" ")
    }
}

// Only preserves last param
open class ConfigBuilderC() {

    protected var params = ""

    fun param(s: String) {
        params = s
    }

    constructor(init: ConfigBuilderC.() -> Unit): this() {
        init()
    }

    fun build(): String {
        return params
    }
}

// Requires at least 3 params
class ConfigBuilderD() : ConfigBuilderA() {

    constructor(init: ConfigBuilderD.() -> Unit): this() {
        init()
    }

    override fun build(): String {
        if (params.size < 3) {
            throw IllegalArgumentException("Must have 3 params")
        }
        else {
            return params.joinToString(" ")
        }
    }
}

class Params(val a: String, val b: String, val c: String, vararg val rest: String)

data class ConfigD(val params: Params) {
    fun build(): String {
        return (arrayOf(params.a, params.b, params.c) + params.rest).joinToString(" ")
    }
}


// Requires either an "a" or "b" but not both
open class ConfigBuilderE() {

    var a: String? = null
    var b: String? = null

    fun a(s: String) {
        a = s
    }

    fun b(s: String) {
        b = s
    }

    constructor(init: ConfigBuilderE.() -> Unit): this() {
        init()
    }

    fun build(): String {
        return a?.let {
            if (b == null) {
                it
            }
            else {
                null
            }
        } ?: b?.let {
            if (a == null) {
                it
            }
            else {
                null
            }
        } ?: throw IllegalArgumentException("You must set either a or b")
    }
}

sealed class Either<L, R> {
    class Left<L, R>(val left: L) : Either<L, R>()
    class Right<L, R>(val right: R) : Either<L, R>()

    companion object {
        fun <L, R> left(l: L) = Left<L, R>(l)
        fun <L, R> right(r: R) = Right<L, R>(r)
    }
}

data class ConfigE(val aOrB: Either<String, String>) {
    fun build() = when(aOrB) {
        is Either.Left -> "left: ${aOrB.left}"
        is Either.Right -> "right: ${aOrB.right}"
    }
}
