package org.opensearch.plugin

import org.opensearch.common.settings.Settings
import org.opensearch.env.Environment
import org.opensearch.index.IndexSettings
import org.opensearch.index.analysis.TokenFilterFactory
import org.opensearch.indices.analysis.AnalysisModule.AnalysisProvider
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

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
object AnalysisProviderUtils {

    fun <T : TokenFilterFactory> createProvider(
        constructor: (IndexSettings, Environment, String, Settings) -> T
    ): AnalysisProvider<TokenFilterFactory> =
        AnalysisProvider { indexSettings, environment, name, settings ->
            constructor(indexSettings, environment, name, settings)
        }
}
