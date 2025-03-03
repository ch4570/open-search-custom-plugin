package org.opensearch.index.common.parser;

import org.apache.commons.lang3.StringUtils;
import org.opensearch.index.common.util.JamoUtil;

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

public abstract class AbstractKoreanParser {
    /**
     * 토큰을 자음과 모음으로 파싱한다.
     *
     */
    public String parse(String token) {
        if (StringUtils.isBlank(token)) {
            return "";
        }

        final StringBuilder result = new StringBuilder();

        // 토큰을 한글자씩 잘라서 처리한다.
        char[] arrCh = token.toCharArray();
        for(char ch : arrCh) {

            // 처리 할 char의 유니코드 인덱스를 구한다.
            final char unicodeIndex = (char)(ch - JamoUtil.START_KOREA_UNICODE);

            // 한글 유니코드 범위 : 0xAC00 ~ 0xD7AF (11184개)
            // 한글 유니코드인지 검사한다.
            if(unicodeIndex >= 0 && unicodeIndex <= 11184) {

                // 초성 유니코드
                final int idxChoSung = unicodeIndex / (28 * 21);
                final char chosung = JamoUtil.UNICODE_CHO_SUNG[idxChoSung];

                // 중성 유니코드
                final int idxJungSung = unicodeIndex % (28 * 21) / 28;
                final char jungsung = JamoUtil.UNICODE_JUNG_SUNG[idxJungSung];

                // 종성 유니코드
                final int idxJongSung = unicodeIndex % (28 * 21) % 28;
                final char jongsung = JamoUtil.UNICODE_JONG_SUNG[idxJongSung];

                // 한글 한글자를 처리한다.
                processForKoreanChar(result, chosung, jungsung, jongsung);

            } else {
                // 한글이 아닌 한글자를 처리한다.
                processForOther(result, ch);
            }
        }

        // 토큰을 분석한 최종 결과를 리턴한다.
        return result.toString();
    }


    /**
     * 한글 문자를 처리한다.
     *
     */
    protected abstract void processForKoreanChar(StringBuilder sb, char chosung, char jungsung, char jongsung);


    /**
     * 한글 문자를 제외한 일반 문자를 처리한다.
     *
     */
    protected abstract void processForOther(StringBuilder sb, char eachToken);
}
