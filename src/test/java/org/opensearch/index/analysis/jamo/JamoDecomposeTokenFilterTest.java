package org.opensearch.index.analysis.jamo;

import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.opensearch.index.analysis.utils.AnalyzeUtils.*;
import static org.opensearch.index.analysis.utils.AnalyzerFactory.*;
import static org.opensearch.index.analysis.utils.AnalyzerType.*;

public class JamoDecomposeTokenFilterTest {

    private final Analyzer analyzer = createAnalyzer(JAMO_DECOMPOSE);

    @Test
    public void jamoDecomposeTokenFilterTest() {
        assertEquals("ㅇㅗㅍㅡㄴㅅㅓㅊㅣ", analyze("오픈서치", analyzer));
        assertEquals("ㅌㅗㄴㅣㅇㅘㅇㅐㄴㄷㅣ", analyze("토니와앤디", analyzer));
        assertEquals("ㅋㅜㅍㅏㅇ", analyze("쿠팡", analyzer));
        assertEquals("ㅇㅗㅍㅡㄴ@#@#ㅅㅓㅊㅣ", analyze("오픈@#@#서치", analyzer));
    }
}
