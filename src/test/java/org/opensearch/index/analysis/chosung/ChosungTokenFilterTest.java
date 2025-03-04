package org.opensearch.index.analysis.chosung;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.KeywordTokenizer;
import org.junit.Test;
import org.opensearch.index.analysis.utils.AnalyzeUtils;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ChosungTokenFilterTest {

    private final Analyzer analyzer = new Analyzer(Analyzer.PER_FIELD_REUSE_STRATEGY) {
        @Override
        protected TokenStreamComponents createComponents(String fieldName) {
            try (Tokenizer tokenizer = new KeywordTokenizer()) {
                TokenStream tokenFilter = new ChosungTokenFilter(tokenizer);

                return new TokenStreamComponents(tokenizer, tokenFilter);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    };

    @Test
    public void testChosungTokenFilter() {
        assertEquals("ㅇㅍ ㅅㅊ", AnalyzeUtils.analyze("오픈 서치", analyzer));
        assertEquals("ㅋㅍ ㅍㄹㅇ", AnalyzeUtils.analyze("쿠팡 플레이", analyzer));
        assertEquals("[ㅋㅍ]-ㅍㄹㅇ!@#!#ㄱㅈ ㅇㄹ", AnalyzeUtils.analyze("[쿠팡]-플레이!@#!#결제 알림", analyzer));
        assertEquals("ㅋㅍㅍㄹㅇ", AnalyzeUtils.analyze("ㅋㅍ프레ㅇ", analyzer));
    }
}
