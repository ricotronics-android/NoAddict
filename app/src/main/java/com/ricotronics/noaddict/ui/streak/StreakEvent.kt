package com.ricotronics.noaddict.ui.streak

sealed class StreakEvent {
    data object ResetStreak: StreakEvent()
    data object OnShowRelapsesClick: StreakEvent()
}