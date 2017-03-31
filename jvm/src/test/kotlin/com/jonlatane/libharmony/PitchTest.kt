package com.jonlatane.libharmony

import org.junit.Assert.*
import org.junit.Test

class PitchTest {
    @Test
    fun getTone() {
        mapOf(
                "C4" to 0,
                "B#3" to 0,
                "C5" to 12,
                "C3" to -12,
                "C#4" to 1,
                "C#5" to 13,
                "C##" to 2,
                "D4" to 2
        ).forEach {
            print("Testing ${it.key} -> ${it.value}... ")
            val actual = Pitch.getTone(it.key)
            println("Got $actual")
            assertEquals(actual, it.value)
        }
    }
    @Test
    fun getEnharmonics() {
        mapOf(
                0 to setOf("C4", "B${SHARP}3", "D$FLAT${FLAT}4"),
                12 to setOf("C5", "B${SHARP}4", "D$FLAT${FLAT}5"),
                5 to setOf("F4", "E${SHARP}4", "G$FLAT${FLAT}4")
        ).forEach {
            val expected = it.value
            print("Testing ${it.key} -> $expected... ")
            val actual = Pitch.getEnharmonics(it.key)
            println("Got $actual")
            assertTrue(expected.containsAll(actual) && actual.containsAll(expected))
        }
    }
    
    @Test
    fun testCompareTo() {
        mapOf(
                ("C4" to "B#3") to 0,
                ("C4" to "C#4") to -1,
                ("C4" to "B3") to 1
        ).forEach {
            println("Testing ${it.key.first} against ${it.key.second}... ")
            val pitch1 = Pitch(it.key.first)
            val pitch2 = Pitch(it.key.second)
            assertEquals(pitch1.compareTo(pitch2), it.value)
        }
    }

    @Test
    fun testEquals() {

    }

    @Test
    fun testMinus() {

    }

    @Test
    fun testPlus() {

    }

}