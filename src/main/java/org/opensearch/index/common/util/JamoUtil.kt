package org.opensearch.index.common.util

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

object JamoUtil {
        /**
         * 초성 (19자)<br></br>
         * <br></br>
         * 초성으로 올 수 있는 유니코드들<br></br>
         * 총 19자로 구성된다.<br></br>
         * <br></br>
         * ㄱ ㄲ ㄴ ㄷ ㄸ ㄹ ㅁ ㅂ ㅃ ㅅ <br></br>
         * ㅆ ㅇ ㅈ ㅉ ㅊ ㅋ ㅌ ㅍ ㅎ<br></br>
         *
         */
        val UNICODE_CHO_SUNG: CharArray = charArrayOf(
            0x3131.toChar(),
            0x3132.toChar(),
            0x3134.toChar(),
            0x3137.toChar(),
            0x3138.toChar(),
            0x3139.toChar(),
            0x3141.toChar(),
            0x3142.toChar(),
            0x3143.toChar(),
            0x3145.toChar(),
            0x3146.toChar(),
            0x3147.toChar(),
            0x3148.toChar(),
            0x3149.toChar(),
            0x314A.toChar(),
            0x314B.toChar(),
            0x314C.toChar(),
            0x314D.toChar(),
            0x314E.toChar()
        )


        /**
         * 중성 (21자)<br></br>
         * <br></br>
         * 중성으로 올 수 있는 유니코드들<br></br>
         * 총 21자로 구성된다.<br></br>
         * <br></br>
         * ㅏ ㅐ ㅑ ㅒ ㅓ ㅔ ㅕ ㅖ ㅗ ㅘ <br></br>
         * ㅙ ㅚ ㅛ ㅜ ㅝ ㅞ ㅟ ㅠ ㅡ ㅢ <br></br>
         * ㅣ<br></br>
         *
         */
        val UNICODE_JUNG_SUNG: CharArray = charArrayOf(
            0x314F.toChar(),
            0x3150.toChar(),
            0x3151.toChar(),
            0x3152.toChar(),
            0x3153.toChar(),
            0x3154.toChar(),
            0x3155.toChar(),
            0x3156.toChar(),
            0x3157.toChar(),
            0x3158.toChar(),
            0x3159.toChar(),
            0x315A.toChar(),
            0x315B.toChar(),
            0x315C.toChar(),
            0x315D.toChar(),
            0x315E.toChar(),
            0x315F.toChar(),
            0x3160.toChar(),
            0x3161.toChar(),
            0x3162.toChar(),
            0x3163.toChar()
        )


        /**
         * 종성 (28자)<br></br>
         * <br></br>
         * 종성으로 올 수 있는 유니코드들<br></br>
         * 기본 27자와 "빈값"을 표현하는 1자를 합쳐서 총 28자로 구성된다.<br></br>
         * <br></br>
         * 빈값 ㄱ ㄲ ㄳ ㄴ ㄵ ㄶ ㄷ ㄹ ㄺ <br></br>
         * ㄻ ㄼ ㄽ ㄾ ㄿ ㅀ ㅁ ㅂ ㅄ ㅅ <br></br>
         * ㅆ ㅇ ㅈ ㅊ ㅋ ㅌ ㅍ ㅎ<br></br>
         *
         */
        val UNICODE_JONG_SUNG: CharArray = charArrayOf(
            0x0000.toChar(),
            0x3131.toChar(),
            0x3132.toChar(),
            0x3133.toChar(),
            0x3134.toChar(),
            0x3135.toChar(),
            0x3136.toChar(),
            0x3137.toChar(),
            0x3139.toChar(),
            0x313A.toChar(),
            0x313B.toChar(),
            0x313C.toChar(),
            0x313D.toChar(),
            0x313E.toChar(),
            0x313F.toChar(),
            0x3140.toChar(),
            0x3141.toChar(),
            0x3142.toChar(),
            0x3144.toChar(),
            0x3145.toChar(),
            0x3146.toChar(),
            0x3147.toChar(),
            0x3148.toChar(),
            0x314A.toChar(),
            0x314B.toChar(),
            0x314C.toChar(),
            0x314D.toChar(),
            0x314E.toChar()
        )


        /**
         * 한글 유니코드의 시작값 (가)<br></br>
         * <br></br>
         * 한글 유니코드는 0xAC00로 시작하여 0xD79F로 끝난다.<br></br>
         * 시작값과 끝값을 벗어난 유니코드는 한글이 아니다.<br></br>
         * <br></br>
         * 시작값 : 0xAC00 가<br></br>
         * 끝값   : 0xD79F 힟<br></br>
         */
        const val START_KOREA_UNICODE: Char = 0xAC00.toChar()


        /**
         * 종성 빈값 유니코드
         */
        const val UNICODE_JONG_SUNG_EMPTY: Char = 0x0000.toChar()
}



















