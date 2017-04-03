package com.jonlatane.libharmony

@Suppress("Unused_Parameter")
@native fun parseInt(s: String): Int = noImpl

fun String.toInt() = parseInt(this)

fun Char.isDigit(): Boolean {
    try {
        parseInt(this.toString())
        return true
    } catch(t: Throwable) {
        return false
    }
}