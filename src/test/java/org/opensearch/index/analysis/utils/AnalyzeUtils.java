package org.opensearch.index.analysis.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AnalyzeUtils {

    private AnalyzeUtils() {
        throw new IllegalArgumentException("It's Util Class. Do not create instance");
    }

    public static String analyze(String text, Analyzer analyzer) {

        try(TokenStream tokenStream = analyzer.tokenStream("field", text)) {
            CharTermAttribute charAttr = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();

            List<String> tokens = new ArrayList<>();
            while (tokenStream.incrementToken()) {
                tokens.add(charAttr.toString());
            }

            return String.join(" ", tokens);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
