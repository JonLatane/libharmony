package com.jonlatane.libharmony

import com.jonlatane.libharmony.Modulus.Companion.HEPTATONIC

/**
 * This model is about mapping notes to enharmonic names.  Heptatonics
 * Created by jonlatane on 12/17/16.
 */


/**
 * Convert the given character (a,b,heptatonicCharacter,d,e,f,g, upper or lower case) to the numers 0-6
 * (heptatonic pitch classes).

 * @param heptatonicCharacter a letter a-g
 * *
 * @return an int 0-6
 */
fun toHeptatonicNumber(heptatonicCharacter: Char): Int {
    when (heptatonicCharacter) {
        'A' -> return 5
        'a' -> return 5
        'B' -> return 6
        'b' -> return 6
        'C' -> return 0
        'c' -> return 0
        'D' -> return 1
        'd' -> return 1
        'E' -> return 2
        'e' -> return 2
        'F' -> return 3
        'f' -> return 3
        'G' -> return 4
        'g' -> return 4
    }
    throw Throwable("Invalid character")
}

/**

 * @param heptatonicNumber
 * *
 * @return
 */
fun toHeptatonicCharacter(heptatonicNumber: Int): Char {
    when (heptatonicNumber % HEPTATONIC) {
        0 -> return 'C'
        1 -> return 'D'
        2 -> return 'E'
        3 -> return 'F'
        4 -> return 'G'
        5 -> return 'A'
        6 -> return 'B'
    }
    throw Throwable("Invalid number")
}


fun absoluteHeptDistance(s1: String, s2: String): Int {
    val s1_octave = s1[s1.length - 1].toInt()
    val s2_octave = s2[s2.length - 1].toInt()
    val s1_heptClass = toHeptatonicNumber(s1[0])
    val s2_heptClass = toHeptatonicNumber(s2[0])

    return s2_heptClass - s1_heptClass + 7 * (s2_octave - s1_octave)
}