package org.opensearch.index.analysis.chosung;

import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;
import org.opensearch.index.analysis.utils.AnalyzeUtils;
import org.opensearch.index.analysis.utils.AnalyzerFactory;
import org.opensearch.index.analysis.utils.AnalyzerType;

import static org.junit.Assert.assertEquals;

public class ChosungTokenFilterTest {

    private final Analyzer analyzer = AnalyzerFactory.createAnalyzer(AnalyzerType.CHOSUNG_TOKEN);

    @Test
    public void testChosungTokenFilter() {
        assertEquals("ㅇㅍ ㅅㅊ", AnalyzeUtils.analyze("오픈 서치", analyzer));
        assertEquals("ㅋㅍ ㅍㄹㅇ", AnalyzeUtils.analyze("쿠팡 플레이", analyzer));
        assertEquals("[ㅋㅍ]-ㅍㄹㅇ!@#!#ㄱㅈ ㅇㄹ", AnalyzeUtils.analyze("[쿠팡]-플레이!@#!#결제 알림", analyzer));
        assertEquals("ㅋㅍㅍㄹㅇ", AnalyzeUtils.analyze("ㅋㅍ프레ㅇ", analyzer));
    }
}
