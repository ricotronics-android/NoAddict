package com.ricotronics.noaddict.ui.streak

import java.time.LocalDateTime

sealed class StreakEvent {
    data object ResetStreak: StreakEvent()
    data object OnShowRelapsesClick: StreakEvent()
    data object OnShowSettingsClick: StreakEvent()
    data object DeleteAllStreaks: StreakEvent()
    data class StartStreakCounter(val start: Long): StreakEvent()
    data class ChangeAddictionName(val addictionName: String): StreakEvent()
    data class ChangeStreakStart(val streakStart: LocalDateTime, val streakId: Int): StreakEvent()
    data object StopStreakCounter: StreakEvent()
}