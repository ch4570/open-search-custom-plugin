package org.opensearch.index.common.parser

import org.opensearch.index.common.util.JamoUtil

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
abstract class AbstractKoreanParser {
    /**
     * 토큰을 자음과 모음으로 파싱한다.
     *
     */
    fun parse(token: String): String {
        if (token.isBlank()) {
            return ""
        }

        val builder = StringBuilder()

        // 토큰을 한글자씩 잘라서 처리한다.
        for (char in token.toCharArray()) {
            // 처리 할 char의 유니코드 인덱스를 구한다.
            val unicodeIndex = (char - JamoUtil.START_KOREA_UNICODE)

            // 한글 유니코드 범위 : 0xAC00 ~ 0xD7AF (11184개)
            // 한글 유니코드인지 검사한다.
            if (unicodeIndex in 0..11184) {
                // 초성 유니코드
                val idxChoSung = unicodeIndex / (28 * 21)
                val choSung = JamoUtil.UNICODE_CHO_SUNG[idxChoSung]

                // 중성 유니코드
                val idxJungSung = unicodeIndex % (28 * 21) / 28
                val jungSung = JamoUtil.UNICODE_JUNG_SUNG[idxJungSung]

                // 종성 유니코드
                val idxJongSung = unicodeIndex % (28 * 21) % 28
                val jongSung = JamoUtil.UNICODE_JONG_SUNG[idxJongSung]

                // 한글 한글자를 처리한다.
                processForKoreanChar(builder, choSung, jungSung, jongSung)
            } else {
                // 한글이 아닌 한글자를 처리한다.
                processForOther(builder, char)
            }
        }

        // 토큰을 분석한 최종 결과를 리턴한다.
        return builder.toString()
    }


    /**
     * 한글 문자를 처리한다.
     *
     */
    protected abstract fun processForKoreanChar(sb: StringBuilder, chosung: Char, jungsung: Char, jongsung: Char)


    /**
     * 한글 문자를 제외한 일반 문자를 처리한다.
     *
     */
    protected abstract fun processForOther(sb: StringBuilder, eachToken: Char)
}
