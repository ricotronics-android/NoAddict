package com.ricotronics.noaddict.ui.streak

sealed class StreakEvent {
    data object ResetStreak: StreakEvent()
    data object OnShowRelapsesClick: StreakEvent()
    data object OnShowSettingsClick: StreakEvent()
    data object DeleteAllStreaks: StreakEvent()
    data class StartStreakCounter(val start: Long): StreakEvent()
    data object StopStreakCounter: StreakEvent()
}