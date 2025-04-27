package com.ricotronics.noaddict

sealed class StreakAction {
    data object Reset: StreakAction()
}