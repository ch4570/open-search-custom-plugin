package org.opensearch.index.analysis.chosung

import org.junit.Assert.assertEquals
import org.junit.Test
import org.opensearch.index.analysis.utils.AnalyzeUtils
import org.opensearch.index.analysis.utils.AnalyzerFactory.createAnalyzer

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
class ChosungFilterTest {
    private val analyzer = createAnalyzer(::ChosungFilter)

    @Test
    fun testChosungTokenFilter() {
        assertEquals("ㅇㅍ ㅅㅊ", AnalyzeUtils.analyze("오픈 서치", analyzer))
        assertEquals("ㅋㅍ ㅍㄹㅇ", AnalyzeUtils.analyze("쿠팡 플레이", analyzer))
        assertEquals("[ㅋㅍ]-ㅍㄹㅇ!@#!#ㄱㅈ ㅇㄹ", AnalyzeUtils.analyze("[쿠팡]-플레이!@#!#결제 알림", analyzer))
        assertEquals("ㅋㅍㅍㄹㅇ", AnalyzeUtils.analyze("ㅋㅍ프레ㅇ", analyzer))
    }
}
