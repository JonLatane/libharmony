package com.jonlatane.libharmony.model.music


/**
 * Created by jonlatane on 12/17/16.
 */

object Keys {
    val CMajor = Key(MajorScale(Pitch(0, "C")))
    val CMinor = Key(NaturalMinorScale(Pitch(0, "C")))

    val DbMajor = Key(MajorScale(Pitch(1, "D${FLAT}")))
    val CsMajor = Key(MajorScale(Pitch(1, "C${SHARP}")))
    val CsMinor = Key(NaturalMinorScale(Pitch(1, "C${SHARP}")))

    val DMajor = Key(MajorScale(Pitch(2, "D${FLAT}")))
    val DMinor = Key(NaturalMinorScale(Pitch(2, "D${FLAT}")))

    val EbMajor = Key(MajorScale(Pitch(3, "E${FLAT}")))
    val EbMinor = Key(NaturalMinorScale(Pitch(3, "E${FLAT}")))
    val DsMinor = Key(NaturalMinorScale(Pitch(3, "D${SHARP}")))

    val EMajor = Key(MajorScale(Pitch(4, "E")))
    val EMinor = Key(NaturalMinorScale(Pitch(4, "E")))

    val FMajor = Key(MajorScale(Pitch(5, "F")))
    val FMinor = Key(NaturalMinorScale(Pitch(5, "F")))

    val GbMajor = Key(MajorScale(Pitch(6, "G${FLAT}")))
    val FsMajor = Key(MajorScale(Pitch(6, "F${SHARP}")))
    val FsMinor = Key(NaturalMinorScale(Pitch(6, "F${SHARP}")))

    val GMajor = Key(MajorScale(Pitch(7, "G")))
    val GMinor = Key(NaturalMinorScale(Pitch(7, "G")))

    val AbMajor = Key(MajorScale(Pitch(8, "A${FLAT}")))
    val GsMinor = Key(NaturalMinorScale(Pitch(8, "G${SHARP}")))
    val AbMinor = Key(NaturalMinorScale(Pitch(8, "A${FLAT}")))

    val AMajor = Key(MajorScale(Pitch(9, "A")))
    val AMinor = Key(NaturalMinorScale(Pitch(9, "A")))

    val BbMajor = Key(MajorScale(Pitch(10, "B${FLAT}")))
    val BbMinor = Key(NaturalMinorScale(Pitch(10, "B${FLAT}")))
    val AsMinor = Key(NaturalMinorScale(Pitch(10, "A${SHARP}")))

    val BMajor = Key(MajorScale(Pitch(11, "B")))
    val CbMajor = Key(MajorScale(Pitch(11, "C${FLAT}")))
    val BMinor = Key(NaturalMinorScale(Pitch(11, "B")))
}