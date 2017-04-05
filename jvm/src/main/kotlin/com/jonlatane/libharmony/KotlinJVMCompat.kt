package com.jonlatane.libharmony

/**
 * Kotlin's JVM standard library does not include a JSName annotation.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION,
        AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.EXPRESSION)
annotation class JsName(val name: String)