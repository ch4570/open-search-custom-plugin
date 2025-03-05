package org.opensearch.index.analysis.utils

import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import java.io.IOException

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
object AnalyzeUtils {

    fun analyze(text: String, analyzer: Analyzer): String {
        try {
            analyzer.tokenStream("field", text).use { tokenStream ->
                val charAttr = tokenStream.addAttribute(
                    CharTermAttribute::class.java
                )

                tokenStream.reset()

                val tokens = mutableListOf<String>()
                while (tokenStream.incrementToken()) {
                    tokens.add(charAttr.toString())
                }
                return tokens.joinToString(" ")
            }
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }
}
