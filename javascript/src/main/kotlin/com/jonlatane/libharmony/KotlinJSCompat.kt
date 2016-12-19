package com.jonlatane.libharmony

@native fun parseInt(s: String): Int = noImpl

@native fun String.toInt() = parseInt(this)

@native fun Char.isDigit(): Boolean {
    try {
        parseInt(this.toString())
        return true
    } catch(t: Throwable) {
        return false
    }
}