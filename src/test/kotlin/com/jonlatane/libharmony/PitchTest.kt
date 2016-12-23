package com.jonlatane.libharmony

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by jonlatane on 12/23/16.
 */
class PitchTest {

    @Test
    fun getTone() {
        mapOf(
                "C4" to 0,
                "B#3" to 0
        ).forEach {
            println("Testing ${it.key}...")
            assertEquals(Pitch.getTone(it.key), it.value)
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