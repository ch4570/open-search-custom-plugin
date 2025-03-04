package org.opensearch.index.analysis.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.KeywordTokenizer;
import org.opensearch.index.analysis.chosung.ChosungTokenFilter;
import org.opensearch.index.analysis.engtokor.ConvertEngToKoreanFilter;
import org.opensearch.index.analysis.jamo.JamoDecomposeTokenFilter;
import org.opensearch.index.analysis.kortoeng.ConvertKoreanToEngFilter;
import org.opensearch.index.analysis.spell.SpellFilter;

import java.io.IOException;

public abstract class AnalyzerFactory {

    private AnalyzerFactory() {
        throw new IllegalArgumentException("It's Util Class. Do not create instance");
    }

    public static Analyzer createAnalyzer(AnalyzerType type) {
        return new Analyzer(Analyzer.PER_FIELD_REUSE_STRATEGY) {
            @Override
            protected TokenStreamComponents createComponents(String fieldName) {
                try (Tokenizer tokenizer = new KeywordTokenizer()) {
                    TokenStream tokenFilter = switch (type) {
                        case CHOSUNG_TOKEN -> new ChosungTokenFilter(tokenizer);
                        case JAMO_DEOMPOSE -> new JamoDecomposeTokenFilter(tokenizer);
                        case CONVERT_ENG_TO_KOREAN -> new ConvertEngToKoreanFilter(tokenizer);
                        case CONVERT_KOREAN_TO_ENG -> new ConvertKoreanToEngFilter(tokenizer);
                        case SPELL_CHECK -> new SpellFilter(tokenizer);
                    };

                    return new TokenStreamComponents(tokenizer, tokenFilter);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
}
