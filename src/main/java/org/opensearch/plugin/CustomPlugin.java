package org.opensearch.plugin;

import java.util.HashMap;
import java.util.Map;

import org.opensearch.index.analysis.chosung.JavacafeChosungTokenFilterFactory;
import org.opensearch.index.analysis.eng2kor.JavacafeEng2KorConvertFilterFactory;
import org.opensearch.index.analysis.jamo.JavacafeJamoTokenFilterFactory;
import org.opensearch.index.analysis.kor2eng.JavacafeKor2EngConvertFilterFactory;
import org.opensearch.index.analysis.spell.JavacafeSpellFilterFactory;
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
        Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> extra = new HashMap<>();

        // (1) 한글 자모 분석 필터
        extra.put("javacafe_jamo", JavacafeJamoTokenFilterFactory::new);

        // (2) 한글 초성 분석 필터
        extra.put("javacafe_chosung", JavacafeChosungTokenFilterFactory::new);

        // (3) 영한 오타 변환 필터
        extra.put("javacafe_eng2kor", JavacafeEng2KorConvertFilterFactory::new);

        // (4) 한영 오타 변환 필터
        extra.put("javacafe_kor2eng", JavacafeKor2EngConvertFilterFactory::new);

        // (5) 한글 스펠링 체크 필터
        extra.put("javacafe_spell", JavacafeSpellFilterFactory::new);

        return extra;
    }
}



