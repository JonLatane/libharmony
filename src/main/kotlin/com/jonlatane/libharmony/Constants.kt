package com.jonlatane.libharmony

val FLAT = '\u266D'
val SHARP = '#'
val NATURAL = '\u266E'
val MAJOR = 'M'
val MINOR = '-'
val DIMINISHED = '\u00B0'

fun Char.isFlat(): Boolean {
    return this == FLAT || this == 'b'
}

fun Char.isSharp(): Boolean {
    return this == SHARP || this == '#'
}

fun Char.isMajor() : Boolean {
    return this == MAJOR
}

fun Char.isDiminished(perfect: Boolean = false): Boolean {
    return this == DIMINISHED ||
            (perfect && isFlat())
}

fun Char.toOctave(): Int {
    if(!isDigit()) throw Throwable("toOctave can only be called on digits")
    return toInt() - 48
}

fun Char.isNatural(): Boolean {
    return this == NATURAL
}

fun Char.isMusical(): Boolean {
    return TWELVE_TONE_INVERSE.keys.contains(this)
}

fun Char.toTone(): Int{
    return TWELVE_TONE_INVERSE[this] ?: throw Throwable("Character is not musical")
}

val TWELVE_TONE_NAMES = mapOf(
        0 to 'C',
        2 to 'D',
        4 to 'E',
        5 to 'F',
        7 to 'G',
        9 to 'A',
        11 to 'B'
)

val TWELVE_TONE_INVERSE = TWELVE_TONE_NAMES.invert() +
        TWELVE_TONE_NAMES.mapValues { it.value.toLowerCase() }.invert()

private fun <K,V> Map<K,V>.invert(): Map<V,K> {
    return mapOf(*map {
        it.value to it.key
    }.toTypedArray())
}
