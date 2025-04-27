package com.ricotronics.noaddict

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class StreakViewModel: ViewModel() {
    var state by mutableStateOf(StreakState())
        private set

    private val _timer = MutableStateFlow(0L)
    val timer = _timer.asStateFlow()

    private var timerJob: Job? = null

    fun startTimer() {
        timerJob?.cancel()
        _timer.value = 0
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _timer.value++
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

    fun onAction(action: StreakAction) {
        when (action) {
            is StreakAction.Reset -> resetStreak()
        }
    }

    private fun resetStreak() {
        state = state.copy(
            startDate = Date(),
            streakCount = state.streakCount + 1
        )
        startTimer()
    }
}