package org.opensearch.index.common.util

import org.opensearch.index.common.type.CodeType

/*
* Licensed to Elasticsearch B.V. under one or more contributor
* license agreements. See the NOTICE file distributed with
* this work for additional information regarding copyright
* ownership. Elasticsearch B.V. licenses this file to you under
* the Apache License, Version 2.0 (the "License"); you may
* not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/
object KeyboardUtil {
    /**
     * Converter 진행시 무시되는 문자들
     */
    const val IGNORE_CHAR = "`1234567890-=[]\\;',./~!@#$%^&*()_+{}|:\"<>?\' \' "

    /**
     * 초성 키에 해당하는 키보드상의 영문자 (19자)
     */
    val KEYBOARD_CHO_SUNG = arrayOf(
        "r", "R", "s", "e", "E", "f", "a", "q", "Q", "t",
        "T", "d", "w", "W", "c", "z", "x", "v", "g",
    )

    /**
     * 중성 키에 해당하는 키보스상의 영문자 (21자)
     */
    val KEYBOARD_JUNG_SUNG = arrayOf(
        "k", "o", "i", "O", "j", "p", "u", "P", "h", "hk",
        "ho", "hl", "y", "n", "nj", "np", "nl", "b", "m", "ml", "l",
    )

    /**
     * 종성 키에 해당하는 키보드상의 영문자 (27자) - "빈값" 제외
     */
    val KEYBOARD_JONG_SUNG = arrayOf(
        "r", "R", "rt", "s", "sw", "sg", "e", "f", "fr", "fa",
        "fq", "ft", "fx", "fv", "fg", "a", "q", "qt", "t", "T",
        "d", "w", "c", "z", "x", "v", "g",
    )


    /**
     * 키보드상에서 한영키에 의해서 오타 교정이 필요한 키배열 (영문키 33자)
     */
    val KEYBOARD_KEY_ENG = arrayOf(
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
        "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
        "u", "v", "w", "x", "y", "z", "Q", "W", "E", "R",
        "T", "O", "P",
    )

    /**
     * 키보드상에서 한영키에 의해서 오타 교정이 필요한 키배열 (한글키 33자)
     */
    val KEYBOARD_KEY_KOR = arrayOf(
        "ㅁ", "ㅠ", "ㅊ", "ㅇ", "ㄷ", "ㄹ", "ㅎ", "ㅗ", "ㅑ", "ㅓ",
        "ㅏ", "ㅣ", "ㅡ", "ㅜ", "ㅐ", "ㅔ", "ㅂ", "ㄱ", "ㄴ", "ㅅ",
        "ㅕ", "ㅍ", "ㅈ", "ㅌ", "ㅛ", "ㅋ", "ㅃ", "ㅉ", "ㄸ", "ㄲ",
        "ㅆ", "ㅒ", "ㅖ",
    )

    val DOUBLE_JAMO_MAP = mapOf(
        "ㅘ" to "ㅗㅏ",
        "ㅙ" to "ㅗㅐ",
        "ㅚ" to "ㅗㅣ",
        "ㅝ" to "ㅜㅓ",
        "ㅞ" to "ㅜㅔ",
        "ㅟ" to "ㅜㅣ",
        "ㅢ" to "ㅡㅣ",
    )


    /**
     * 초성 정보를 제공한다.
     *
     *
     * - 초성과 매칭된 코드 조회
     * - 한 자로 이루어진 초성코드만 존재한다.
     */
    fun getInfoForChoSung(index: Int, word: String): Map<String, Int> =
        mapOf(
            "code" to makeUnicodeIndex(CodeType.CHOSUNG, word[index].toString()),
            "idx" to index + 1,
        )



    /**
     * 중성 정보를 제공한다.
     *
     *
     * - 중성과 매칭된 코드 조회
     * - 두 자로 이루어진 중성코드가 존재한다.
     */
    fun getInfoForJungSung(index: Int, word: String): Map<String, Int> {
        var code = getDoubleMedial(index, word)
        var idx = index + 2

        if (code == -1) {
            code = getSingleMedial(index, word)
            idx = index + 1
        }

        return mapOf(
            "code" to code,
            "idx" to idx
        )
    }


    /**
     * 종성 정보를 제공한다.
     *
     *
     * - 종성과 매칭된 코드 조회
     * - 두 자로 이루어진 종성코드가 존재한다.
     */
    fun getInfoForJongSung(index: Int, word: String): Map<String, Int> {
        var code: Int
        var idx = index

        var temp = getDoubleFinal(idx, word)
        if (-1 == temp) {
            temp = getSingleMedial(idx + 1, word)
            if (temp != -1) {
                code = 0
                idx--
            } else {
                code = getSingleFinal(idx, word)
                if (code == -1) {
                    code = 0
                    idx--
                }
            }
        } else {
            code = temp
            temp = getSingleMedial(idx + 2, word)
            if (temp != -1) {
                code = getSingleFinal(idx, word)
            } else {
                idx++
            }
        }

        return mapOf(
            "code" to code,
            "idx" to idx
        )
    }


    /**
     * 1자로 구성된 중성 유니코드 Index를 리턴한다.
     */
    private fun getSingleMedial(index: Int, word: String): Int =
        if ((index + 1) <= word.length) {
            makeUnicodeIndex(CodeType.JUNGSUNG, word[index].toString())
        } else {
            -1
        }

    /**
     * 2자로 구성된 중성 유니코드 Index를 리턴한다.
     */
    private fun getDoubleMedial(index: Int, word: String): Int =
        if ((index + 2) > word.length) {
            -1
        } else {
            makeUnicodeIndex(CodeType.JUNGSUNG, word.substring(index, index + 2))
        }

    /**
     * 1자로 구성된 종성 유니코드 Index를 리턴한다.
     */
    private fun getSingleFinal(index: Int, word: String): Int =
        if ((index + 1) <= word.length) {
            makeUnicodeIndex(CodeType.JONGSUNG, word[index].toString())
        } else {
            -1
        }

    /**
     * 2자로 구성된 종성 유니코드 Index를 리턴한다.
     */
    private fun getDoubleFinal(index: Int, word: String): Int =
        if ((index + 2) > word.length) {
            -1
        } else {
            makeUnicodeIndex(CodeType.JONGSUNG, word.substring(index, index + 2))
        }


    /**
     * 키보드상에 매칭된 유니코드값 Index를 리턴한다.
     */
    private fun makeUnicodeIndex(type: CodeType, subStr: String): Int {
        when (type) {
            CodeType.CHOSUNG -> {
                for (i in KEYBOARD_CHO_SUNG.indices) {
                    if (KEYBOARD_CHO_SUNG[i] == subStr) {
                        return i * 28 * 21
                    }
                }
            }

            CodeType.JUNGSUNG -> {
                for (i in KEYBOARD_JUNG_SUNG.indices) {
                    if (KEYBOARD_JUNG_SUNG[i] == subStr) {
                        return i * 28
                    }
                }
            }

            CodeType.JONGSUNG -> {
                for (i in KEYBOARD_JONG_SUNG.indices) {
                    if (KEYBOARD_JONG_SUNG[i] == subStr) {
                        return i + 1
                    }
                }
            }
        }
        return -1
    }
}



















