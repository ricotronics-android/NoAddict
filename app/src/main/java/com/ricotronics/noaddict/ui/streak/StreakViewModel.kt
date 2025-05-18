package com.ricotronics.noaddict.ui.streak

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricotronics.noaddict.data.MetaData
import com.ricotronics.noaddict.data.MetaRepository
import com.ricotronics.noaddict.data.StreakData
import com.ricotronics.noaddict.data.StreakRepository
import com.ricotronics.noaddict.utils.Routes
import com.ricotronics.noaddict.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StreakViewModel @Inject constructor(
    private val repository: StreakRepository,
    private val metaRepository: MetaRepository
): ViewModel() {
    val streakList = repository.getAllStreakDates()

    // meta data
    val metaData = metaRepository.getMetaData()

    init {
        viewModelScope.launch {
            val md = metaRepository.getMetaData()
            if(md.toList().isEmpty())  {
                metaRepository.addMetaData(MetaData(0, "Addict"))
            }
        }
    }

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun streakEvent(event: StreakEvent) {
        when (event) {
            StreakEvent.OnShowRelapsesClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.RELAPSE_VIEW))
            }
            is StreakEvent.OnShowSettingsClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.SETTINGS_VIEW)) // TODO: add id for edit
            }
            StreakEvent.ResetStreak -> {
                resetStreak()
            }
            StreakEvent.DeleteAllStreaks -> {
                viewModelScope.launch {
                    repository.deleteAllStreaks()
                }
            }
            is StreakEvent.StartStreakCounter -> {
                startTimer(event.start)
            }
            is StreakEvent.StopStreakCounter -> {
                cancelTimer()
            }
            is StreakEvent.ChangeAddictionName -> {
                println(event.addictionName)
                viewModelScope.launch {
                    metaRepository.addMetaData(MetaData(0, event.addictionName))
                }
            }
        }
    }

    // timer
    private val _timer = MutableStateFlow(0L)
    val timer = _timer.asStateFlow()

    private var timerJob: Job? = null
    private var _running = MutableStateFlow(false)
    private val running = _running.asStateFlow()

    private fun startTimer(start: Long) {
        _running.value = false
        timerJob?.cancel()
        _running.value = true
        _timer.value = start
        timerJob = viewModelScope.launch {
            while (running.value) {
                delay(1000)
                _timer.value++
            }
        }
    }

    private fun cancelTimer() {
        _running.value = false
        _timer.value = 0
    }

    private fun resetStreak() {
        viewModelScope.launch {
            repository.addStreakDate(StreakData(System.currentTimeMillis()))
        }
    }

    private fun sendUiEvent(event: UiEvent) {
         viewModelScope.launch { _uiEvent.send(event) }
    }
}