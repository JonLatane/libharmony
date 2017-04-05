package com.jonlatane.libharmony

/**
 * Things that are handy to have in JS for testing purposes.
 * 
 * Created by jonlatane on 4/3/17.
 */
@JsName("getChordsInCMajor")
fun getChordsInCMajor(vararg notes: String): Map<Int, List<String>> {
    val pitches = notes.toList().map {Pitch(it)}.toTypedArray()
    val chord = Chord(*pitches)
    val data = Keys.CMajor.getRootLikelihoodsAndNames(chord)
    return data
}