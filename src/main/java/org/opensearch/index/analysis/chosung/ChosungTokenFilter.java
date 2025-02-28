package org.opensearch.index.analysis.chosung;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.opensearch.index.common.parser.KoreanChosungParser;

/*
 * Licensed to Elasticsearch B.V. under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch B.V. licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

public final class ChosungTokenFilter extends TokenFilter {

    private final KoreanChosungParser parser;
    private final CharTermAttribute termAtt;

    public ChosungTokenFilter(TokenStream stream) {
        super(stream);
        this.parser = new KoreanChosungParser();
        this.termAtt = addAttribute(CharTermAttribute.class);
    }

    /**
     * 한글 초성 Parser를 이용하여 토큰을 파싱하고 Term을 구한다.
     */
    @Override
    public boolean incrementToken() throws IOException {

        if (input.incrementToken()) {
            final CharSequence parsedData = parser.parse(termAtt.toString());
            termAtt.setEmpty();
            termAtt.append(parsedData);

            return true;
        }

        return false;
    }
}
