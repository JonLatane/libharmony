package com.jonlatane.libharmony

import com.jonlatane.libharmony.Modulus.Companion.HEPTATONIC
import com.jonlatane.libharmony.Modulus.Companion.TWELVETONE

/**
 * A Key is just what we think it is, and is sort of where we tie together
 * all the harmonic things defined at lower levels.  We must always assume a Key
 * is a major or minor scale represented with 0-7 flats or sharps, which occur in
 * circle-of-fifths order.  That is what most of the logic below accomplishes.  This
 * provides us with 30 total keys to write in.

 * @author Jon
 */
class Key(root: Pitch) : Scale(root) {
    constructor(scale: Scale) : this(scale.root) {
        addAll(scale)
    }
    
    fun invariant() {
        if(root.enharmonic == null) throw Throwable("Keys must have a root name.")
    }
    
    init {
        invariant()
    }
    
    /**
     * Gets the note name for the given note using this Key's root name.  Assumes this is a
     * heptatonic key (tone.e., major, minor or modal) that can be represented with only sharps,
     * flats, double sharps and double flats.  Naturals will also be represented if the
     * key assumes a note is flat.

     * Depending on this key, results will change accidentals dynamically.  For instance:

     * Key | A | Ab
     * C   | A | Ab
     * C-  | An| A

     * @param tone
     * *
     * @return
     */
    fun getNoteName(tone: Int): String {
        val i: Int = tone % modulus!!
        var result = ""

        // This method works by careful interlocking of the heptatonic and twelve-tone systems.
        val rootCharIndex = toHeptatonicNumber(root.enharmonic!![0])

        // Note that this is scale degrees and thus is assumed to be heptatonic (as per documentation)
        val p = degreeOf(i)

        if (p.first == p.second) {
            val letterName = toHeptatonicCharacter((rootCharIndex + p.first - 1) % HEPTATONIC)
            result += letterName
            if (i < TWELVE_TONE_INVERSE[letterName]!!) {
                result += FLAT
            }
            if (i > TWELVE_TONE_INVERSE[letterName]!!) {
                result += '#'
            }
        } else {
            // represent it as a sharp/double-sharp/flat
            if (TWELVETONE.distance(i, this[p.first]) < TWELVETONE.distance(i, this[p.second])) {
                val letterName = toHeptatonicCharacter((rootCharIndex + p.first - 1) % HEPTATONIC)
                result += letterName

                // Check for flats
                if (i < TWELVE_TONE_INVERSE[letterName]!!) {
                    result += FLAT
                }

                // Check for sharps/double-sharps
                if (i > TWELVE_TONE_INVERSE[letterName]!!) {
                    result += '#'
                }
                if (i > TWELVE_TONE_INVERSE[letterName]!! + 1) {
                    result += '#'
                }
                //represent as a flat/double-flat/sharp
            } else {
                val letterName = toHeptatonicCharacter((rootCharIndex + p.second - 1) % HEPTATONIC)
                result += letterName

                // Check for sharps
                if (i > TWELVE_TONE_INVERSE[letterName]!!) {
                    result += '#'
                }

                // Check for flats/double flats
                if (i < TWELVE_TONE_INVERSE[letterName]!!) {
                    result += FLAT
                }
                if (i < TWELVE_TONE_INVERSE[letterName]!! - 1) {
                    result += FLAT
                }
            }

        }
        return result
    }

    fun getRootLikelihoodsAndNames(c: Chord): Map<Int, List<String>> {
        val result = mutableMapOf<Int, MutableList<String>>()
        for (n in 0..11) {
            val data = Chord.guessCharacteristic(c, n)
            val name = data.first
            val score = data.second
            //if (n == c.root.tone) {
            //    score += 1000
            //}
            var bucket: MutableList<String>? = result[score]
            if (bucket == null) {
                bucket = mutableListOf<String>()
                result.put(score, bucket)
            }

            val rootName = this.getNoteName(n)
            bucket.add(rootName + name)
        }

        return result
    }
}
