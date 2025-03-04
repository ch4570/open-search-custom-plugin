package org.opensearch.index.analysis.chosung;

import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.opensearch.index.analysis.utils.AnalyzeUtils.*;
import static org.opensearch.index.analysis.utils.AnalyzerFactory.*;
import static org.opensearch.index.analysis.utils.AnalyzerType.*;

public class ChosungTokenFilterTest {

    private final Analyzer analyzer = createAnalyzer(CHOSUNG_TOKEN);

    @Test
    public void testChosungTokenFilter() {
        assertEquals("ㅇㅍ ㅅㅊ", analyze("오픈 서치", analyzer));
        assertEquals("ㅋㅍ ㅍㄹㅇ", analyze("쿠팡 플레이", analyzer));
        assertEquals("[ㅋㅍ]-ㅍㄹㅇ!@#!#ㄱㅈ ㅇㄹ", analyze("[쿠팡]-플레이!@#!#결제 알림", analyzer));
        assertEquals("ㅋㅍㅍㄹㅇ", analyze("ㅋㅍ프레ㅇ", analyzer));
    }
}
