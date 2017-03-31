package com.jonlatane.libharmony

import com.jonlatane.libharmony.Chord.Companion.NO_CHORD
import com.jonlatane.libharmony.Keys.CMajor
import org.junit.Assert
import org.junit.Test

/**
 * Created by jonlatane on 12/26/16.
 */
class ChordTest {
    @Test
    fun noChord() {
        println("Testing $NO_CHORD")
        Assert.assertTrue(Chord.NO_CHORD != Chord(Pitch(0)))
    }

    @Test
    fun getChordByName() {
        mapOf(
                "C" to Chord(Pitch(0), Pitch(4), Pitch(7))
        ).forEach { 
            print("Testing ${it.key} -> ${it.value}... ")
            println(Chord.getChordByName(it.key))
        }
    }
    @Test
    fun getTone() {
        mapOf(
                (Chord(Pitch(0), Pitch(4), Pitch(7))) to "C"
        ).forEach {
            print("Testing ${it.key} -> ${it.value}... ")
            //val actual = Pitch.getTone(it.key)
            println()
            //println("Got $actual")
            //Assert.assertEquals(actual, it.value)
        }
    }
}