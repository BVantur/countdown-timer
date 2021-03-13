package com.example.androiddevchallenge.model

enum class TimeUnitType {
    HOUR,
    MINUTE,
    SECOND,
    NONE;

    fun isHourType() = this == HOUR
    fun isMinuteType() = this == MINUTE
    fun isSecondType() = this == SECOND
    fun isNoneType() = this == NONE
}