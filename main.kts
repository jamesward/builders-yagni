#!/bin/bash

//usr/bin/env echo '
/**** BOOTSTRAP kscript ****\'>/dev/null
command -v kscript >/dev/null 2>&1 || curl -L "https://git.io/fpF1K" | bash 1>&2
exec kscript $0 "$@"
\*** IMPORTANT: Any code including imports and annotations must come after this line ***/

//INCLUDE stuff.kt
//KOTLIN_OPTS -J-ea


val configBuilderA = ConfigBuilderA {
    param("foo")
    param("bar")
}

assert(configBuilderA.build() == "foo bar")


val configBuilderB = ConfigBuilderB {
    param("foo")
    param("bar")
}

assert(configBuilderB.build() == "bar foo")


val configBuilderC = ConfigBuilderC {
    param("foo")
    param("bar")
}

assert(configBuilderC.build() == "bar")

val configBuilderD = ConfigBuilderD {
    param("foo")
    param("bar")
}

assert(try { configBuilderD.build(); false } catch (e: IllegalArgumentException) { true } )


val configD = ConfigD(
    // invalid to construct: Params("foo", "bar")
    Params("foo", "bar", "baz")
)

assert(configD.build() == "foo bar baz")



val configBuilderE1 = ConfigBuilderE()

assert(try { configBuilderE1.build(); false } catch (e: IllegalArgumentException) { true } )

val configBuilderE2 = ConfigBuilderE {
    a("foo")
    b("bar")
}

assert(try { configBuilderE2.build(); false } catch (e: IllegalArgumentException) { true } )

val configBuilderE3 = ConfigBuilderE {
    a("foo")
}

assert(configBuilderE3.build() == "foo")

val configBuilderE4 = ConfigBuilderE {
    b("bar")
}

assert(configBuilderE4.build() == "bar")


val configE = ConfigE(
    Either.left("foo")
)

assert(configE.build() == "left: foo")
