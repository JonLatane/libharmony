package com.jonlatane.libharmony

/**
 * The first function below is about going from enharmonic to tone - it's dead easy. The inverse
 * is what the rest of the library is about :) 
 *
 * Created by jonlatane on 12/17/16.
 */

data class Pitch(
        val tone: Int,
        val enharmonic: String? = null
) {
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
    companion object {
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
                    result += 12 * (enharmonic[i].toInt() - 4)
                }
                i += 1
            }
            return result
        }
    }
}

interface ImmutablePitchSet : Set<Pitch>
open class PitchSet(
        val modulus: Modulus? = null,
        vararg elements: Pitch
) : MutableSet<Pitch> by mutableSetOf(*elements), ImmutablePitchSet {
    fun add(tone: Int): Boolean {
        return add(Pitch(tone))
    }
    
    fun contains(tone: Int): Boolean {
        return filter {it.tone == tone}.isNotEmpty()
    }


    fun lower(tone: Int): Int? {
        if(modulus == null) {
            return filter {it.tone < tone }.maxBy { it.tone }?.tone
        } else {
            return (
                filter { it.tone % modulus < tone % modulus }
                        .maxBy { it.tone % modulus } ?:
                filter { it.tone % modulus < (tone + modulus.octaveSteps) % modulus }
                        .maxBy { it.tone % modulus }
            )?.tone
        }
    }

    fun floor(tone: Int): Int? {
        if(modulus == null) {
            return filter {it.tone <= tone }.maxBy { it.tone }?.tone
        } else {
            return (
                    filter { it.tone % modulus <= tone % modulus }
                            .maxBy { it.tone % modulus } ?:
                    filter { it.tone % modulus <= (tone + modulus.octaveSteps) % modulus }
                            .maxBy { it.tone % modulus }
                    )?.tone
        }
    }

    fun ceiling(tone: Int): Int? {
        if(modulus == null) {
            return filter {it.tone >= tone }.maxBy { it.tone }?.tone
        } else {
            return (
                    filter { it.tone % modulus >= tone % modulus }
                            .maxBy { it.tone % modulus } ?:
                    filter { it.tone % modulus >= (tone + modulus.octaveSteps) % modulus }
                            .maxBy { it.tone % modulus }
                    )?.tone
        }
    }


    fun tailSet(tone: Int): PitchSet {
        return PitchSet(modulus, *filter { it.tone > tone }.sortedBy { it.tone }.toTypedArray())
    }

    companion object {
        val REST: ImmutablePitchSet = PitchSet()
    }
}