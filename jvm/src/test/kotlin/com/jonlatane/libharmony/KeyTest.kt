package com.jonlatane.libharmony

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by jonlatane on 3/31/17.
 */
class KeyTest {
    @Test
    fun getDegree() {
        assertEquals(0,  Keys.CMajor[1])
        assertEquals(2,  Keys.CMajor[2])
        assertEquals(4,  Keys.CMajor[3])
        assertEquals(5,  Keys.CMajor[4])
        assertEquals(7,  Keys.CMajor[5])
        assertEquals(9,  Keys.CMajor[6])
        assertEquals(11, Keys.CMajor[7])
    }
    @Test
    fun degreeOf() {
        // C Major: C is the first scale degree
        assertEquals(1 to 1, Keys.CMajor.degreeOf(0))
        // Db is between first and second degrees
        assertEquals(1 to 2, Keys.CMajor.degreeOf(1))
        // D is the second degree
        assertEquals(2 to 2, Keys.CMajor.degreeOf(2))
        // Eb is between second and third
        assertEquals(2 to 3, Keys.CMajor.degreeOf(3))
        // E is the third
        assertEquals(3 to 3, Keys.CMajor.degreeOf(4))
    }
    @Test
    fun getNoteName() {
        assertEquals("C", Keys.CMajor.getNoteName(0))
        assertEquals("D$FLAT", Keys.CMajor.getNoteName(1))
        assertEquals("D", Keys.CMajor.getNoteName(2))
        assertEquals("E$FLAT", Keys.CMajor.getNoteName(3))
        assertEquals("E", Keys.CMajor.getNoteName(4))
        assertEquals("B", Keys.CMajor.getNoteName(11))

        assertEquals("D$FLAT", Keys.DbMajor.getNoteName(1))
        assertEquals("E$FLAT$FLAT", Keys.DbMajor.getNoteName(2))
    }
    @Test
    fun cMajorInCMajor() {
        val pitch1 = Pitch("C4")
        val pitch2 = Pitch("E4")
        val pitch3 = Pitch("G4")
        val chord = Chord(pitch1, pitch2, pitch3)
        val data = Keys.CMajor.getRootLikelihoodsAndNames(chord)
        
        println(data)
    }
}