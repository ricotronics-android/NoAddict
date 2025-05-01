package com.ricotronics.noaddict.ui.streak

import java.time.LocalDate

data class StreakState(
    val startDate: LocalDate = LocalDate.now(),
    val streakCount: MutableList<LocalDate> = mutableListOf()
)