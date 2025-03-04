package org.opensearch.index.analysis.kortoeng;


import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;
import org.opensearch.index.analysis.utils.AnalyzerFactory;

import static org.junit.Assert.assertEquals;
import static org.opensearch.index.analysis.utils.AnalyzeUtils.analyze;
import static org.opensearch.index.analysis.utils.AnalyzerType.CONVERT_KOREAN_TO_ENG;

public class ConvertKoreanToEngFilterTest {
    private final Analyzer analyzer = AnalyzerFactory.createAnalyzer(CONVERT_KOREAN_TO_ENG);

    @Test
    public void convertKoreanToEngFilterTest() {
        assertEquals("iphone", analyze("ㅑㅔㅙㅜㄷ", analyzer));
        assertEquals("mlOps", analyze("ㅢㅒㅔㄴ", analyzer));
        assertEquals("coupang", analyze("채ㅕㅔ뭏", analyzer));
        assertEquals("neo!# ple", analyze("ㅜ대!# ㅔㅣㄷ", analyzer));
    }
}
