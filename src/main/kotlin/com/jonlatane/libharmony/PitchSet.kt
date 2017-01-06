package com.jonlatane.libharmony

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
    
    open override fun toString(): String {
        var result = "("
        var first = true
        for(pitch in this) {
            if(!first) result += ','
            first = false
            result += pitch
        }
        return  result + ')'
    }

    companion object {
        val REST: ImmutablePitchSet = PitchSet()
    }
}