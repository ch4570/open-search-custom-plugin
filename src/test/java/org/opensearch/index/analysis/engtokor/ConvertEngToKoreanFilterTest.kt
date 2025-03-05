package org.opensearch.index.analysis.engtokor

import org.junit.Assert.assertEquals
import org.junit.Test
import org.opensearch.index.analysis.utils.AnalyzeUtils.analyze
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
class ConvertEngToKoreanFilterTest {
    private val analyzer = createAnalyzer(::ConvertEngToKoreanFilter)

    @Test
    fun convertEngToKoreanFilter() {
        assertEquals("삼성전자", analyze("tkatjdwjswk", analyzer))
        assertEquals("삼성@!\$전@자", analyze("tkatjd@!\$wjs@wk", analyzer))
        assertEquals("기획서 안내", analyze("rlghlrtj dksso", analyzer))
        assertEquals("우리의 소원은 통일", analyze("dnfldml thdnjsdms xhddlf", analyzer))
    }
}
