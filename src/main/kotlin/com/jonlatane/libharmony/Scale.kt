package com.jonlatane.libharmony

import com.jonlatane.libharmony.Modulus.Companion.HEPTATONIC
import com.jonlatane.libharmony.Modulus.Companion.TWELVETONE

/**
 * Scales work like Chords but should contain more notes.

 * This class may construct scales from semantic information
 * in Android Resources
 */
open class Scale(root: Pitch) : Chord(root) {
    /**
     * May be overridden by melodic minor or bebop scales.  Otherwise return this.

     * @return
     */
    open val descendingVersion: Scale get() { return this }

    /**
     * Returns the chromatic note for the provided scale degree of this Scale.
     * For C Major, the parameters 1, 2, 3, 4, 5, 6, 7 return 0, 2, 4, 5, 7, 9, 11.
     * 
     * For Chromatic, 

     * @param tone
     * *
     * @return
     */
    fun getDegree(tone: Int): Int {
        var i = HEPTATONIC.mod(tone)
        if (i == 0) {
            i = 7
        }
        var currentDegree = 1
        filter { Pitch(it.tone) > root }
        var itr = tailSet(root.tone).iterator()
        while (true) {
            if (itr.hasNext()) {
                if (currentDegree == i) {
                    return itr.next().tone
                } else {
                    itr.next()
                }
            } else {
                itr = iterator()
            }
            currentDegree = HEPTATONIC.mod(currentDegree + 1)
            if (currentDegree == 0) {
                currentDegree = 7
            }
        }
    }

    /**
     * Returns a pair of scale degrees to the left and right of tone.  If tone is in this Scale,
     * the two results will be the same.  If not, each will be the scale degrees to the right
     * and left of tone.

     * @param tone
     * *
     * @return a Pair of numbers between 1 and size()
     */
    fun degreeOf(tone: Int): Pair<Int, Int> {
        val i = TWELVETONE.mod(tone)

        var upper = ceiling(i)
        if (upper == null)
            upper = ceiling(i - 12)
        var lower = floor(i)
        if (lower == null)
            lower = floor(i + 12)
        var chromatic = root.tone
        var degree = 0
        var lowerDegree: Int? = null
        var upperDegree: Int? = null
        while (lowerDegree == null || upperDegree == null) {
            if (contains(chromatic))
                degree += 1
            if (chromatic == lower)
                lowerDegree = degree
            if (chromatic == upper)
                upperDegree = degree
            chromatic += 1

            chromatic = TWELVETONE.mod(chromatic)
        }

        return Pair<Int, Int>(lowerDegree, upperDegree)
    }

    open val isMajor = false

    open val isMinor = false
}
