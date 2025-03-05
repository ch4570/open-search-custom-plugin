package org.opensearch.index.common.converter

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
class EngToKorConverter : Converter {
    /**
     * 토큰을 영문 키보드 기준으로 변환한다.
     *
     */
    override fun convert(token: String): String {
        val builder = StringBuilder()

        // 문자열을 한글자씩 잘라서 처리한다.
        val word = token.trim()
        var index = 0

        while (index < word.length) {
            // 처리 불가능한 글자는 그냥 넘긴다.
            if (KeyboardUtil.IGNORE_CHAR.contains(word.substring(index, index + 1))) {
                builder.append(word[index])
                index++
                continue
            }

            try {
                // 초성 정보를 구한다.
                val choSungMap = KeyboardUtil.getInfoForChoSung(index, word)
                val choSung = choSungMap["code"]!!
                index = choSungMap["idx"]!!

                // 중성 정보를 구한다.
                val jungSungMap = KeyboardUtil.getInfoForJungSung(index, word)
                val jungSung = jungSungMap["code"]!!
                index = jungSungMap["idx"]!!

                // 종성 정보를 구한다.
                val jongSungMap = KeyboardUtil.getInfoForJongSung(index, word)
                val jongSung = jongSungMap["code"]!!
                index = jongSungMap["idx"]!!

                // 한글 유니코드를 생성한다.
                builder.append((JamoUtil.START_KOREA_UNICODE.code + choSung + jungSung + jongSung).toChar())
            } catch (ignored: Exception) {}
            index++
        }

        return builder.toString()
    }
}
