package org.opensearch.plugin;

import java.util.Map;

import org.opensearch.index.analysis.chosung.ChosungTokenFilterFactory;
import org.opensearch.index.analysis.engtokor.ConvertEngToKoreanFilterFactory;
import org.opensearch.index.analysis.jamo.JamoDecomposeTokenFilterFactory;
import org.opensearch.index.analysis.kortoeng.ConvertKorToEngFilterFactory;
import org.opensearch.index.analysis.spell.SpellFilterFactory;
import org.opensearch.index.analysis.TokenFilterFactory;
import org.opensearch.indices.analysis.AnalysisModule;
import org.opensearch.plugins.AnalysisPlugin;
import org.opensearch.plugins.Plugin;

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

public class CustomPlugin extends Plugin implements AnalysisPlugin {

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
        return Map.of(
                // (1) 한글 자모 분석 필터
                "kor-jamo-filter", JamoDecomposeTokenFilterFactory::new,
                // (2) 한글 초성 분석 필터
                "kor-chosung-filter", ChosungTokenFilterFactory::new,
                // (3) 영한 오타 변환 필터
                "convert-eng-to-kor-filter", ConvertEngToKoreanFilterFactory::new,
                // (4) 한영 오타 변환 필터
                "convert-kor-to-eng-filter", ConvertKorToEngFilterFactory::new,
                // (5) 한글 스펠링 체크 필터
                "kor-spell-check-filter", SpellFilterFactory::new
        );
    }
}



