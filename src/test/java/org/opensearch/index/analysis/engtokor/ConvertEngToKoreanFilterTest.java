package org.opensearch.index.analysis.engtokor;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.KeywordTokenizer;
import org.junit.Test;
import org.opensearch.index.analysis.utils.AnalyzeUtils;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ConvertEngToKoreanFilterTest {

    private final Analyzer analyzer = new Analyzer(Analyzer.PER_FIELD_REUSE_STRATEGY) {
        @Override
        protected TokenStreamComponents createComponents(String fieldName) {
            try (Tokenizer tokenizer = new KeywordTokenizer()) {
                TokenStream tokenFilter = new ConvertEngToKoreanFilter(tokenizer);

                return new TokenStreamComponents(tokenizer, tokenFilter);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    };

    @Test
    public void convertEngToKoreanFilter() {
        assertEquals("삼성전자", AnalyzeUtils.analyze("tkatjdwjswk", analyzer));
        assertEquals("기획서 안내", AnalyzeUtils.analyze("rlghlrtj dksso", analyzer));
        assertEquals("우리의 소원은 통일", AnalyzeUtils.analyze("dnfldml thdnjsdms xhddlf", analyzer));
    }
}
