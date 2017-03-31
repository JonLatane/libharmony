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
     * @param scaleDegree
     * *
     * @return
     */
    operator fun get(scaleDegree: Int): Int {
        var normalDegree = Modulus(size).mod(scaleDegree)
        if (normalDegree == 0) {
            normalDegree = size
        }
        var currentDegree = 1
        var itr = tailSet(root.tone).iterator()
        while (true) {
            if (itr.hasNext()) {
                if (currentDegree == normalDegree) {
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
        var chromatic = root.tone
        var degree = 0
        var lowerDegree: Int? = null
        while (lowerDegree == null) {
            if (contains(chromatic))
                degree += 1
            if (chromatic == i)
                lowerDegree = degree
            chromatic += 1

            chromatic = TWELVETONE.mod(chromatic)
        }
        
        val lowerDegreeToChromatic = this[lowerDegree]
        val upperDegree = if(lowerDegreeToChromatic == i) lowerDegree else HEPTATONIC.mod(lowerDegree + 1)

        return Pair<Int, Int>(lowerDegree, upperDegree)
    }

    open val isMajor = false

    open val isMinor = false
}
