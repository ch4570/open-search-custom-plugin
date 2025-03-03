package org.opensearch.index.common.converter;

import org.apache.commons.lang3.StringUtils;
import org.opensearch.index.common.type.CodeType;
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

public final class KorToEngConverter {
    /**
     * 토큰을 한글 키보드 기준으로 변환한다.
     *
     */
    public String convert(String token) {
        final StringBuilder sb = new StringBuilder();

        // 문자열을 한글자씩 잘라서 처리한다.
        String word = token.trim();
        for (int index = 0; index < word.length(); index++) {
            // 이중모음 분해 처리
            String currentChar = String.valueOf(word.charAt(index));

            if (KeyboardUtil.DOUBLE_JAMO_MAP.containsKey(currentChar)) {
                String decomposed = KeyboardUtil.DOUBLE_JAMO_MAP.get(currentChar);

                for (int j = 0; j < decomposed.length(); j++) {
                    String jamo = String.valueOf(decomposed.charAt(j));
                    sb.append(getSameEngCharForJamo(jamo));
                }
            }

            // 처리 불가능한 글자는 그냥 넘긴다.
            if (KeyboardUtil.IGNORE_CHAR.contains(word.substring(index, index + 1))) {
                sb.append(word.charAt(index));
                continue;
            }

            try {
                final int init = word.charAt(index);
                final int initUnicode = init - JamoUtil.START_KOREA_UNICODE;

                if (initUnicode > 0) {
                    /**
                     * 1글자로 조합형 한글이 들어올 경우 처리
                     */
                    final int cho  = initUnicode / 21 / 28;   // 0 ~ 18
                    final String strCho = getSameEngChar(CodeType.CHOSUNG, cho);
                    if (StringUtils.isNotEmpty(strCho)) {
                        sb.append(strCho);
                    }


                    final int jung = initUnicode / 28 % 21;   // 0 ~ 20
                    final String strJung = getSameEngChar(CodeType.JUNGSUNG, jung);
                    if (StringUtils.isNotEmpty(strJung)) {
                        sb.append(strJung);
                    }

                    final int jong = initUnicode % 28;        // 0 ~ 27
                    final String strJong = getSameEngChar(CodeType.JONGSUNG, jong);
                    if (StringUtils.isNotEmpty(strJong)) {
                        sb.append(strJong);
                    }

                } else {
                    /**
                     * 1글자로 자모가 들어올 경우 처리
                     */
                    final String subStr = String.valueOf((char) init);
                    sb.append(getSameEngCharForJamo(subStr));
                }
            } catch(Exception ignored) {}
        }

        return sb.toString();
    }

    private String getSameEngChar(CodeType type, int pos) {
        return switch (type) {
            case CHOSUNG -> KeyboardUtil.KEYBOARD_CHO_SUNG[pos];
            case JUNGSUNG -> KeyboardUtil.KEYBOARD_JUNG_SUNG[pos];
            case JONGSUNG -> {
                if ((pos - 1) > -1) {
                    yield KeyboardUtil.KEYBOARD_JONG_SUNG[pos - 1];
                }
                yield "";
            }
        };
    }


    private String getSameEngCharForJamo(String key) {
        for (int i=0; i<KeyboardUtil.KEYBOARD_KEY_KOR.length; i++) {
            if (KeyboardUtil.KEYBOARD_KEY_KOR[i].equals(key)) {
                return KeyboardUtil.KEYBOARD_KEY_ENG[i];
            }
        }

        return "";
    }
}












