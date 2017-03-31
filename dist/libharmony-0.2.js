if (typeof kotlin === 'undefined') {
  throw new Error("Error loading module 'libharmony-0.2'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'libharmony-0.2'.");
}
this['libharmony-0.2'] = function (Kotlin) {
  'use strict';
  var _ = Kotlin.defineRootPackage(null, /** @lends _ */ {
    com: Kotlin.definePackage(null, /** @lends _.com */ {
      jonlatane: Kotlin.definePackage(null, /** @lends _.com.jonlatane */ {
        libharmony: Kotlin.definePackage(function () {
          this.FLAT = '\u266D';
          this.SHARP = '#';
          this.NATURAL = '\u266E';
          this.MAJOR = 'M';
          this.MINOR = '-';
          this.DIMINISHED = '\xB0';
          this.TWELVE_TONE_NAMES = Kotlin.kotlin.collections.mapOf_eoa9s7$([Kotlin.kotlin.to_l1ob02$(0, 'C'), Kotlin.kotlin.to_l1ob02$(2, 'D'), Kotlin.kotlin.to_l1ob02$(4, 'E'), Kotlin.kotlin.to_l1ob02$(5, 'F'), Kotlin.kotlin.to_l1ob02$(7, 'G'), Kotlin.kotlin.to_l1ob02$(9, 'A'), Kotlin.kotlin.to_l1ob02$(11, 'B')]);
          var tmp$2 = _.com.jonlatane.libharmony.invert_0(_.com.jonlatane.libharmony.TWELVE_TONE_NAMES);
          var $receiver = _.com.jonlatane.libharmony.TWELVE_TONE_NAMES;
          var destination = new Kotlin.LinkedHashMap(Kotlin.kotlin.collections.mapCapacity_0($receiver.size));
          var tmp$4;
          tmp$4 = $receiver.entries.iterator();
          while (tmp$4.hasNext()) {
            var element = tmp$4.next();
            destination.put_wn2jw4$(element.key, element.value.toLowerCase());
          }
          this.TWELVE_TONE_INVERSE = Kotlin.kotlin.collections.plus_y1w8a6$(tmp$2, _.com.jonlatane.libharmony.invert_0(destination));
        }, /** @lends _.com.jonlatane.libharmony */ {
          toInt_pdl1w0$: function ($receiver) {
            return parseInt($receiver);
          },
          isFlat_myv2d1$: function ($receiver) {
            return $receiver === _.com.jonlatane.libharmony.FLAT || $receiver === 'b';
          },
          isSharp_myv2d1$: function ($receiver) {
            return $receiver === _.com.jonlatane.libharmony.SHARP || $receiver === '#';
          },
          isMajor_myv2d1$: function ($receiver) {
            return $receiver === _.com.jonlatane.libharmony.MAJOR;
          },
          isDiminished_jczb2y$: function ($receiver, perfect) {
            if (perfect === void 0)
              perfect = false;
            return $receiver === _.com.jonlatane.libharmony.DIMINISHED || (perfect && _.com.jonlatane.libharmony.isFlat_myv2d1$($receiver));
          },
          toOctave_myv2d1$: function ($receiver) {
            if (!$receiver.isDigit())
              throw Kotlin.Throwable('toOctave can only be called on digits');
            return $receiver.charCodeAt(0) - 48;
          },
          isNatural_myv2d1$: function ($receiver) {
            return $receiver === _.com.jonlatane.libharmony.NATURAL;
          },
          isMusical_myv2d1$: function ($receiver) {
            return _.com.jonlatane.libharmony.TWELVE_TONE_INVERSE.keys.contains_za3rmp$($receiver);
          },
          toTone_myv2d1$: function ($receiver) {
            var tmp$0;
            tmp$0 = _.com.jonlatane.libharmony.TWELVE_TONE_INVERSE.get_za3rmp$($receiver);
            if (tmp$0 == null) {
              throw Kotlin.Throwable('Character is not musical');
            }
            return tmp$0;
          },
          invert_0: function ($receiver) {
            var destination = new Kotlin.ArrayList($receiver.size);
            var tmp$2;
            tmp$2 = Kotlin.kotlin.collections.iterator_efxzmg$($receiver);
            while (tmp$2.hasNext()) {
              var item = tmp$2.next();
              destination.add_za3rmp$(Kotlin.kotlin.to_l1ob02$(item.value, item.key));
            }
            return Kotlin.kotlin.collections.mapOf_eoa9s7$(Kotlin.copyToArray(destination).slice());
          },
          toHeptatonicNumber_s8itvh$: function (heptatonicCharacter) {
            if (heptatonicCharacter === 'A')
              return 5;
            else if (heptatonicCharacter === 'a')
              return 5;
            else if (heptatonicCharacter === 'B')
              return 6;
            else if (heptatonicCharacter === 'b')
              return 6;
            else if (heptatonicCharacter === 'C')
              return 0;
            else if (heptatonicCharacter === 'c')
              return 0;
            else if (heptatonicCharacter === 'D')
              return 1;
            else if (heptatonicCharacter === 'd')
              return 1;
            else if (heptatonicCharacter === 'E')
              return 2;
            else if (heptatonicCharacter === 'e')
              return 2;
            else if (heptatonicCharacter === 'F')
              return 3;
            else if (heptatonicCharacter === 'f')
              return 3;
            else if (heptatonicCharacter === 'G')
              return 4;
            else if (heptatonicCharacter === 'g')
              return 4;
            throw Kotlin.Throwable('Invalid character');
          },
          toHeptatonicCharacter_za3lpa$: function (heptatonicNumber) {
            var tmp$0;
            tmp$0 = _.com.jonlatane.libharmony.mod_gb6t67$(heptatonicNumber, _.com.jonlatane.libharmony.Modulus.Companion.HEPTATONIC);
            if (tmp$0 === 0)
              return 'C';
            else if (tmp$0 === 1)
              return 'D';
            else if (tmp$0 === 2)
              return 'E';
            else if (tmp$0 === 3)
              return 'F';
            else if (tmp$0 === 4)
              return 'G';
            else if (tmp$0 === 5)
              return 'A';
            else if (tmp$0 === 6)
              return 'B';
            throw Kotlin.Throwable('Invalid number');
          },
          absoluteHeptDistance_puj7f4$: function (s1, s2) {
            var s1_octave = s1.charAt(s1.length - 1).charCodeAt(0);
            var s2_octave = s2.charAt(s2.length - 1).charCodeAt(0);
            var s1_heptClass = _.com.jonlatane.libharmony.toHeptatonicNumber_s8itvh$(s1.charAt(0));
            var s2_heptClass = _.com.jonlatane.libharmony.toHeptatonicNumber_s8itvh$(s2.charAt(0));
            return s2_heptClass - s1_heptClass + 7 * (s2_octave - s1_octave);
          },
          Key: Kotlin.createClass(function () {
            return [_.com.jonlatane.libharmony.Scale];
          }, function Key(root) {
            Key.baseInitializer.call(this, root);
            this.invariant();
          }, /** @lends _.com.jonlatane.libharmony.Key.prototype */ {
            invariant: function () {
              if (this.root.enharmonic == null)
                throw Kotlin.Throwable('Keys must have a root name.');
            },
            getNoteName_za3lpa$: function (tone) {
              var tmp$0, tmp$1, tmp$2, tmp$3, tmp$4, tmp$5, tmp$6, tmp$7, tmp$8, tmp$9;
              var i = _.com.jonlatane.libharmony.mod_gb6t67$(tone, (tmp$0 = this.modulus) != null ? tmp$0 : Kotlin.throwNPE());
              Kotlin.println('Getting note name for ' + i + ' in ' + this.toString());
              var result = '';
              var rootCharIndex = _.com.jonlatane.libharmony.toHeptatonicNumber_s8itvh$(((tmp$1 = this.root.enharmonic) != null ? tmp$1 : Kotlin.throwNPE()).charAt(0));
              var p = this.degreeOf_za3lpa$(i);
              if (p.first === p.second) {
                var letterName = _.com.jonlatane.libharmony.toHeptatonicCharacter_za3lpa$(_.com.jonlatane.libharmony.mod_gb6t67$(rootCharIndex + p.first - 1, _.com.jonlatane.libharmony.Modulus.Companion.HEPTATONIC));
                result += letterName;
                if (i < ((tmp$2 = _.com.jonlatane.libharmony.TWELVE_TONE_INVERSE.get_za3rmp$(letterName)) != null ? tmp$2 : Kotlin.throwNPE())) {
                  result += _.com.jonlatane.libharmony.FLAT;
                }
                if (i > ((tmp$3 = _.com.jonlatane.libharmony.TWELVE_TONE_INVERSE.get_za3rmp$(letterName)) != null ? tmp$3 : Kotlin.throwNPE())) {
                  result += '#';
                }
              }
               else {
                Kotlin.println('Trying to name note not in Key: ' + p.first + ',' + p.second + ';' + _.com.jonlatane.libharmony.Modulus.Companion.TWELVETONE.distance_vux9f0$(i, this.getDegree_za3lpa$(p.first)) + ',' + _.com.jonlatane.libharmony.Modulus.Companion.TWELVETONE.distance_vux9f0$(i, this.getDegree_za3lpa$(p.second)));
                if (_.com.jonlatane.libharmony.Modulus.Companion.TWELVETONE.distance_vux9f0$(i, this.getDegree_za3lpa$(p.first)) < _.com.jonlatane.libharmony.Modulus.Companion.TWELVETONE.distance_vux9f0$(i, this.getDegree_za3lpa$(p.second))) {
                  var letterName_0 = _.com.jonlatane.libharmony.toHeptatonicCharacter_za3lpa$(_.com.jonlatane.libharmony.mod_gb6t67$(rootCharIndex + p.first - 1, _.com.jonlatane.libharmony.Modulus.Companion.HEPTATONIC));
                  result += letterName_0;
                  if (i < ((tmp$4 = _.com.jonlatane.libharmony.TWELVE_TONE_INVERSE.get_za3rmp$(letterName_0)) != null ? tmp$4 : Kotlin.throwNPE())) {
                    result += _.com.jonlatane.libharmony.FLAT;
                  }
                  if (i > ((tmp$5 = _.com.jonlatane.libharmony.TWELVE_TONE_INVERSE.get_za3rmp$(letterName_0)) != null ? tmp$5 : Kotlin.throwNPE())) {
                    result += '#';
                  }
                  if (i > ((tmp$6 = _.com.jonlatane.libharmony.TWELVE_TONE_INVERSE.get_za3rmp$(letterName_0)) != null ? tmp$6 : Kotlin.throwNPE()) + 1) {
                    result += '#';
                  }
                }
                 else {
                  var letterName_1 = _.com.jonlatane.libharmony.toHeptatonicCharacter_za3lpa$(_.com.jonlatane.libharmony.mod_gb6t67$(rootCharIndex + p.second - 1, _.com.jonlatane.libharmony.Modulus.Companion.HEPTATONIC));
                  result += letterName_1;
                  if (i > ((tmp$7 = _.com.jonlatane.libharmony.TWELVE_TONE_INVERSE.get_za3rmp$(letterName_1)) != null ? tmp$7 : Kotlin.throwNPE())) {
                    result += '#';
                  }
                  if (i < ((tmp$8 = _.com.jonlatane.libharmony.TWELVE_TONE_INVERSE.get_za3rmp$(letterName_1)) != null ? tmp$8 : Kotlin.throwNPE())) {
                    result += _.com.jonlatane.libharmony.FLAT;
                  }
                  if (i < ((tmp$9 = _.com.jonlatane.libharmony.TWELVE_TONE_INVERSE.get_za3rmp$(letterName_1)) != null ? tmp$9 : Kotlin.throwNPE()) - 1) {
                    result += _.com.jonlatane.libharmony.FLAT;
                  }
                }
              }
              Kotlin.println('Got note name ' + result);
              return result;
            },
            getRootLikelihoodsAndNames: function (c) {
              return _.com.jonlatane.libharmony.Key.Companion.getRootLikelihoodsAndNames_bp1o7t$(Kotlin.kotlin.collections.toList_q5oq31$(new Kotlin.NumberRange(0, 11)), c, this);
            }
          }, /** @lends _.com.jonlatane.libharmony.Key */ {
            Companion: Kotlin.createObject(null, function Companion() {
            }, /** @lends _.com.jonlatane.libharmony.Key.Companion.prototype */ {
              getRootLikelihoodsAndNames_bp1o7t$: function (inputRootCandidates, c, k) {
                var tmp$0;
                var result = Kotlin.kotlin.collections.mutableMapOf_eoa9s7$([]);
                var declaredRoot = c.root;
                tmp$0 = inputRootCandidates.iterator();
                while (tmp$0.hasNext()) {
                  var n = tmp$0.next();
                  var tmp$1 = Chord.guessCharacteristic(c, n)
                  , name = tmp$1.component1()
                  , score = tmp$1.component2();
                  if (n === c.root.tone) {
                    score += 1000;
                  }
                  var bucket = result.get_za3rmp$(score);
                  if (bucket == null) {
                    bucket = Kotlin.kotlin.collections.mutableListOf_9mqe4v$([]);
                    result.put_wn2jw4$(score, bucket);
                  }
                  var rootName = k.getNoteName_za3lpa$(n);
                  bucket.add_za3rmp$(rootName + name);
                }
                return result;
              }
            }),
            object_initializer$: function () {
              _.com.jonlatane.libharmony.Key.Companion;
            }
          }),
          Key_init_shmg3a$: function (scale, $this) {
            $this = $this || Object.create(_.com.jonlatane.libharmony.Key.prototype);
            _.com.jonlatane.libharmony.Key.call($this, scale.root);
            $this.addAll_wtfk93$(scale);
            return $this;
          },
          Keys: Kotlin.createObject(null, function Keys() {
            this.CMajor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.MajorScale(new _.com.jonlatane.libharmony.Pitch(0, 'C')));
            this.CMinor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.NaturalMinorScale(new _.com.jonlatane.libharmony.Pitch(0, 'C')));
            this.DbMajor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.MajorScale(new _.com.jonlatane.libharmony.Pitch(1, 'D\u266D')));
            this.CsMajor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.MajorScale(new _.com.jonlatane.libharmony.Pitch(1, 'C#')));
            this.CsMinor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.NaturalMinorScale(new _.com.jonlatane.libharmony.Pitch(1, 'C#')));
            this.DMajor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.MajorScale(new _.com.jonlatane.libharmony.Pitch(2, 'D')));
            this.DMinor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.NaturalMinorScale(new _.com.jonlatane.libharmony.Pitch(2, 'D')));
            this.EbMajor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.MajorScale(new _.com.jonlatane.libharmony.Pitch(3, 'E\u266D')));
            this.EbMinor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.NaturalMinorScale(new _.com.jonlatane.libharmony.Pitch(3, 'E\u266D')));
            this.DsMinor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.NaturalMinorScale(new _.com.jonlatane.libharmony.Pitch(3, 'D#')));
            this.EMajor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.MajorScale(new _.com.jonlatane.libharmony.Pitch(4, 'E')));
            this.EMinor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.NaturalMinorScale(new _.com.jonlatane.libharmony.Pitch(4, 'E')));
            this.FMajor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.MajorScale(new _.com.jonlatane.libharmony.Pitch(5, 'F')));
            this.FMinor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.NaturalMinorScale(new _.com.jonlatane.libharmony.Pitch(5, 'F')));
            this.GbMajor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.MajorScale(new _.com.jonlatane.libharmony.Pitch(6, 'G\u266D')));
            this.FsMajor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.MajorScale(new _.com.jonlatane.libharmony.Pitch(6, 'F#')));
            this.FsMinor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.NaturalMinorScale(new _.com.jonlatane.libharmony.Pitch(6, 'F#')));
            this.GMajor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.MajorScale(new _.com.jonlatane.libharmony.Pitch(7, 'G')));
            this.GMinor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.NaturalMinorScale(new _.com.jonlatane.libharmony.Pitch(7, 'G')));
            this.AbMajor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.MajorScale(new _.com.jonlatane.libharmony.Pitch(8, 'A\u266D')));
            this.GsMinor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.NaturalMinorScale(new _.com.jonlatane.libharmony.Pitch(8, 'G#')));
            this.AbMinor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.NaturalMinorScale(new _.com.jonlatane.libharmony.Pitch(8, 'A\u266D')));
            this.AMajor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.MajorScale(new _.com.jonlatane.libharmony.Pitch(9, 'A')));
            this.AMinor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.NaturalMinorScale(new _.com.jonlatane.libharmony.Pitch(9, 'A')));
            this.BbMajor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.MajorScale(new _.com.jonlatane.libharmony.Pitch(10, 'B\u266D')));
            this.BbMinor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.NaturalMinorScale(new _.com.jonlatane.libharmony.Pitch(10, 'B\u266D')));
            this.AsMinor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.NaturalMinorScale(new _.com.jonlatane.libharmony.Pitch(10, 'A#')));
            this.BMajor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.MajorScale(new _.com.jonlatane.libharmony.Pitch(11, 'B')));
            this.CbMajor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.MajorScale(new _.com.jonlatane.libharmony.Pitch(11, 'C\u266D5')));
            this.BMinor = _.com.jonlatane.libharmony.Key_init_shmg3a$(new _.com.jonlatane.libharmony.NaturalMinorScale(new _.com.jonlatane.libharmony.Pitch(11, 'B')));
          }),
          mod_gb6t67$: function ($receiver, modulus) {
            return modulus.mod_za3lpa$($receiver);
          },
          mod_e1qdcr$: function ($receiver, modulus) {
            return modulus.mod_sg3a3w$($receiver);
          },
          Modulus: Kotlin.createClass(null, function Modulus(octaveSteps) {
            if (octaveSteps === void 0)
              octaveSteps = 12;
            this.octaveSteps = octaveSteps;
          }, /** @lends _.com.jonlatane.libharmony.Modulus.prototype */ {
            mod_za3lpa$: function (tone) {
              var i = tone;
              while (i < 0)
                i += this.octaveSteps;
              return i % this.octaveSteps;
            },
            mod_sg3a3w$: function (pitch) {
              return new _.com.jonlatane.libharmony.Pitch(this.mod_za3lpa$(pitch.tone), pitch.enharmonic);
            },
            octave_za3lpa$: function (tone) {
              var i = tone;
              var octave = 4;
              var n = this.mod_za3lpa$(i);
              while (i < n) {
                i += this.octaveSteps;
                octave -= 1;
              }
              while (i > n) {
                i -= this.octaveSteps;
                octave += 1;
              }
              return octave;
            },
            distance_vux9f0$: function (tone1, tone2) {
              var result1 = this.mod_za3lpa$(tone1 - tone2);
              var result2 = this.mod_za3lpa$(tone2 - tone1);
              return Math.min(result1, result2);
            },
            component1: function () {
              return this.octaveSteps;
            },
            copy_za3lpa$: function (octaveSteps) {
              return new _.com.jonlatane.libharmony.Modulus(octaveSteps === void 0 ? this.octaveSteps : octaveSteps);
            },
            toString: function () {
              return 'Modulus(octaveSteps=' + Kotlin.toString(this.octaveSteps) + ')';
            },
            hashCode: function () {
              var result = 0;
              result = result * 31 + Kotlin.hashCode(this.octaveSteps) | 0;
              return result;
            },
            equals_za3rmp$: function (other) {
              return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.octaveSteps, other.octaveSteps))));
            }
          }, /** @lends _.com.jonlatane.libharmony.Modulus */ {
            Companion: Kotlin.createObject(null, function Companion() {
              _.com.jonlatane.libharmony.Modulus.Companion.TWELVETONE = new _.com.jonlatane.libharmony.Modulus();
              _.com.jonlatane.libharmony.Modulus.Companion.HEPTATONIC = new _.com.jonlatane.libharmony.Modulus(7);
            }),
            object_initializer$: function () {
              _.com.jonlatane.libharmony.Modulus.Companion;
            }
          }),
          Pitch: Kotlin.createClass(null, function Pitch(tone, enharmonic) {
            if (enharmonic === void 0)
              enharmonic = null;
            this.tone = tone;
            this.enharmonic = enharmonic;
            this.invariant();
          }, /** @lends _.com.jonlatane.libharmony.Pitch.prototype */ {
            compareTo_sg3a3w$: function (other) {
              return this.compareTo_6mduzp$(other, false);
            },
            compareTo_6mduzp$: function (other, enharmonicSensitive) {
              var tmp$0, tmp$1, tmp$2, tmp$3, tmp$4;
              var result = Kotlin.primitiveCompareTo(this.tone, other.tone);
              if (enharmonicSensitive && result === 0) {
                result = (tmp$4 = (tmp$3 = (tmp$0 = this.enharmonic) != null ? tmp$0.charAt(0) : null) != null ? Kotlin.primitiveCompareTo(tmp$3, (tmp$2 = (tmp$1 = other.enharmonic) != null ? tmp$1.charAt(0) : null) != null ? tmp$2 : this.enharmonic.charAt(0)) : null) != null ? tmp$4 : 0;
              }
              return result;
            },
            minus_za3lpa$: function (value) {
              return new _.com.jonlatane.libharmony.Pitch(this.tone - value);
            },
            plus_za3lpa$: function (value) {
              return new _.com.jonlatane.libharmony.Pitch(this.tone + value);
            },
            invariant: function () {
              if (this.enharmonic != null && _.com.jonlatane.libharmony.Pitch.Companion.getTone_61zpoe$(this.enharmonic) !== this.tone) {
                throw Kotlin.Throwable('Pitch enharmonic does not match tone.');
              }
            },
            toString: function () {
              var tmp$0;
              return (tmp$0 = this.enharmonic) != null ? tmp$0 : this.tone.toString();
            },
            component1: function () {
              return this.tone;
            },
            component2: function () {
              return this.enharmonic;
            },
            copy_19mbxw$: function (tone, enharmonic) {
              return new _.com.jonlatane.libharmony.Pitch_init_61zpoe$(tone === void 0 ? this.tone : tone, enharmonic === void 0 ? this.enharmonic : enharmonic);
            },
            hashCode: function () {
              var result = 0;
              result = result * 31 + Kotlin.hashCode(this.tone) | 0;
              result = result * 31 + Kotlin.hashCode(this.enharmonic) | 0;
              return result;
            },
            equals_za3rmp$: function (other) {
              return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.tone, other.tone) && Kotlin.equals(this.enharmonic, other.enharmonic)))));
            }
          }, /** @lends _.com.jonlatane.libharmony.Pitch */ {
            Companion: Kotlin.createObject(null, function Companion() {
            }, /** @lends _.com.jonlatane.libharmony.Pitch.Companion.prototype */ {
              getTone_61zpoe$: function (enharmonic) {
                var tmp$0;
                if (enharmonic.length > 5)
                  throw Kotlin.Throwable('Too long');
                var result = (tmp$0 = _.com.jonlatane.libharmony.TWELVE_TONE_INVERSE.get_za3rmp$(enharmonic.charAt(0))) != null ? tmp$0 : Kotlin.throwNPE();
                var i = 1;
                while (i < enharmonic.length) {
                  if (_.com.jonlatane.libharmony.isFlat_myv2d1$(enharmonic.charAt(i)))
                    result -= 1;
                  else if (_.com.jonlatane.libharmony.isSharp_myv2d1$(enharmonic.charAt(i)))
                    result += 1;
                  else if (enharmonic.charAt(i).isDigit()) {
                    result += 12 * (_.com.jonlatane.libharmony.toOctave_myv2d1$(enharmonic.charAt(i)) - 4);
                  }
                  i += 1;
                }
                return result;
              },
              getEnharmonics_za3lpa$: function (tone) {
                var baseTone = _.com.jonlatane.libharmony.mod_gb6t67$(tone, _.com.jonlatane.libharmony.Modulus.Companion.TWELVETONE);
                var result = Kotlin.kotlin.collections.mutableListOf_9mqe4v$([]);
                var $receiver = _.com.jonlatane.libharmony.TWELVE_TONE_NAMES;
                var destination = new Kotlin.LinkedHashMap(Kotlin.kotlin.collections.mapCapacity_0($receiver.size));
                var tmp$2;
                tmp$2 = $receiver.entries.iterator();
                while (tmp$2.hasNext()) {
                  var element = tmp$2.next();
                  destination.put_wn2jw4$(element.key, element.value.toString());
                }
                var tmp$6;
                tmp$6 = Kotlin.kotlin.collections.iterator_efxzmg$(destination);
                while (tmp$6.hasNext()) {
                  var element_0 = tmp$6.next();
                  var tmp$7;
                  tmp$7 = _.com.jonlatane.libharmony.mod_gb6t67$(baseTone - element_0.key, _.com.jonlatane.libharmony.Modulus.Companion.TWELVETONE);
                  if (tmp$7 === 0)
                    result.add_za3rmp$(element_0.value);
                  else if (tmp$7 === 11)
                    result.add_za3rmp$(element_0.value + _.com.jonlatane.libharmony.FLAT);
                  else if (tmp$7 === 10)
                    result.add_za3rmp$(element_0.value + _.com.jonlatane.libharmony.FLAT + _.com.jonlatane.libharmony.FLAT);
                  else if (tmp$7 === 1)
                    result.add_za3rmp$(element_0.value + _.com.jonlatane.libharmony.SHARP);
                  else if (tmp$7 === 2)
                    result.add_za3rmp$(element_0.value + _.com.jonlatane.libharmony.SHARP + _.com.jonlatane.libharmony.SHARP);
                }
                var octave = _.com.jonlatane.libharmony.Modulus.Companion.TWELVETONE.octave_za3lpa$(tone);
                var destination_0 = new Kotlin.ArrayList(Kotlin.kotlin.collections.collectionSizeOrDefault_0(result, 10));
                var tmp$8;
                tmp$8 = result.iterator();
                while (tmp$8.hasNext()) {
                  var item = tmp$8.next();
                  destination_0.add_za3rmp$(!Kotlin.kotlin.text.startsWith_41xvrb$(item, 'B#') ? item + octave : item + (octave - 1));
                }
                return Kotlin.kotlin.collections.toSet_q5oq31$(destination_0);
              }
            }, /** @lends _.com.jonlatane.libharmony.Pitch.Companion */ {
            }),
            object_initializer$: function () {
              _.com.jonlatane.libharmony.Pitch.Companion;
            }
          }),
          Pitch_init_61zpoe$: function (enharmonic, $this) {
            $this = $this || Object.create(_.com.jonlatane.libharmony.Pitch.prototype);
            _.com.jonlatane.libharmony.Pitch.call($this, _.com.jonlatane.libharmony.Pitch.Companion.getTone_61zpoe$(enharmonic), enharmonic);
            return $this;
          },
          ImmutablePitchSet: Kotlin.createTrait(function () {
            return [Kotlin.kotlin.collections.Set];
          }),
          PitchSet: Kotlin.createClass(function () {
            return [_.com.jonlatane.libharmony.ImmutablePitchSet, Kotlin.kotlin.collections.MutableSet];
          }, function PitchSet(modulus, elements) {
            if (modulus === void 0)
              modulus = null;
            this.modulus = modulus;
            this.$delegate_5vv76y$_0 = Kotlin.kotlin.collections.mutableSetOf_9mqe4v$(elements.slice());
          }, /** @lends _.com.jonlatane.libharmony.PitchSet.prototype */ {
            add_za3lpa$: function (tone) {
              return this.add_za3rmp$(new _.com.jonlatane.libharmony.Pitch(tone));
            },
            contains_za3lpa$: function (tone) {
              var destination = new Kotlin.ArrayList();
              var tmp$1;
              tmp$1 = this.iterator();
              while (tmp$1.hasNext()) {
                var element = tmp$1.next();
                if (element.tone === tone) {
                  destination.add_za3rmp$(element);
                }
              }
              return !destination.isEmpty();
            },
            lower_za3lpa$: function (tone) {
              var tmp$0, tmp$1, tmp$2;
              if (this.modulus == null) {
                var destination = new Kotlin.ArrayList();
                var tmp$4;
                tmp$4 = this.iterator();
                while (tmp$4.hasNext()) {
                  var element = tmp$4.next();
                  if (element.tone < tone) {
                    destination.add_za3rmp$(element);
                  }
                }
                var maxBy_l82ugp$result;
                maxBy_l82ugp$break: {
                  var iterator = destination.iterator();
                  if (!iterator.hasNext()) {
                    maxBy_l82ugp$result = null;
                    break maxBy_l82ugp$break;
                  }
                  var maxElem = iterator.next();
                  var maxValue = maxElem.tone;
                  while (iterator.hasNext()) {
                    var e = iterator.next();
                    var v = e.tone;
                    if (Kotlin.compareTo(maxValue, v) < 0) {
                      maxElem = e;
                      maxValue = v;
                    }
                  }
                  maxBy_l82ugp$result = maxElem;
                }
                return (tmp$0 = maxBy_l82ugp$result) != null ? tmp$0.tone : null;
              }
               else {
                var tmp$6;
                var destination_0 = new Kotlin.ArrayList();
                var tmp$8;
                tmp$8 = this.iterator();
                while (tmp$8.hasNext()) {
                  var element_0 = tmp$8.next();
                  if (_.com.jonlatane.libharmony.mod_gb6t67$(element_0.tone, this.modulus) < _.com.jonlatane.libharmony.mod_gb6t67$(tone, this.modulus)) {
                    destination_0.add_za3rmp$(element_0);
                  }
                }
                var maxBy_l82ugp$result_0;
                maxBy_l82ugp$break_0: {
                  var iterator_0 = destination_0.iterator();
                  if (!iterator_0.hasNext()) {
                    maxBy_l82ugp$result_0 = null;
                    break maxBy_l82ugp$break_0;
                  }
                  var maxElem_0 = iterator_0.next();
                  var maxValue_0 = _.com.jonlatane.libharmony.mod_gb6t67$(maxElem_0.tone, this.modulus);
                  while (iterator_0.hasNext()) {
                    var e_0 = iterator_0.next();
                    var v_0 = _.com.jonlatane.libharmony.mod_gb6t67$(e_0.tone, this.modulus);
                    if (Kotlin.compareTo(maxValue_0, v_0) < 0) {
                      maxElem_0 = e_0;
                      maxValue_0 = v_0;
                    }
                  }
                  maxBy_l82ugp$result_0 = maxElem_0;
                }
                if ((tmp$1 = maxBy_l82ugp$result_0) != null)
                  tmp$6 = tmp$1;
                else {
                  var destination_1 = new Kotlin.ArrayList();
                  var tmp$10;
                  tmp$10 = this.iterator();
                  while (tmp$10.hasNext()) {
                    var element_1 = tmp$10.next();
                    if (_.com.jonlatane.libharmony.mod_gb6t67$(element_1.tone, this.modulus) < _.com.jonlatane.libharmony.mod_gb6t67$(tone + this.modulus.octaveSteps, this.modulus)) {
                      destination_1.add_za3rmp$(element_1);
                    }
                  }
                  maxBy_l82ugp$break_1: {
                    var iterator_1 = destination_1.iterator();
                    if (!iterator_1.hasNext()) {
                      tmp$6 = null;
                      break maxBy_l82ugp$break_1;
                    }
                    var maxElem_1 = iterator_1.next();
                    var maxValue_1 = _.com.jonlatane.libharmony.mod_gb6t67$(maxElem_1.tone, this.modulus);
                    while (iterator_1.hasNext()) {
                      var e_1 = iterator_1.next();
                      var v_1 = _.com.jonlatane.libharmony.mod_gb6t67$(e_1.tone, this.modulus);
                      if (Kotlin.compareTo(maxValue_1, v_1) < 0) {
                        maxElem_1 = e_1;
                        maxValue_1 = v_1;
                      }
                    }
                    tmp$6 = maxElem_1;
                  }
                }
                return (tmp$2 = tmp$6) != null ? tmp$2.tone : null;
              }
            },
            floor_za3lpa$: function (tone) {
              var tmp$0, tmp$1, tmp$2;
              if (this.modulus == null) {
                var destination = new Kotlin.ArrayList();
                var tmp$4;
                tmp$4 = this.iterator();
                while (tmp$4.hasNext()) {
                  var element = tmp$4.next();
                  if (element.tone <= tone) {
                    destination.add_za3rmp$(element);
                  }
                }
                var maxBy_l82ugp$result;
                maxBy_l82ugp$break: {
                  var iterator = destination.iterator();
                  if (!iterator.hasNext()) {
                    maxBy_l82ugp$result = null;
                    break maxBy_l82ugp$break;
                  }
                  var maxElem = iterator.next();
                  var maxValue = maxElem.tone;
                  while (iterator.hasNext()) {
                    var e = iterator.next();
                    var v = e.tone;
                    if (Kotlin.compareTo(maxValue, v) < 0) {
                      maxElem = e;
                      maxValue = v;
                    }
                  }
                  maxBy_l82ugp$result = maxElem;
                }
                return (tmp$0 = maxBy_l82ugp$result) != null ? tmp$0.tone : null;
              }
               else {
                var tmp$6;
                var destination_0 = new Kotlin.ArrayList();
                var tmp$8;
                tmp$8 = this.iterator();
                while (tmp$8.hasNext()) {
                  var element_0 = tmp$8.next();
                  if (_.com.jonlatane.libharmony.mod_gb6t67$(element_0.tone, this.modulus) <= _.com.jonlatane.libharmony.mod_gb6t67$(tone, this.modulus)) {
                    destination_0.add_za3rmp$(element_0);
                  }
                }
                var maxBy_l82ugp$result_0;
                maxBy_l82ugp$break_0: {
                  var iterator_0 = destination_0.iterator();
                  if (!iterator_0.hasNext()) {
                    maxBy_l82ugp$result_0 = null;
                    break maxBy_l82ugp$break_0;
                  }
                  var maxElem_0 = iterator_0.next();
                  var maxValue_0 = _.com.jonlatane.libharmony.mod_gb6t67$(maxElem_0.tone, this.modulus);
                  while (iterator_0.hasNext()) {
                    var e_0 = iterator_0.next();
                    var v_0 = _.com.jonlatane.libharmony.mod_gb6t67$(e_0.tone, this.modulus);
                    if (Kotlin.compareTo(maxValue_0, v_0) < 0) {
                      maxElem_0 = e_0;
                      maxValue_0 = v_0;
                    }
                  }
                  maxBy_l82ugp$result_0 = maxElem_0;
                }
                if ((tmp$1 = maxBy_l82ugp$result_0) != null)
                  tmp$6 = tmp$1;
                else {
                  var destination_1 = new Kotlin.ArrayList();
                  var tmp$10;
                  tmp$10 = this.iterator();
                  while (tmp$10.hasNext()) {
                    var element_1 = tmp$10.next();
                    if (_.com.jonlatane.libharmony.mod_gb6t67$(element_1.tone, this.modulus) <= _.com.jonlatane.libharmony.mod_gb6t67$(tone + this.modulus.octaveSteps, this.modulus)) {
                      destination_1.add_za3rmp$(element_1);
                    }
                  }
                  maxBy_l82ugp$break_1: {
                    var iterator_1 = destination_1.iterator();
                    if (!iterator_1.hasNext()) {
                      tmp$6 = null;
                      break maxBy_l82ugp$break_1;
                    }
                    var maxElem_1 = iterator_1.next();
                    var maxValue_1 = _.com.jonlatane.libharmony.mod_gb6t67$(maxElem_1.tone, this.modulus);
                    while (iterator_1.hasNext()) {
                      var e_1 = iterator_1.next();
                      var v_1 = _.com.jonlatane.libharmony.mod_gb6t67$(e_1.tone, this.modulus);
                      if (Kotlin.compareTo(maxValue_1, v_1) < 0) {
                        maxElem_1 = e_1;
                        maxValue_1 = v_1;
                      }
                    }
                    tmp$6 = maxElem_1;
                  }
                }
                return (tmp$2 = tmp$6) != null ? tmp$2.tone : null;
              }
            },
            ceiling_za3lpa$: function (tone) {
              var tmp$0, tmp$1, tmp$2;
              if (this.modulus == null) {
                var destination = new Kotlin.ArrayList();
                var tmp$4;
                tmp$4 = this.iterator();
                while (tmp$4.hasNext()) {
                  var element = tmp$4.next();
                  if (element.tone >= tone) {
                    destination.add_za3rmp$(element);
                  }
                }
                var maxBy_l82ugp$result;
                maxBy_l82ugp$break: {
                  var iterator = destination.iterator();
                  if (!iterator.hasNext()) {
                    maxBy_l82ugp$result = null;
                    break maxBy_l82ugp$break;
                  }
                  var maxElem = iterator.next();
                  var maxValue = maxElem.tone;
                  while (iterator.hasNext()) {
                    var e = iterator.next();
                    var v = e.tone;
                    if (Kotlin.compareTo(maxValue, v) < 0) {
                      maxElem = e;
                      maxValue = v;
                    }
                  }
                  maxBy_l82ugp$result = maxElem;
                }
                return (tmp$0 = maxBy_l82ugp$result) != null ? tmp$0.tone : null;
              }
               else {
                var tmp$6;
                var destination_0 = new Kotlin.ArrayList();
                var tmp$8;
                tmp$8 = this.iterator();
                while (tmp$8.hasNext()) {
                  var element_0 = tmp$8.next();
                  if (_.com.jonlatane.libharmony.mod_gb6t67$(element_0.tone, this.modulus) >= _.com.jonlatane.libharmony.mod_gb6t67$(tone, this.modulus)) {
                    destination_0.add_za3rmp$(element_0);
                  }
                }
                var maxBy_l82ugp$result_0;
                maxBy_l82ugp$break_0: {
                  var iterator_0 = destination_0.iterator();
                  if (!iterator_0.hasNext()) {
                    maxBy_l82ugp$result_0 = null;
                    break maxBy_l82ugp$break_0;
                  }
                  var maxElem_0 = iterator_0.next();
                  var maxValue_0 = _.com.jonlatane.libharmony.mod_gb6t67$(maxElem_0.tone, this.modulus);
                  while (iterator_0.hasNext()) {
                    var e_0 = iterator_0.next();
                    var v_0 = _.com.jonlatane.libharmony.mod_gb6t67$(e_0.tone, this.modulus);
                    if (Kotlin.compareTo(maxValue_0, v_0) < 0) {
                      maxElem_0 = e_0;
                      maxValue_0 = v_0;
                    }
                  }
                  maxBy_l82ugp$result_0 = maxElem_0;
                }
                if ((tmp$1 = maxBy_l82ugp$result_0) != null)
                  tmp$6 = tmp$1;
                else {
                  var destination_1 = new Kotlin.ArrayList();
                  var tmp$10;
                  tmp$10 = this.iterator();
                  while (tmp$10.hasNext()) {
                    var element_1 = tmp$10.next();
                    if (_.com.jonlatane.libharmony.mod_gb6t67$(element_1.tone, this.modulus) >= _.com.jonlatane.libharmony.mod_gb6t67$(tone + this.modulus.octaveSteps, this.modulus)) {
                      destination_1.add_za3rmp$(element_1);
                    }
                  }
                  maxBy_l82ugp$break_1: {
                    var iterator_1 = destination_1.iterator();
                    if (!iterator_1.hasNext()) {
                      tmp$6 = null;
                      break maxBy_l82ugp$break_1;
                    }
                    var maxElem_1 = iterator_1.next();
                    var maxValue_1 = _.com.jonlatane.libharmony.mod_gb6t67$(maxElem_1.tone, this.modulus);
                    while (iterator_1.hasNext()) {
                      var e_1 = iterator_1.next();
                      var v_1 = _.com.jonlatane.libharmony.mod_gb6t67$(e_1.tone, this.modulus);
                      if (Kotlin.compareTo(maxValue_1, v_1) < 0) {
                        maxElem_1 = e_1;
                        maxValue_1 = v_1;
                      }
                    }
                    tmp$6 = maxElem_1;
                  }
                }
                return (tmp$2 = tmp$6) != null ? tmp$2.tone : null;
              }
            },
            tailSet_za3lpa$: function (tone) {
              var tmp$1 = this.modulus;
              var destination = new Kotlin.ArrayList();
              var tmp$4;
              tmp$4 = this.iterator();
              while (tmp$4.hasNext()) {
                var element = tmp$4.next();
                if (element.tone > tone) {
                  destination.add_za3rmp$(element);
                }
              }
              return new _.com.jonlatane.libharmony.PitchSet(tmp$1, Kotlin.copyToArray(Kotlin.kotlin.collections.sortedWith_7dpn5g$(destination, new Kotlin.kotlin.comparisons.compareBy$f_0(_.com.jonlatane.libharmony.PitchSet.tailSet_za3lpa$f_0))));
            },
            toString: function () {
              var tmp$0;
              var result = '(';
              var first = true;
              tmp$0 = this.iterator();
              while (tmp$0.hasNext()) {
                var pitch = tmp$0.next();
                if (!first)
                  result += ',';
                first = false;
                result += pitch;
              }
              return result + ')';
            },
            size: {
              get: function () {
                return this.$delegate_5vv76y$_0.size;
              }
            },
            add_za3rmp$: function (element) {
              return this.$delegate_5vv76y$_0.add_za3rmp$(element);
            },
            addAll_wtfk93$: function (elements) {
              return this.$delegate_5vv76y$_0.addAll_wtfk93$(elements);
            },
            clear: function () {
              return this.$delegate_5vv76y$_0.clear();
            },
            contains_za3rmp$: function (element) {
              return this.$delegate_5vv76y$_0.contains_za3rmp$(element);
            },
            containsAll_wtfk93$: function (elements) {
              return this.$delegate_5vv76y$_0.containsAll_wtfk93$(elements);
            },
            isEmpty: function () {
              return this.$delegate_5vv76y$_0.isEmpty();
            },
            iterator: function () {
              return this.$delegate_5vv76y$_0.iterator();
            },
            remove_za3rmp$: function (element) {
              return this.$delegate_5vv76y$_0.remove_za3rmp$(element);
            },
            removeAll_wtfk93$: function (elements) {
              return this.$delegate_5vv76y$_0.removeAll_wtfk93$(elements);
            },
            retainAll_wtfk93$: function (elements) {
              return this.$delegate_5vv76y$_0.retainAll_wtfk93$(elements);
            }
          }, /** @lends _.com.jonlatane.libharmony.PitchSet */ {
            tailSet_za3lpa$f_0: function (it) {
              return it.tone;
            },
            Companion: Kotlin.createObject(null, function Companion() {
              _.com.jonlatane.libharmony.PitchSet.Companion.REST = new _.com.jonlatane.libharmony.PitchSet(void 0, []);
            }),
            object_initializer$: function () {
              _.com.jonlatane.libharmony.PitchSet.Companion;
            }
          }),
          Scale: Kotlin.createClass(function () {
            return [Chord];
          }, function Scale(root) {
            Scale.baseInitializer.call(this, root);
            this.isMajor_usz75w$_0 = false;
            this.isMinor_usz75w$_0 = false;
          }, /** @lends _.com.jonlatane.libharmony.Scale.prototype */ {
            descendingVersion: {
              get: function () {
                return this;
              }
            },
            getDegree_za3lpa$: function (tone) {
              var i = _.com.jonlatane.libharmony.Modulus.Companion.HEPTATONIC.mod_za3lpa$(tone);
              if (i === 0) {
                i = 7;
              }
              var currentDegree = 1;
              var destination = new Kotlin.ArrayList();
              var tmp$0;
              tmp$0 = this.iterator();
              while (tmp$0.hasNext()) {
                var element = tmp$0.next();
                if ((new _.com.jonlatane.libharmony.Pitch(element.tone)).compareTo_sg3a3w$(this.root) > 0) {
                  destination.add_za3rmp$(element);
                }
              }
              var itr = this.tailSet_za3lpa$(this.root.tone).iterator();
              while (true) {
                if (itr.hasNext()) {
                  if (currentDegree === i) {
                    return itr.next().tone;
                  }
                   else {
                    itr.next();
                  }
                }
                 else {
                  itr = this.iterator();
                }
                currentDegree = _.com.jonlatane.libharmony.Modulus.Companion.HEPTATONIC.mod_za3lpa$(currentDegree + 1);
                if (currentDegree === 0) {
                  currentDegree = 7;
                }
              }
            },
            degreeOf_za3lpa$: function (tone) {
              var i = _.com.jonlatane.libharmony.Modulus.Companion.TWELVETONE.mod_za3lpa$(tone);
              var upper = this.ceiling_za3lpa$(i);
              if (upper == null)
                upper = this.ceiling_za3lpa$(i - 12);
              var lower = this.floor_za3lpa$(i);
              if (lower == null)
                lower = this.floor_za3lpa$(i + 12);
              var chromatic = this.root.tone;
              var degree = 0;
              var lowerDegree = null;
              var upperDegree = null;
              while (lowerDegree == null || upperDegree == null) {
                if (this.contains_za3lpa$(chromatic))
                  degree += 1;
                if (chromatic === lower)
                  lowerDegree = degree;
                if (chromatic === upper)
                  upperDegree = degree;
                chromatic += 1;
                chromatic = _.com.jonlatane.libharmony.Modulus.Companion.TWELVETONE.mod_za3lpa$(chromatic);
              }
              return new Kotlin.kotlin.Pair(lowerDegree, upperDegree);
            },
            isMajor: {
              get: function () {
                return this.isMajor_usz75w$_0;
              }
            },
            isMinor: {
              get: function () {
                return this.isMinor_usz75w$_0;
              }
            }
          }, /** @lends _.com.jonlatane.libharmony.Scale */ {
          }),
          MajorScale: Kotlin.createClass(function () {
            return [_.com.jonlatane.libharmony.Scale];
          }, function MajorScale(root) {
            MajorScale.baseInitializer.call(this, root);
            this.add_za3rmp$(root);
            this.add_za3rmp$(root.plus_za3lpa$(2));
            this.add_za3rmp$(root.plus_za3lpa$(4));
            this.add_za3rmp$(root.plus_za3lpa$(5));
            this.add_za3rmp$(root.plus_za3lpa$(7));
            this.add_za3rmp$(root.plus_za3lpa$(9));
            this.add_za3rmp$(root.plus_za3lpa$(11));
            this.isMajor_x1p71x$_0 = true;
          }, /** @lends _.com.jonlatane.libharmony.MajorScale.prototype */ {
            isMajor: {
              get: function () {
                return this.isMajor_x1p71x$_0;
              }
            }
          }),
          NaturalMinorScale: Kotlin.createClass(function () {
            return [_.com.jonlatane.libharmony.Scale];
          }, function NaturalMinorScale(root) {
            NaturalMinorScale.baseInitializer.call(this, root);
            this.add_za3rmp$(root);
            this.add_za3rmp$(root.plus_za3lpa$(2));
            this.add_za3rmp$(root.plus_za3lpa$(3));
            this.add_za3rmp$(root.plus_za3lpa$(5));
            this.add_za3rmp$(root.plus_za3lpa$(7));
            this.add_za3rmp$(root.plus_za3lpa$(8));
            this.add_za3rmp$(root.plus_za3lpa$(10));
            this.isMinor_md48j4$_0 = true;
          }, /** @lends _.com.jonlatane.libharmony.NaturalMinorScale.prototype */ {
            isMinor: {
              get: function () {
                return this.isMinor_md48j4$_0;
              }
            }
          }),
          HarmonicMinorScale: Kotlin.createClass(function () {
            return [_.com.jonlatane.libharmony.Scale];
          }, function HarmonicMinorScale(root) {
            HarmonicMinorScale.baseInitializer.call(this, root);
            this.add_za3rmp$(root);
            this.add_za3rmp$(root.plus_za3lpa$(2));
            this.add_za3rmp$(root.plus_za3lpa$(3));
            this.add_za3rmp$(root.plus_za3lpa$(5));
            this.add_za3rmp$(root.plus_za3lpa$(7));
            this.add_za3rmp$(root.plus_za3lpa$(8));
            this.add_za3rmp$(root.plus_za3lpa$(11));
          }, /** @lends _.com.jonlatane.libharmony.HarmonicMinorScale.prototype */ {
            isMinor: {
              get: function () {
                return false;
              }
            }
          }, /** @lends _.com.jonlatane.libharmony.HarmonicMinorScale */ {
            Companion: Kotlin.createObject(null, function Companion() {
              _.com.jonlatane.libharmony.HarmonicMinorScale.Companion.serialVersionUID_0 = new Kotlin.Long(-2025552534, 301048545);
            }),
            object_initializer$: function () {
              _.com.jonlatane.libharmony.HarmonicMinorScale.Companion;
            }
          }),
          MelodicMinorScale: Kotlin.createClass(function () {
            return [_.com.jonlatane.libharmony.Scale];
          }, function MelodicMinorScale(root) {
            MelodicMinorScale.baseInitializer.call(this, root);
            this.descendingVersion_ybo1au$_0 = new _.com.jonlatane.libharmony.Scale(root);
            this.add_za3rmp$(root);
            this.add_za3rmp$(root.plus_za3lpa$(2));
            this.add_za3rmp$(root.plus_za3lpa$(3));
            this.add_za3rmp$(root.plus_za3lpa$(5));
            this.add_za3rmp$(root.plus_za3lpa$(7));
            this.add_za3rmp$(root.plus_za3lpa$(9));
            this.add_za3rmp$(root.plus_za3lpa$(11));
            this.descendingVersion.add_za3rmp$(root);
            this.descendingVersion.add_za3rmp$(root.plus_za3lpa$(2));
            this.descendingVersion.add_za3rmp$(root.plus_za3lpa$(3));
            this.descendingVersion.add_za3rmp$(root.plus_za3lpa$(5));
            this.descendingVersion.add_za3rmp$(root.plus_za3lpa$(7));
            this.descendingVersion.add_za3rmp$(root.plus_za3lpa$(8));
            this.descendingVersion.add_za3rmp$(root.plus_za3lpa$(11));
            this.isMinor_ybo1au$_0 = true;
          }, /** @lends _.com.jonlatane.libharmony.MelodicMinorScale.prototype */ {
            descendingVersion: {
              get: function () {
                return this.descendingVersion_ybo1au$_0;
              }
            },
            isMinor: {
              get: function () {
                return this.isMinor_ybo1au$_0;
              }
            }
          }),
          ChromaticScale: Kotlin.createClass(function () {
            return [_.com.jonlatane.libharmony.Scale];
          }, function ChromaticScale(root) {
            ChromaticScale.baseInitializer.call(this, root);
            var tmp$0, tmp$1, tmp$2, tmp$3;
            this.add_za3rmp$(root);
            tmp$0 = new Kotlin.NumberRange(1, 11);
            tmp$1 = tmp$0.first;
            tmp$2 = tmp$0.last;
            tmp$3 = tmp$0.step;
            for (var i = tmp$1; i <= tmp$2; i += tmp$3) {
              this.add_za3rmp$(root.plus_za3lpa$(i));
            }
          })
        })
      })
    })
  });
  Kotlin.defineModule('libharmony-0.2', _);
  return _;
}(kotlin);

//@ sourceMappingURL=libharmony-0.2.js.map
