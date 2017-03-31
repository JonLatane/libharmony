package com.jonlatane.libharmony.model.music


/**
 * Created by jonlatane on 12/17/16.
 */

/**
 * The musical version of a Modulus is nice in that it preserves enharomic information.
 */

operator fun Int.mod(modulus: Modulus): Int {
    return modulus.mod(this)
}

operator fun Pitch.mod(modulus: Modulus): Pitch {
    return modulus.mod(this)
}

data class Modulus constructor(val octaveSteps: Int = 12) {
    companion object {
        val TWELVETONE = Modulus()
        val HEPTATONIC = Modulus(7)
    }

    /**
     * Return the pitch class of the given note.  For a 12 com.jonlatane.libharmony.model.music.Modulus this means a number 0-11.
     * For heptatonic work, it is 0-6.
     * @param tone a note, where C4 = 0, B4 = 11
     * *
     * @return a number between 0 and octaveSteps - 1
     */
    fun mod(tone: Int): Int {
        var i = tone
        while (i < 0)
            i += octaveSteps
        return i % octaveSteps
    }

    /**
     * Performing a modular operation on a pitch should always return a pitch of the same enharmonic.
     */
    fun mod(pitch: Pitch): Pitch {
        return Pitch(
                mod(pitch.tone), 
                pitch.enharmonic
        )
    }

    /**
     * Return the octave of a given note.  For 0-11, this is 4 (the middle octave),
     * 5 for the next twelve and so on.

     * @param tone a note, where C4 = 0, B4 = 11
     * *
     * @return the octave of the note (4)
     */
    fun octave(tone: Int): Int {
        var i = tone
        var octave = 4
        val n = mod(i)
        while (i < n) {
            i += octaveSteps
            octave -= 1
        }

        while (i > n) {
            i -= octaveSteps
            octave += 1
        }
        return octave
    }

    /**
     * Returns the smallest distance between the two provided numbers
     * @param tone1
     * *
     * @param tone2
     * *
     * @return
     */
    fun distance(tone1: Int, tone2: Int): Int {
        val result1 = mod(tone1 - tone2)
        val result2 = mod(tone2 - tone1)
        return Math.min(result1, result2)
    }
}