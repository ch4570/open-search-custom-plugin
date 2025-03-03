package org.opensearch.index.common.converter;

import java.util.Map;

import org.opensearch.index.common.util.JamoUtil;
import org.opensearch.index.common.util.KeyboardUtil;

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

public final class EngToKorConverter {


    /**
     * 토큰을 영문 키보드 기준으로 변환한다.
     *
     */
    public String convert(String token) {
        final StringBuilder sb = new StringBuilder();

        // 문자열을 한글자씩 잘라서 처리한다.
        final String word = token.trim();
        for (int index = 0; index < word.length(); index++) {

            // 처리 불가능한 글자는 그냥 넘긴다.
            if (KeyboardUtil.IGNORE_CHAR.contains(word.substring(index, index + 1))) {
                sb.append(word.charAt(index));
                continue;
            }

            try {
                // 초성 정보를 구한다.
                final Map<String, Integer> mChoSung = KeyboardUtil.getInfoForChoSung(index, word);
                final int cho = mChoSung.get("code");
                index = mChoSung.get("idx");

                // 중성 정보를 구한다.
                final Map<String, Integer> mJungSung = KeyboardUtil.getInfoForJungSung(index, word);
                final int jung = mJungSung.get("code");
                index = mJungSung.get("idx");

                // 종성 정보를 구한다.
                final Map<String, Integer> mJongSung = KeyboardUtil.getInfoForJongSung(index, word);
                final int jong = mJongSung.get("code");
                index = mJongSung.get("idx");

                // 한글 유니코드를 생성한다.
                sb.append((char) (JamoUtil.START_KOREA_UNICODE + cho + jung + jong));

            } catch(Exception ignored) {}
        }

        return sb.toString();
    }







}
