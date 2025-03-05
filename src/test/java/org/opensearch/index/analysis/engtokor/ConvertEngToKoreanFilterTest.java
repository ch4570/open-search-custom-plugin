package org.opensearch.index.analysis.engtokor;

import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.opensearch.index.analysis.utils.AnalyzeUtils.*;
import static org.opensearch.index.analysis.utils.AnalyzerFactory.createAnalyzer;
import static org.opensearch.index.analysis.utils.AnalyzerType.CONVERT_ENG_TO_KOREAN;

public class ConvertEngToKoreanFilterTest {

    private final Analyzer analyzer = createAnalyzer(CONVERT_ENG_TO_KOREAN);

    @Test
    public void convertEngToKoreanFilter() {
        assertEquals("삼성전자", analyze("tkatjdwjswk", analyzer));
        assertEquals("삼성@!$전@자", analyze("tkatjd@!$wjs@wk", analyzer));
        assertEquals("기획서 안내", analyze("rlghlrtj dksso", analyzer));
        assertEquals("우리의 소원은 통일", analyze("dnfldml thdnjsdms xhddlf", analyzer));
    }
}
