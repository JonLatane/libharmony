package com.jonlatane.libharmony

import com.jonlatane.libharmony.Modulus.Companion.TWELVETONE
import com.jonlatane.libharmony.Pitch.Companion.getEnharmonics
import com.jonlatane.libharmony.Pitch.Companion.getTone

/**
 * The [Pitch] is the base primitive of libharmony.  It is basically a [Pair]<[Int], [String]?>
 * with the invariant that, if the "enharmonic" (String part) is provided, it must be true
 * that:
 * 
 * * [tone] exactly matches [getTone] of [enharmonic]
 * * [enharmonic] is in the list [getEnharmonics] of [tone]
 *
 * Created by jonlatane on 12/17/16.
 */
data class Pitch(
        val tone: Int,
        val enharmonic: String? = null
) {
    /**
     * Convenience constructor from enharmonic only based on [getTone]
     */
    constructor(enharmonic: String) : this(getTone(enharmonic), enharmonic)
    
    init {
        invariant()
    }
    
    operator fun compareTo(other: Pitch): Int {
        return compareTo(other, false)
    }
    fun compareTo(other: Pitch, enharmonicSensitive: Boolean): Int {
        var result = tone.compareTo(other.tone)
        if(enharmonicSensitive && result == 0) {
            result = enharmonic?.get(0)?.compareTo(other.enharmonic?.get(0) ?: enharmonic[0]) ?: 0
        }
        return result
    }
    operator fun minus(value: Int): Pitch {
        return Pitch(tone - value)
    }
    operator fun plus(value: Int): Pitch {
        return Pitch(tone + value)
    }
    fun invariant() {
        if(enharmonic != null && getTone(enharmonic) != tone) {
            throw Throwable("Pitch enharmonic does not match tone.")
        }
    }

    override fun toString(): String {
        return enharmonic ?: tone.toString()
    }
    companion object {
        /**
         * Consumes a given string [enharmonic], like "Ab3" or "F#".  In the latter case,
         * the system will assume "F#4".
         * 
         * Produces an integer representation of the tone with the baseline "C4" -> 0.
         * So "Bb4" -> 0, "C#4" -> 1, "G4" -> 7, "C5" -> 12.  A partial inverse is implemented
         * in [getEnharmonics].
         * 
         * Suitable for all tones "C0" to "B9" (i.e., no negative octaves or octaves >= 10).
         */
        fun getTone(enharmonic: String): Int {
            if(enharmonic.length > 5) throw Throwable("Too long")
            var result = TWELVE_TONE_INVERSE[enharmonic[0]]!!
            var i = 1
            while (i < enharmonic.length) {
                if (enharmonic[i].isFlat())
                    result -= 1
                else if (enharmonic[i].isSharp())
                    result += 1
                else if (enharmonic[i].isDigit()) {
                    result += 12 * (enharmonic[i].toOctave() - 4)
                }
                i += 1
            }
            return result
        }

        /**
         * A partial inverse of [getTone].
         * 
         * For the tone 0, returns ["C4", "Dbb4", "B#3"].
         * 
         * The main point of the rest of this framework is the question:
         * "when does using B#3 or Dbb4 make more sense to the person reading?"
         */
        fun getEnharmonics(tone: Int): Set<String> {
            val baseTone = tone % TWELVETONE
            val result = mutableListOf<String>()
            TWELVE_TONE_NAMES.mapValues { it.value.toString() }.forEach {
                when((baseTone - it.key) % TWELVETONE) {
                    0 -> result.add(it.value)
                    11 -> result.add(it.value + FLAT)
                    10 -> result.add(it.value + FLAT + FLAT)
                    1 -> result.add(it.value + SHARP)
                    2 -> result.add(it.value + SHARP + SHARP)
                }
            }
            val octave = TWELVETONE.octave(tone)
            return result.map {
                if(!it.startsWith("B$SHARP"))
                    it + octave 
                else
                    it + (octave - 1)
            }.toSet()
        }
    }
}
