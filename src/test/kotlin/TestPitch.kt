import com.jonlatane.libharmony.Pitch

/**
 * Created by jonlatane on 12/18/16.
 */

fun testGetTone() {
    mapOf(
            "C4" to 0,
            "B3" to -1,
            "A#3" to -2
    ).forEach { 
        assert(Pitch.getTone(it.key) == it.value) { "Failed on ${it.key}" }
    }
}