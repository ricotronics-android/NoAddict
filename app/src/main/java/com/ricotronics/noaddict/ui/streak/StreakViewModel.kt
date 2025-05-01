package com.ricotronics.noaddict.ui.streak

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricotronics.noaddict.data.StreakData
import com.ricotronics.noaddict.data.StreakRepository
import com.ricotronics.noaddict.utils.Routes
import com.ricotronics.noaddict.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StreakViewModel @Inject constructor(
    private val repository: StreakRepository
): ViewModel() {
    val streakList = repository.getAllStreakDates()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun streakEvent(event: StreakEvent) {
        when (event) {
            StreakEvent.OnShowRelapsesClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.RELAPSE_VIEW))
            }
            StreakEvent.ResetStreak -> {
                resetStreak()
            }
        }
    }

    // timer
    private val _timer = MutableStateFlow(0L)
    val timer = _timer.asStateFlow()

    private var timerJob: Job? = null

    // TODO start timer on app start

    fun startTimer(start: Long) {
        timerJob?.cancel()
        _timer.value = start
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

    private fun resetStreak() {
        viewModelScope.launch {
            repository.addStreakDate(StreakData(System.currentTimeMillis()))
        }
        startTimer(0)
    }

    private fun sendUiEvent(event: UiEvent) {
         viewModelScope.launch { _uiEvent.send(event) }
    }
}