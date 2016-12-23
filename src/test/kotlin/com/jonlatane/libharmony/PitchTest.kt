package com.jonlatane.libharmony

import org.junit.Assert.*
import org.junit.Test
import com.jonlatane.libharmony.FLAT

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
            println("Testing ${it.key}...")
            assertEquals(Pitch.getTone(it.key), it.value)
        }
    }
    @Test
    fun getEnharmonics() {
        mapOf(
                0 to setOf("C4", "B${SHARP}3", "D$FLAT${FLAT}4")
        ).forEach { 
            println("Testing ${it.key} -> ${it.value}...")
            assertEquals(Pitch.getEnharmonics(it.key), it.value)
        }
    }
    
    @Test
    fun compareTo() {

    }

    @Test
    fun compareTo1() {

    }

    @Test
    fun minus() {

    }

    @Test
    fun plus() {

    }

    @Test
    fun invariant() {

    }

}