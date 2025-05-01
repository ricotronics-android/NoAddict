package com.ricotronics.noaddict.ui.Timer

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ricotronics.noaddict.ui.streak.StreakEvent
import com.ricotronics.noaddict.ui.streak.StreakViewModel
import com.ricotronics.noaddict.utils.getDays
import com.ricotronics.noaddict.utils.getHours
import com.ricotronics.noaddict.utils.getMinutes
import com.ricotronics.noaddict.utils.getSeconds
import java.time.Duration
import java.time.LocalDate

@Composable
fun Timer(
    modifier: Modifier = Modifier,
    viewModel: StreakViewModel = hiltViewModel()
) {
    val timerValue by viewModel.timer.collectAsState()
    val streakDates by viewModel.streakList.collectAsState(emptyList())
    if(streakDates.size > 0) {
        val timeDiff: Long = (System.currentTimeMillis() - streakDates.get(streakDates.lastIndex).startDate) / 1000
        viewModel.streakEvent(StreakEvent.StartStreakCounter(timeDiff))
    }
    Column(
        modifier = Modifier
            .border(3.dp, Color.Black, shape = RoundedCornerShape(20.dp)),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        TimerRow(modifier, timerValue.getDays(), "Days")
        TimerRow(modifier, timerValue.getHours(), "Hours")
        TimerRow(modifier, timerValue.getMinutes(), "Minutes")
        TimerRow(modifier, timerValue.getSeconds(), "Seconds")
    }
}