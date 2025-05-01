package com.ricotronics.noaddict.utils

import java.util.Locale

fun Long.getDays(): Long {
    return (this / 3600) / 24
}

fun Long.getHours(): Long {
    return (this / 3600) % 24}

fun Long.getMinutes(): Long {
    return (this % 3600) / 60
}

fun Long.getSeconds(): Long {
    return this % 60
}