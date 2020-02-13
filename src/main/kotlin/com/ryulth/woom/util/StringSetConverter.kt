package com.ryulth.woom.util

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class StringSetConverter : AttributeConverter<Set<String>, String> {
    companion object {
        private const val SPLIT_CHAR = ","
    }

    override fun convertToDatabaseColumn(stringSet: Set<String>): String? {
        return if (stringSet.isEmpty()) {
            ""
        } else stringSet.joinToString(separator = SPLIT_CHAR)
    }

    override fun convertToEntityAttribute(string: String?): Set<String> {
        return if (string.isNullOrEmpty()) {
            hashSetOf()
        } else string.split(SPLIT_CHAR).toHashSet()
    }
}
