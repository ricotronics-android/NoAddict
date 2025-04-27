package com.ricotronics.noaddict.utils

import java.util.Locale

fun Long.formatTime(): String {
    val days = (this / 3600) / 24
    val hours = (this / 3600) % 24
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return String.format(Locale.GERMAN,"%02d Days\n%02d Hours\n%02d Minutes\n%02d Seconds", days, hours, minutes, seconds)
}