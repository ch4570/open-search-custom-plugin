package org.opensearch.index.common.converter

interface Converter {

    fun convert(token: String): String
}
