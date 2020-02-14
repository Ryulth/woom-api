package com.ryulth.woom.util

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date
import java.util.TimeZone

class LocalDateTimeDynamoDBConverter : DynamoDBTypeConverter<Date, LocalDateTime> {
    override fun convert(source: LocalDateTime): Date {
        return Date.from(source.toInstant(ZoneOffset.UTC))
    }

    override fun unconvert(source: Date): LocalDateTime {
        return source.toInstant().atZone(TimeZone.getDefault().toZoneId()).toLocalDateTime()
    }
}
