package org.opensearch.index.common.converter

import org.opensearch.index.common.type.CodeType
import org.opensearch.index.common.util.JamoUtil
import org.opensearch.index.common.util.KeyboardUtil

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
class KorToEngConverter : Converter {
    /**
     * 토큰을 한글 키보드 기준으로 변환한다.
     *
     */
    override fun convert(token: String): String {
        val sb = StringBuilder()

        // 문자열을 한글자씩 잘라서 처리한다.
        val word = token.trim()
        for (index in word.indices) {
            // 이중모음 분해 처리
            val currentChar = word[index].toString()

            if (KeyboardUtil.DOUBLE_JAMO_MAP.containsKey(currentChar)) {
                val doubleJamo = KeyboardUtil.DOUBLE_JAMO_MAP[currentChar]!!

                for (element in doubleJamo) {
                    val jamo = element.toString()
                    sb.append(getSameEngCharForJamo(jamo))
                }
            }

            // 처리 불가능한 글자는 그냥 넘긴다.
            if (KeyboardUtil.IGNORE_CHAR.contains(word[index])) {
                sb.append(word[index])
                continue
            }

            try {
                val init = word[index]
                val initUnicode = init - JamoUtil.START_KOREA_UNICODE

                if (initUnicode > 0) {
                    /**
                     * 1글자로 조합형 한글이 들어올 경우 처리
                     */
                    val choSung = initUnicode / 21 / 28 // 0 ~ 18
                    val strChoSung = getSameEngChar(CodeType.CHOSUNG, choSung)
                    if (strChoSung.isNotEmpty()) {
                        sb.append(strChoSung)
                    }


                    val jungSung = initUnicode / 28 % 21 // 0 ~ 20
                    val strJungSung = getSameEngChar(CodeType.JUNGSUNG, jungSung)
                    if (strJungSung.isNotEmpty()) {
                        sb.append(strJungSung)
                    }

                    val jongSung = initUnicode % 28 // 0 ~ 27
                    val strJongSung = getSameEngChar(CodeType.JONGSUNG, jongSung)
                    if (strJongSung.isNotEmpty()) {
                        sb.append(strJongSung)
                    }
                } else {
                    /**
                     * 1글자로 자모가 들어올 경우 처리
                     */
                    val singularJamo = init.toString()
                    sb.append(getSameEngCharForJamo(singularJamo))
                }
            } catch (ignored: Exception) {
            }
        }

        return sb.toString()
    }

    private fun getSameEngChar(type: CodeType, pos: Int): String =
        when (type) {
            CodeType.CHOSUNG -> KeyboardUtil.KEYBOARD_CHO_SUNG[pos]
            CodeType.JUNGSUNG -> KeyboardUtil.KEYBOARD_JUNG_SUNG[pos]
            CodeType.JONGSUNG -> {
                if ((pos - 1) > -1) {
                    KeyboardUtil.KEYBOARD_JONG_SUNG[pos - 1]
                } else {
                    ""
                }
            }
        }
    }


    private fun getSameEngCharForJamo(key: String): String {
        for (index in KeyboardUtil.KEYBOARD_KEY_KOR.indices) {
            if (KeyboardUtil.KEYBOARD_KEY_KOR[index] == key) {
                return KeyboardUtil.KEYBOARD_KEY_ENG[index]
            }
        }

        return ""
    }
