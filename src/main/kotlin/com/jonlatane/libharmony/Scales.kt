package com.jonlatane.libharmony

/**
 * Created by jonlatane on 12/18/16.
 */

class MajorScale(root: Pitch) : Scale(root) {
    init {
        add(root)
        add(root + 2)
        add(root + 4)
        add(root + 5)
        add(root + 7)
        add(root + 9)
        add(root + 11)
        println("MajorScale")
    }

    override val isMajor = true
}

class NaturalMinorScale(root: Pitch) : Scale(root) {

    init {
        add(root)
        add(root + 2)
        add(root + 3)
        add(root + 5)
        add(root + 7)
        add(root + 8)
        add(root + 10)
    }

    override val isMinor = true
}

class HarmonicMinorScale(root: Pitch) : Scale(root) {

    init {
        add(root)
        add(root + 2)
        add(root + 3)
        add(root + 5)
        add(root + 7)
        add(root + 8)
        add(root + 11)
    }

    override val isMinor: Boolean
        get() = false

    companion object {
        private val serialVersionUID = 1292993657552799082L
    }
}

class MelodicMinorScale(root: Pitch) : Scale(root) {
    override val descendingVersion: Scale = Scale(root)
    init {
        add(root)
        add(root + 2)
        add(root + 3)
        add(root + 5)
        add(root + 7)
        add(root + 9)
        add(root + 11)

        descendingVersion.add(root)
        descendingVersion.add(root + 2)
        descendingVersion.add(root + 3)
        descendingVersion.add(root + 5)
        descendingVersion.add(root + 7)
        descendingVersion.add(root + 8)
        descendingVersion.add(root + 11)
    }

    override val isMinor = true
}

class ChromaticScale(root: Pitch) : Scale(root) {
    init {
        add(root)
        for(i in (1..11)) {
            add(root + i)
        }
    }
}