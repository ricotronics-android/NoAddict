package com.ricotronics.noaddict.ui.streak

sealed class StreakAction {
    data object Reset: StreakAction()
}