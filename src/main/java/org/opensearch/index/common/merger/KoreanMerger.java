package org.opensearch.index.common.merger;

import java.util.List;

import org.opensearch.index.common.util.HangulUtil;

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

public class KoreanMerger {

    /**
     * 자모 리스트를 합쳐서 한글로 변환한다.
     * @param jamoList
     * @return
     * @throws Exception
     */
    public String merge(List<String> jamoList) throws Exception {
        String result = "";

        if (jamoList.size() == 0) {
            return "";
        }

        int jungSungSize = HangulUtil.JUNG_SUNG.length;
        int jongSungSize = HangulUtil.JONG_SUNG.length;

        int startIdx = 0;
        while (true) {
            if (startIdx >= jamoList.size()) {
                break;
            }

            // 자모 리스트에서 한글 한글자에 해당하는 사이즈를 구한다.
            int oneHangulJamoSize = HangulUtil.getOneHangulJamoSize(startIdx, jamoList);
            if (oneHangulJamoSize == -1) {
                throw new Exception("한글은 최소 2개 이상의 유니코드 조합으로 이루어져야 합니다.");
            }

            // 한글 유니코드가 시작되는 Decimal값을 구한다.
            int decimalCode = HangulUtil.START_KOREA_UNICODE_DECIMAL;

            // 초성에 해당하는 Decimal값을 더한다.
            int chosungIdx = HangulUtil.getChoSungIndex(startIdx, jamoList);
            if (chosungIdx >= 0) {
                decimalCode = decimalCode + (jongSungSize * jungSungSize * chosungIdx);
            }

            // 중성에 해당하는 Decimal값을 더한다.
            int jungsungIdx = HangulUtil.getJungSungIndex(startIdx, jamoList);
            if (jungsungIdx >= 0) {
                decimalCode = decimalCode + (jongSungSize * jungsungIdx);
            }

            // 종성에 해당하는 Decimal값을 더한다.
            if (oneHangulJamoSize > 2) {
                int jongsungIdx = HangulUtil.getJongSungIndex(startIdx, jamoList);
                if (jongsungIdx >= 0) {
                    decimalCode = decimalCode + jongsungIdx;
                }
            }

            // Decimal값을 String으로 변환한다.
            String hangul = Character.toString((char)decimalCode);
            result = result + hangul;

            startIdx = startIdx + oneHangulJamoSize;
        }

        return result;
    }



}


