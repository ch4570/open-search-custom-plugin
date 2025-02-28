package org.opensearch.index.analysis.eng2kor;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.opensearch.index.common.converter.EngToKorConverter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

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

public final class JavacafeEng2KorConvertFilter3 extends TokenFilter {

    private EngToKorConverter converter;
    private CharTermAttribute termAtt;

    private PositionIncrementAttribute positionIncrementAttribute;
    private Queue<char[]> simpleQueue;


    public JavacafeEng2KorConvertFilter3(TokenStream stream) {
        super(stream);
        this.converter = new EngToKorConverter();
        this.termAtt = addAttribute(CharTermAttribute.class);

        this.positionIncrementAttribute = addAttribute(PositionIncrementAttribute.class);

        this.simpleQueue = new LinkedList<char[]>();
    }


    @Override
    public boolean incrementToken() throws IOException {

        if (!simpleQueue.isEmpty()) {
            char[] buffer = simpleQueue.poll();
            termAtt.setEmpty();
            termAtt.copyBuffer(buffer, 0, buffer.length);

            positionIncrementAttribute.setPositionIncrement(0);

            return true;
        }

        if (input.incrementToken()) {
            String result = converter.convert(termAtt.toString());
            simpleQueue.add(result.toCharArray());

            return true;
        }

        return false;
    }



}
