package com.ricotronics.noaddict

import java.util.Date

data class StreakState(
    val startDate: Date = Date(),
    val streakCount: Int = 0
)