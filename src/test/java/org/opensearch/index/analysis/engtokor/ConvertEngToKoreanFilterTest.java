package org.opensearch.index.analysis.engtokor;

import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;
import org.opensearch.index.analysis.utils.AnalyzeUtils;
import org.opensearch.index.analysis.utils.AnalyzerFactory;
import org.opensearch.index.analysis.utils.AnalyzerType;


import static org.junit.Assert.assertEquals;

public class ConvertEngToKoreanFilterTest {

    private final Analyzer analyzer = AnalyzerFactory.createAnalyzer(AnalyzerType.CONVERT_ENG_TO_KOREAN);

    @Test
    public void convertEngToKoreanFilter() {
        assertEquals("삼성전자", AnalyzeUtils.analyze("tkatjdwjswk", analyzer));
        assertEquals("기획서 안내", AnalyzeUtils.analyze("rlghlrtj dksso", analyzer));
        assertEquals("우리의 소원은 통일", AnalyzeUtils.analyze("dnfldml thdnjsdms xhddlf", analyzer));
    }
}
