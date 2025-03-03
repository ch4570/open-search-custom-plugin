package org.opensearch.index.analysis.kortoeng;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.opensearch.index.common.converter.KorToEngConverter;

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

public final class ConvertKoreanToEngFilter extends TokenFilter {

    private final KorToEngConverter converter;
    private final CharTermAttribute termAtt;

    public ConvertKoreanToEngFilter(TokenStream stream) {
        super(stream);
        this.converter = new KorToEngConverter();
        this.termAtt = addAttribute(CharTermAttribute.class);
    }

    @Override
    public boolean incrementToken() throws IOException {

        if (input.incrementToken()) {
            final CharSequence parsedData = converter.convert(termAtt.toString());
            termAtt.setEmpty();
            termAtt.append(parsedData);

            return true;
        }

        return false;
    }
}
