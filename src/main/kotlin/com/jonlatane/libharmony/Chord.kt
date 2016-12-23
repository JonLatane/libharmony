/**
 * Created by jonlatane on 12/17/16.
 */

package com.jonlatane.libharmony

import com.jonlatane.libharmony.Modulus.Companion.HEPTATONIC
import com.jonlatane.libharmony.Modulus.Companion.TWELVETONE


/**
 * Chords work much like PitchSets, but they have a com.jonlatane.libharmony.Modulus: the number of tones an octave is divided into. Ex:
 * Given a 12-tone octave, a Chord with both 0 (C4) and 12 (C5) is identical to a Chord with only one of the two.
 * They also have a root.  @Chord.Inversion has a bass as well.

 * In array output from Chords in 1357 format, -1 is used to imply that the element is not present.  Be wary
 * as this is not filtered by @com.jonlatane.libharmony.Modulus.getPitchClassOf .

 * Some of a Chord's more interesting functions include:

 * @schenkerianToInt(String) : convert a b7 to a 10, etc.
 * *
 * @to1357() : convert a Chord to an int[], e.g., [C, E, G, -1, D] for a Cadd9 chord
 * *
 * @getCharacteristic and @guessCharacteristic, for guessing the characteristic of a chord
 */
open class Chord(
        var root: Pitch = Pitch(0)
) : PitchSet(Modulus()) {
    companion object {

        val NO_CHORD = Chord()

        private fun nameTones(c: Chord, root: Int, vararg tones: Int): Pair<String, Int> {
            var name = ""
            var certainty = 0
            for (m in tones) {
                when (m) {
                //color
                    9 -> if (c.contains(root + 2) /*&& !c.contains(root+5) && !c.contains(root+9)*/) {
                        name += "(9)"
                    } else if (c.contains(root + 1)) {
                        name += FLAT + "9"
                    } else if (c.contains(root + 3) && c.contains(root + 4)) {
                        name += "#9"
                    }
                //only look for flat 9 (for sus2/sus24 chords)
                    -9 -> if (c.contains(root + 1)) {
                        name += FLAT + "9"
                    }

                //color
                    11 -> if (c.contains(root + 5) /*&& !c.contains(root+9)*/) {
                        name += "(11)"
                    } else if (c.contains(root + 6) && (c.contains(root + 7) || c.contains(root + 8) && c.contains(root + 4))) {
                        name += "#11"
                    } // flat 11s are impossible as they will be seen as M3s
                //only look for sharp 11
                    -11 -> if (c.contains(root + 6)) {
                        name += "#11"
                    }

                    6 -> if (c.contains(root + 9)) {
                        name += "(6)"
                    } else if (c.contains(root + 8) && c.contains(root + 7)) {
                        name += FLAT + "6"
                        certainty -= 5
                    }
                // only look for flat 6 (for dim chords)
                    -6 -> if (c.contains(root + 8) && c.contains(root + 7)) {
                        name += "(" + FLAT + "6)"
                        certainty -= 5
                    } else if (c.contains(root + 8)) {
                        name += FLAT + "6"
                        certainty -= 5
                    }
                    13 -> if (c.contains(root + 9)) {
                        name += "(13)"
                    } else if (c.contains(root + 8) && c.contains(root + 7)) {
                        name += FLAT + "13"
                    } else if (c.contains(root + 10) && c.contains(root + 11)) {
                        name += "#13"
                    }

                // only for cases where we assume the chord is major like sus/5 chords
                    7 -> if (c.contains(root + 10)) {
                        name += "7"
                    } else if (c.contains(root + 11)) {
                        name += "M7"
                    }

                // in case there is a -5
                    -7 -> if (c.contains(root + 10)) {
                        name += "7" + FLAT + "5"
                    } else if (c.contains(root + 11)) {
                        name += "M7" + FLAT + "5"
                    } else if (c.contains(root + 9)) {
                        name += DIMINISHED + "7"
                    }

                // in case of sus chords
                    5 -> if (c.contains(root + 8) && c.contains(root + 9)) {
                        name += "#5"
                    } else if (c.contains(root + 6)) {
                        name += FLAT + "5"
                    }
                }

            }
            val result = Pair(name, certainty)
            return result
        }

        /**
         * Given the provided root, returns the name (excluding the root as it may be enharmonically named) of the
         * chord and a "score" of how likely it is that the chord is the one guessed.

         * @param c the Chord to be inspected
         * *
         * @param root the root note to inspect from
         * *
         * @return
         */
        internal fun guessCharacteristic(c: Chord, root: Int): Pair<String, Int> {
            var name = ""
            var certainty = 0


            println("Guessing name for chord: " + c.toString() + "R:" + root)

            // Root should be in the chord, but doesn't have to be necessarily.
            if (c.contains(root)) {
                certainty += 12
                println("Root is in the chord!")
            }

            // M3
            if (c.contains(root + 4)) {
                println("M3 is in the chord!")
                certainty += 10
                // M3P5 - major triad present
                if (c.contains(root + 7)) {
                    println("P5 is in the chord!")
                    certainty += 8

                    //M3P5M7 (major 7 type chord)
                    if (c.contains(root + 11)) {
                        certainty += 8
                        //M3P5M7M9
                        if (c.contains(root + 2)) {
                            certainty += 5
                            //M3P5M7M9M13 (11 is optional to call it a 13 chord, as the 11 is an avoid note)
                            if (c.contains(root + 9)) {
                                name += "M13"
                                val p = nameTones(c, root, 9, 11)
                                name += p.first
                                certainty += p.second
                                //M3P5M7M9M11
                            } else if (c.contains(root + 5)) {
                                name += "M11"
                                val p = nameTones(c, root, 9)
                                name += p.first
                                certainty += p.second
                                //M3P5M7M9, no M11, no M13
                            } else {
                                name += "M9"
                                val p = nameTones(c, root, 11, 13)
                                name += p.first
                                certainty += p.second
                            }
                            // M3P5M7, no M9, no M11, no M13
                        } else {
                            name += "M7"
                            val p = nameTones(c, root, 9, 11, 13)
                            name += p.first
                            certainty += p.second
                        }

                        //M3P5m7 (dominant 7)
                    } else if (c.contains(root + 10)) {
                        certainty += 8
                        //M3P5m7M9
                        if (c.contains(root + 2)) {
                            certainty += 5
                            //M3P5m7M9M13 (11 is optional to call it a 13 chord, as the 11 is an avoid note)
                            if (c.contains(root + 9)) {
                                name += "13"
                                val p = nameTones(c, root, 11)
                                name += p.first
                                certainty += p.second
                                //M3P5m7M9M11
                            } else if (c.contains(root + 5)) {
                                name += "11"
                                val p = nameTones(c, root, 9)
                                name += p.first
                                certainty += p.second
                                //M3P5m7M9, no M11, no M13
                            } else {
                                name += "9"
                                val p = nameTones(c, root, 11, 13)
                                name += p.first
                                certainty += p.second
                            }
                            // M3P5m7, no M9, no M11, no M13
                        } else {
                            name += "7"
                            val p = nameTones(c, root, 9, 11, 13)
                            name += p.first
                            certainty += p.second
                        }
                        //M3P5M6 (major 6 with no M7/m7 at all).  Slightly disfavor 6 chords under 7 chords.
                    } else if (c.contains(root + 9)) {
                        certainty += 6
                        name = name + "6"
                        val p = nameTones(c, root, 9, 11)
                        name += p.first
                        certainty += p.second
                        //M3P5 with no (non-flat)6 or any 7
                    } else {
                        //name = name + "M";
                        certainty += 8

                        val p = nameTones(c, root, -6, 9, 11)
                        val colors = p.first
                        certainty += p.second
                        if (colors.length > 0 && (colors.startsWith("#") || colors[0] == FLAT))
                            name += "($colors)"
                        else
                            name += colors
                    }
                    // M3+5 - augmented chord
                } else if (c.contains(root + 8)) {
                    println("+5 is in the chord!")
                    name += "+"
                    certainty += 7

                    //M3+5M7 (major 7+5 type chord)
                    if (c.contains(root + 11)) {
                        certainty += 7
                        //M3+5M7M9
                        if (c.contains(root + 2)) {
                            certainty += 5
                            //M3+5M7M9M13 (11 is optional to call it a 13 chord, as the 11 is an avoid note)
                            if (c.contains(root + 9)) {
                                name += "M13"
                                val p = nameTones(c, root, 9, 11)
                                name += p.first
                                certainty += p.second
                                //M3+5M7M9M11
                            } else if (c.contains(root + 5)) {
                                name += "M11"
                                val p = nameTones(c, root, 9)
                                name += p.first
                                certainty += p.second
                                //M3+5M7M9, no M11, no M13
                            } else {
                                name += "M9"
                                val p = nameTones(c, root, 11, 13)
                                name += p.first
                                certainty += p.second
                            }
                            // M3+5M7, no M9, no M11, no M13
                        } else {
                            name += "M7"
                            val p = nameTones(c, root, 9, 11, 13)
                            name += p.first
                            certainty += p.second
                        }

                        //M3+5m7 (dominant 7+5)
                    } else if (c.contains(root + 10)) {
                        certainty += 6
                        //M3+5m7M9
                        if (c.contains(root + 2)) {
                            certainty += 5
                            //M3+5m7M9M13 (11 is optional to call it a 13 chord, as the 11 is an avoid note)
                            if (c.contains(root + 9)) {
                                name += "13"
                                val p = nameTones(c, root, 9, 11)
                                name += p.first
                                certainty += p.second
                                //M3+5m7M9M11
                            } else if (c.contains(root + 5)) {
                                name += "11"
                                val p = nameTones(c, root, 9)
                                name += p.first
                                certainty += p.second
                                //M3+5m7M9, no M11, no M13
                            } else {
                                name += "9"
                                val p = nameTones(c, root, 11, 13)
                                name += p.first
                                certainty += p.second
                            }
                            // M3+5m7, no M9, no M11, no M13
                        } else {
                            name += "7"
                            val p = nameTones(c, root, 9, 11, 13)
                            name += p.first
                            certainty += p.second
                        }
                        //M3+5M6 (major 6 with no M7/m7 at all)
                    } else if (c.contains(root + 9)) {
                        certainty += 6
                        name = name + "M6"
                        val p = nameTones(c, root, 9, 11)
                        name += p.first
                        certainty += p.second
                        // M3+5 No6or7
                    } else {
                        certainty += 7
                        val p = nameTones(c, root, 9, 11)
                        name += p.first
                        certainty += p.second
                    }
                    // M3-5P11 - major flat five chord. We require a P11 to be present
                    // because normally if there is no perfect 5 we'd rather call this a #11.  This is ugly so make it less certain
                } else if (c.contains(root + 6)) {
                    println("-5 is in the chord!")
                    certainty += 1

                    // M3-5M7 (major 7 type chord)
                    if (c.contains(root + 11)) {
                        certainty += 8
                        // M3-5M7M9
                        if (c.contains(root + 2)) {
                            // M3-5M7M9M13 (11 is optional to call it a 13 chord, as
                            // the 11 is an avoid note)
                            if (c.contains(root + 9)) {
                                name += "M13" + FLAT + "5"
                                val p = nameTones(c, root, 9, 11)
                                name += p.first
                                certainty += p.second
                                // M3-5M7M9M11
                            } else if (c.contains(root + 5)) {
                                name += "M11" + FLAT + "5"
                                val p = nameTones(c, root, 9)
                                name += p.first
                                certainty += p.second
                                // M3-5M7M9, no M11, no M13
                            } else {
                                name += "M9" + FLAT + "5"
                                val p = nameTones(c, root, 11, 13)
                                name += p.first
                                certainty += p.second
                            }
                            // M3-5M7, no M9, no M11, no M13
                        } else {
                            name += "M7" + FLAT + "5"
                            val p = nameTones(c, root, 9, 11, 13)
                            name += p.first
                            certainty += p.second
                        }

                        // M3-5m7 (dominant 7 flat 5)
                    } else if (c.contains(root + 10)) {
                        certainty += 8
                        // M3-5m7M9
                        if (c.contains(root + 2)) {
                            // M3-5m7M9M13 (11 is optional to call it a 13 chord, as the 11 is an avoid note)
                            if (c.contains(root + 9)) {
                                name += "13" + FLAT + "5"
                                val p = nameTones(c, root, 9, 11)
                                name += p.first
                                certainty += p.second
                                // M3-5m7M9M11
                            } else if (c.contains(root + 5)) {
                                name += "11" + FLAT + "5"
                                val p = nameTones(c, root, 9)
                                name += p.first
                                certainty += p.second
                                // M3-5m7M9, no M11, no M13
                            } else {
                                name += "9" + FLAT + "5"
                                val p = nameTones(c, root, 11, 13)
                                name += p.first
                                certainty += p.second
                            }
                            // M3-5m7, no M9, no M11, no M13
                        } else {
                            name += "7" + FLAT + "5"
                            val p = nameTones(c, root, 9, 11, 13)
                            name += p.first
                            certainty += p.second
                        }
                        // M3-5M6 (major 6 with no M7/m7 at all)
                    } else if (c.contains(root + 9)) {
                        certainty += 6
                        name += "6" + FLAT + "5"
                        val p = nameTones(c, root, 9, 11)
                        name += p.first
                        certainty += p.second
                        //M3-5 with no (non-flat)6 or any 7
                    } else {
                        name += "M" + FLAT + "5"
                        val p = nameTones(c, root, -6, 9, 11)
                        name += p.first
                        certainty += p.second
                    }
                    // No 5 present (name as if there's a perfect 5)
                    //M3x5
                } else {
                    println("No5 is in the chord!")
                    if (c.contains(root))
                        certainty += 5
                    //M3x5M7 (major 7 type chord)
                    if (c.contains(root + 11)) {
                        certainty += 8
                        //M3x5M7M9
                        if (c.contains(root + 2)) {
                            certainty += 5
                            //M3x5M7M9M13 (11 is optional to call it a 13 chord, as the 11 is an avoid note)
                            if (c.contains(root + 9)) {
                                name += "M13"
                                val p = nameTones(c, root, 9, 11)
                                name += p.first
                                certainty += p.second
                                //M3x5M7M9M11
                            } else if (c.contains(root + 5)) {
                                name += "M11"
                                val p = nameTones(c, root, 9)
                                name += p.first
                                certainty += p.second
                                //M3x5M7M9, no M11, no M13
                            } else {
                                name += "M9"
                                val p = nameTones(c, root, 11, 13)
                                name += p.first
                                certainty += p.second
                            }
                            // M3x5M7, no M9, no M11, no M13
                        } else {
                            name += "M7"
                            val p = nameTones(c, root, 9, 11, 13)
                            name += p.first
                            certainty += p.second
                        }

                        //M3x5m7 (dominant 7)
                    } else if (c.contains(root + 10)) {
                        certainty += 8
                        //M3x5m7M9
                        if (c.contains(root + 2)) {
                            certainty += 5
                            //M3x5m7M9M13 (11 is optional to call it a 13 chord, as the 11 is an avoid note)
                            if (c.contains(root + 9)) {
                                name += "13"
                                val p = nameTones(c, root, 9, 11)
                                name += p.first
                                certainty += p.second
                                //M3x5m7M9M11
                            } else if (c.contains(root + 5)) {
                                name += "11"
                                val p = nameTones(c, root, 9)
                                name += p.first
                                certainty += p.second
                                //M3x5m7M9, no M11, no M13
                            } else {
                                name += "9"
                                val p = nameTones(c, root, 11, 13)
                                name += p.first
                                certainty += p.second
                            }
                            // M3x5m7, no M9, no M11, no M13
                        } else {
                            name += "7"
                            val p = nameTones(c, root, 9, 11, 13)
                            name += p.first
                            certainty += p.second
                        }
                        //M3x5M6 (major 6/13 with no M7/m7 at all)
                    } else if (c.contains(root + 9)) {
                        certainty -= 1
                        name = name + "6"
                        val p = nameTones(c, root, 9, 11)
                        name += p.first
                        certainty += p.second
                        //M3x5 with no (non-flat)6 or any 7
                    } else {
                        //name += "M";

                        val p = nameTones(c, root, -6, 9, 11)
                        val colors = p.first
                        certainty += p.second
                        if (colors.length > 0 && (colors.startsWith("#") || colors[0] == FLAT))
                            name += "M" + colors
                        else
                            name += colors
                        //Pair<String,Integer> p = nameTones(c,root, -6, 9, 11);
                        //name += p.first;
                        certainty += p.second
                    }
                }

                // m3 (and no M3 - if both are present we don't get here and it becomes a #9)
            } else if (c.contains(root + 3)) {
                println("m3 is in the chord!")
                certainty += 10
                // m3P5 - major triad present
                if (c.contains(root + 7)) {
                    name += "-"
                    println("P5 is in the chord!")
                    certainty += 8

                    //m3P5M7 (major 7 type chord)
                    if (c.contains(root + 11)) {
                        certainty += 8
                        //m3P5M7M9
                        if (c.contains(root + 2)) {
                            certainty += 5
                            //m3P5M7M9M13 (11 is optional to call it a 13 chord, as the 11 is an avoid note)
                            if (c.contains(root + 9)) {
                                name += "M13"
                                val p = nameTones(c, root, 9, 11)
                                name += p.first
                                certainty += p.second
                                //m3P5M7M9M11
                            } else if (c.contains(root + 5)) {
                                name += "M11"
                                val p = nameTones(c, root, 9)
                                name += p.first
                                certainty += p.second
                                //m3P5M7M9, no M11, no M13
                            } else {
                                name += "M9"
                                val p = nameTones(c, root, 11, 13)
                                name += p.first
                                certainty += p.second
                            }
                            // m3P5M7, no M9, no M11, no M13
                        } else {
                            name += "M7"
                            val p = nameTones(c, root, 9, 11, 13)
                            name += p.first
                            certainty += p.second
                        }

                        //m3P5m7 (minor 7)
                    } else if (c.contains(root + 10)) {
                        certainty += 8
                        //m3P5m7M9
                        if (c.contains(root + 2)) {
                            certainty += 5
                            //m3P5m7M9M13 (11 is optional to call it a 13 chord, as the 11 is an avoid note)
                            if (c.contains(root + 9)) {
                                name += "13"
                                val p = nameTones(c, root, 9, 11)
                                name += p.first
                                certainty += p.second
                                //m3P5m7M9M11
                            } else if (c.contains(root + 5)) {
                                name += "11"
                                val p = nameTones(c, root, 9)
                                name += p.first
                                certainty += p.second
                                //m3P5m7M9, no M11, no M13
                            } else {
                                name += "9"
                                val p = nameTones(c, root, 11, 13)
                                name += p.first
                                certainty += p.second
                            }
                            // m3P5m7, no M9, no M11, no M13
                        } else {
                            name += "7"
                            val p = nameTones(c, root, 9, 11, 13)
                            name += p.first
                            certainty += p.second
                        }
                        //m3P5M6 (major 6 with no M7/m7 at all)
                    } else if (c.contains(root + 9)) {
                        name = name + "6"
                        val p = nameTones(c, root, 9, 11)
                        name += p.first
                        certainty += p.second
                        // m3P5 with no (non-flat)6 or any 7
                    } else {
                        val p = nameTones(c, root, -6, 9, 11)
                        name += p.first
                        certainty += p.second
                    }

                    // m3-5 - diminished chord/triad present
                } else if (c.contains(root + 6)) {
                    println("-5 is in the chord!")
                    certainty += 8

                    // m3-5M7 (diminished major 7 type chord)
                    if (c.contains(root + 11)) {
                        certainty += 5
                        name = name + DIMINISHED
                        // m3-5M7M9
                        if (c.contains(root + 2)) {
                            // m3-5M7M9M13 (11 is optional to call it a 13 chord, as
                            // the 11 is an avoid note)
                            if (c.contains(root + 9)) {
                                name += "M13"
                                val p = nameTones(c, root, 9, 11)
                                name += p.first
                                certainty += p.second
                                // m3-5M7M9M11
                            } else if (c.contains(root + 5)) {
                                name += "M11"
                                val p = nameTones(c, root, 9)
                                name += p.first
                                certainty += p.second
                                // m3-5M7M9, no M11, no M13
                            } else {
                                name += "M9"
                                val p = nameTones(c, root, 11, 13)
                                name += p.first
                                certainty += p.second
                            }
                            // m3-5M7, no M9, no M11, no M13
                        } else {
                            name += "M7"
                            val p = nameTones(c, root, 9, 11, 13)
                            name += p.first
                            certainty += p.second
                        }

                        // m3-5m7 (minor 7 flat 5/half-dim)
                    } else if (c.contains(root + 10)) {
                        name += "-"
                        certainty += 8
                        // m3-5m7M9
                        if (c.contains(root + 2)) {
                            // m3-5m7M9M13 (11 is optional to call it a 13 chord, as the 11 is an avoid note)
                            if (c.contains(root + 9)) {
                                name += "13" + FLAT + "5"
                                val p = nameTones(c, root, 9, 11)
                                name += p.first
                                certainty += p.second
                                // M3-5m7M9M11
                            } else if (c.contains(root + 5)) {
                                name += "11" + FLAT + "5"
                                val p = nameTones(c, root, 9)
                                name += p.first
                                certainty += p.second
                                // M3-5m7M9, no M11, no M13
                            } else {
                                name += "9" + FLAT + "5"
                                val p = nameTones(c, root, 11, 13)
                                name += p.first
                                certainty += p.second
                            }
                            // M3-5m7, no M9, no M11, no M13
                        } else {
                            name += "7" + FLAT + "5"
                            val p = nameTones(c, root, 9, 11, 13)
                            name += p.first
                            certainty += p.second
                        }
                        // m3-5-7 (fully diminished with no M7/m7 at all)
                    } else if (c.contains(root + 9)) {
                        name += DIMINISHED + "7"
                        val p = nameTones(c, root, -6, 9, 11)
                        name += p.first
                        certainty += p.second
                        //m3-5 with no (non-flat)6 or any 7 (dim chord)
                    } else {
                        name += DIMINISHED
                        val p = nameTones(c, root, -6, 9, 11)
                        name += p.first
                        certainty += p.second
                    }
                    // m3x5 No 5 present (name as if there's a major 5)
                } else {
                    println("No5 is in the chord!")
                    if (c.contains(root))
                        certainty += 4
                    name += "-"
                    //m3x5M7 (minor-major 7 type chord)
                    if (c.contains(root + 11)) {
                        certainty += 8
                        //m3x5M7M9
                        if (c.contains(root + 2)) {
                            certainty += 5
                            //m3x5M7M9M13 (11 is optional to call it a 13 chord, as the 11 is an avoid note)
                            if (c.contains(root + 9)) {
                                name += "M13"
                                val p = nameTones(c, root, 9, 11)
                                name += p.first
                                certainty += p.second
                                //m3x5M7M9M11
                            } else if (c.contains(root + 5)) {
                                name += "M11"
                                val p = nameTones(c, root, 9)
                                name += p.first
                                certainty += p.second
                                //m3x5M7M9, no M11, no M13
                            } else {
                                name += "M9"
                                val p = nameTones(c, root, 11, 13)
                                name += p.first
                                certainty += p.second
                            }
                            // m3x5M7, no M9, no M11, no M13
                        } else {
                            name += "M7"
                            val p = nameTones(c, root, 9, 11, 13)
                            name += p.first
                            certainty += p.second
                        }

                        //m3x5m7 (minor 7)
                    } else if (c.contains(root + 10)) {
                        certainty += 8
                        //m3x5m7M9
                        if (c.contains(root + 2)) {
                            certainty += 5
                            //m3x5m7M9M13 (11 is optional to call it a 13 chord, as the 11 is an avoid note)
                            if (c.contains(root + 9)) {
                                name += "13"
                                val p = nameTones(c, root, 9, 11)
                                name += p.first
                                certainty += p.second
                                //m3x5m7M9M11
                            } else if (c.contains(root + 5)) {
                                name += "11"
                                val p = nameTones(c, root, 9)
                                name += p.first
                                certainty += p.second
                                //m3x5m7M9, no M11, no M13
                            } else {
                                name += "9"
                                val p = nameTones(c, root, 11, 13)
                                name += p.first
                                certainty += p.second
                            }
                            // m3x5m7, no M9, no M11, no M13
                        } else {
                            name += "7"
                            val p = nameTones(c, root, 9, 11, 13)
                            name += p.first
                            certainty += p.second
                        }
                        //m3x5M6 (major 6 with no M7/m7 at all)
                    } else if (c.contains(root + 9)) {
                        certainty -= 1
                        name += "6"
                        val p = nameTones(c, root, 9, 11)
                        name += p.first
                        certainty += p.second
                        //m3x5b6x7 or m3x5x6x7.  Just need to name the 9, 11, and possibly b6.
                    } else {
                        val p = nameTones(c, root, -6, 9, 11)
                        name += p.first
                        certainty += p.second
                    }
                }

                // x3M2P4 - sus24 chord.  Only notes definable are the 13/6 and 7
            } else if (c.contains(root + 2) && c.contains(root + 5)) {
                certainty += 9
                //TODO what about fifths?
                val seven = nameTones(c, root, 7).first
                name += seven
                name += "sus24"
                val p = nameTones(c, root, 13)
                name += p.first
                certainty += p.second
                // x3P4
            } else if (c.contains(root + 5)) {
                certainty += 9
                //TODO what about fifths?
                val seven = nameTones(c, root, 7).first
                name += seven
                name += "sus4"
                val p = nameTones(c, root, -9, 13)
                name += p.first
                certainty += p.second
                // x3M2
            } else if (c.contains(root + 2)) {
                certainty += 9
                //TODO what about fifths?
                val seven = nameTones(c, root, 7).first
                name += seven
                if (name === "")
                    name += "2"
                else
                    name += "sus2"
                val p = nameTones(c, root, -11, 13)
                name += p.first
                certainty += p.second
                // x2x3x4P5 - 5 chord, no M2, no M/m3, no P4
            } else if (c.contains(root + 7)) {
                if (c.contains(root))
                    certainty += 8
                val seven = nameTones(c, root, 7).first
                name += seven
                if (seven === "")
                    name += "5"
                else
                    name += "(no3)"
                val p = nameTones(c, root, -9, -11, 13)
                name += p.first
                certainty += p.second
                // x2x3x4+5 - +5 chord, no M2, no M/m3, no P4
            } else if (c.contains(root + 8)) {
                if (c.contains(root))
                    certainty += 6
                name += "+"
                val seven = nameTones(c, root, 7).first
                name += seven
                name += "(no3)"
                val p = nameTones(c, root, -9, -11, 13)
                name += p.first
                certainty += p.second

                // x2x3x4-5 - -5 chord, no M2, no M/m3, no P4
            } else if (c.contains(root + 6)) {
                println("-5 is in the chord!")
                certainty += 7

                // x3-5M7 (diminished major 7 type chord)
                if (c.contains(root + 11)) {
                    certainty += 7
                    name = name + DIMINISHED + "(no3)"
                    // m3-5M7M9
                    if (c.contains(root + 2)) {
                        // m3-5M7M9M13 (11 is optional to call it a 13 chord, as
                        // the 11 is an avoid note)
                        if (c.contains(root + 9)) {
                            name += "M13"
                            val p = nameTones(c, root, 9, 11)
                            name += p.first
                            certainty += p.second
                            // m3-5M7M9M11
                        } else if (c.contains(root + 5)) {
                            name += "M11"
                            val p = nameTones(c, root, 9)
                            name += p.first
                            certainty += p.second
                            // m3-5M7M9, no M11, no M13
                        } else {
                            name += "M9"
                            val p = nameTones(c, root, 11, 13)
                            name += p.first
                            certainty += p.second
                        }
                        // m3-5M7, no M9, no M11, no M13
                    } else {
                        name += "M7"
                        val p = nameTones(c, root, 9, 11, 13)
                        name += p.first
                        certainty += p.second
                    }

                    // x3-5m7 (NOT minor 7 flat 5/half-dim; we "hear" the M3 to make a Dom7b5 chord)
                } else if (c.contains(root + 10)) {
                    certainty += 8
                    // m3-5m7M9
                    if (c.contains(root + 2)) {
                        // m3-5m7M9M13 (11 is optional to call it a 13 chord, as the 11 is an avoid note)
                        if (c.contains(root + 9)) {
                            name += "13" + FLAT + "5"
                            val p = nameTones(c, root, 9, 11)
                            name += p.first
                            certainty += p.second
                            // M3-5m7M9M11
                        } else if (c.contains(root + 5)) {
                            name += "11" + FLAT + "5"
                            val p = nameTones(c, root, 9)
                            name += p.first
                            certainty += p.second
                            // M3-5m7M9, no M11, no M13
                        } else {
                            name += "9" + FLAT + "5"
                            val p = nameTones(c, root, 11, 13)
                            name += p.first
                            certainty += p.second
                        }
                        // M3-5m7, no M9, no M11, no M13
                    } else {
                        name += "7" + FLAT + "5"
                        val p = nameTones(c, root, 9, 11, 13)
                        name += p.first
                        certainty += p.second
                    }
                    name += "(no3)"
                    // x3-5-7 (heard as fully diminished with no M7/m7 at all)
                } else if (c.contains(root + 9)) {
                    name += DIMINISHED + "7"
                    val p = nameTones(c, root, -6, 9, 11)
                    name += p.first
                    certainty += p.second
                    //m3-5 with no (non-flat)6 or any 7 (dim chord)
                } else {
                    name += DIMINISHED + "(no3)"
                    val p = nameTones(c, root, -6, 9, 11)
                    name += p.first
                    certainty += p.second
                }
                // x3 with no M/m3, M2, P4, or P/+/-5.  Harmonic series says we fill in the major chord
                // within a few overtones so just name the 7, possible b9, and possible M6/b7/M7 remaining
            } else {
                val p = nameTones(c, root, 7, -9, 13)
                name += p.first
                certainty += p.second
            }

            //Adjust certainty for name complexity.
            //certainty -= name.length() + 2;

            return Pair(name, certainty)
        }

        /**
         * Return the appropriate chord for the given name.  For instance, getChordByName("CM7-5b13")
         * return a Chord with root [0] and [4], [6], [11] and [8] also present.

         * @param s any chord name
         * *
         * @return
         */
        fun getChordByName(s: String): Chord {
            val result = Chord()
            // EX: F+M7#6#9
            val p = Regex("((?:A|B|C|D|E|F|G)(?:#|b|" + FLAT + ")?)" + //root name (1)

                    "(-|\\+|" + DIMINISHED + "|2|sus2|sus|sus4|sus24|5?)" + // quality (2)

                    "(M?)(6|7|9|11|13|)((?:\\(no3\\))?)" + // "color quality" (3)(4)(5)

                    "((?:(?:add)?(?:b|#)?(?:-5|7|9|11|13))*)") // more color tones to be parsed later (6)
            val m = p.matchEntire(s)
            if (m != null) {
                println(m.groupValues[1] + " | " +
                        m.groupValues[2] + " | " +
                        m.groupValues[3] + " | " +
                        m.groupValues[4] + " | " +
                        m.groupValues[5] + " | " +
                        m.groupValues[6])
                
                val root = Pitch.getTone(m.groupValues[1])
                result.root = Pitch(root, m.groupValues[1])

                // quality
                var two = -1
                var three = -1
                var four = -1
                var five = -1

                if (m.groupValues[2] === "-") {
                    three = root + 3
                } else if (m.groupValues[2] == DIMINISHED.toString()) {
                    three = root + 3
                    five = root + 6
                } else if (m.groupValues[2] == "+") {
                    three = root + 4
                    five = root + 8
                } else if (m.groupValues[2] == "2" || m.groupValues[2] == "sus2") {
                    two = root + 2
                } else if (m.groupValues[2] == "sus" || m.groupValues[2] == "sus4") {
                    four = root + 5
                } else if (m.groupValues[2] == "sus24") {
                    two = root + 2
                    four = root + 5
                } else if (m.groupValues[2] == "" && m.groupValues[5] == "") {
                    three = root + 4
                }


                var six = -1
                var seven = -1

                // color quality
                val thirdsSpan = m.groupValues[4]
                val colorQualityInterval: Int
                if (thirdsSpan == "")
                    colorQualityInterval = -1
                else
                    colorQualityInterval = HEPTATONIC.mod(thirdsSpan.toInt())
                when (colorQualityInterval) {
                // 13 chord
                    6 -> {
                        six = root + 8
                        if (colorQualityInterval == 4)
                            four = root + 5
                        two = root + 2
                        if (five == root + 6)
                            seven = root + 9
                        else if (m.groupValues[3] == "M")
                            seven = root + 11
                        else
                            seven = root + 10
                    }
                // 11 chord (or 13 chord, but the conditional will skip the 11)
                    4 -> {
                        if (colorQualityInterval == 4)
                            four = root + 5
                        two = root + 2
                        if (five == root + 6)
                            seven = root + 9
                        else if (m.groupValues[3] == "M")
                            seven = root + 11
                        else
                            seven = root + 10
                    }
                // 9 or 11 or 13 chord
                    2 -> {
                        two = root + 2
                        if (five == root + 6)
                            seven = root + 9
                        else if (m.groupValues[3] == "M")
                            seven = root + 11
                        else
                            seven = root + 10
                    }
                // 7 chord.  Must decide major or minor.
                    0 -> if (five == root + 6)
                        seven = root + 9
                    else if (m.groupValues[3] == "M")
                        seven = root + 11
                    else
                        seven = root + 10
                }

                //colors
                val p6 = Regex("(?:add)?((?:b|#)?((?:-5|6|7|9|11|13)))")
                var m6 = p6.matchEntire(m.groupValues[6])
                while(m6 != null) {
                    val colorToneInterval = m6.groupValues[2].toInt()
                    when (colorToneInterval) {
                        -5 -> if (five == -1)
                            five = root + 6
                        6 -> six = root + schenkerianToInt(m6.groupValues[1])
                        7 -> seven = root + schenkerianToInt(m6.groupValues[1])
                        9 -> two = root + schenkerianToInt(m6.groupValues[1])
                        11 -> four = root + schenkerianToInt(m6.groupValues[1])
                        13 -> six = root + schenkerianToInt(m6.groupValues[1])
                    }
                    m6 = m6.next()
                }

                if (m.groupValues[5] == "(no3)") {
                    three = -1
                }

                //Add a P5 if one is not present
                if (five == -1) {
                    five = root + 7
                }

                // Add tones to chord
                for (i in intArrayOf(two, three, four, five, six, seven)) {
                    if (i != -1)
                        result.add(i)
                }
            }
            return result
        }

        /**
         * Convert a Schenkerian name (6, b7, #9, -5) to the number of half
         * steps that interval is from its tonic. (here, these are 9, 10, 3, and 6).
         * @param s
         * *
         * @return
         */
        fun schenkerianToInt(s: String): Int {
            var result = 0
            val regex = Regex("(#|$FLAT|-|\\+|)(\\d+)")
            val matcher = regex.matchEntire(s)
            if (matcher != null) {
                
                val interval = HEPTATONIC.mod(matcher.groupValues[2].toInt())
                when (interval) {
                //7
                    0 -> result = 11
                    1 -> result = 0
                // 2 or 9
                    2 -> result = 2
                    3 -> result = 4
                // 4 or 1
                    4 -> result = 5
                    5 -> result = 7
                // 6 or 13
                    6 -> result = 9
                    else -> {
                    }
                }

                if (matcher.groupValues[1].isNotEmpty()) {
                    if (matcher.groupValues[1][0].isFlat()) {
                        result -= 1
                    } else if (matcher.groupValues[1][0].isSharp()) {
                        result += 1

                        // Augmented/diminished intervals 
                    } else if (matcher.groupValues[1][0] == '+' && (result == 7 || result == 5)) {
                        result += 1
                    } else if (matcher.groupValues[1][0] == '+') {
                        result += 2
                    } else if (matcher.groupValues[1][0] == '-' && (result == 7 || result == 5)) {
                        result -= 1
                    } else if (matcher.groupValues[1][0] == '-') {
                        result -= 2
                    }
                }
            }

            return TWELVETONE.mod(result)
        }
    }
}
