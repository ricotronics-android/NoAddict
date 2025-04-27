package com.ricotronics.noaddict

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ricotronics.noaddict.utils.formatTime

@Composable
fun Timer(
    modifier: Modifier = Modifier,
) {
    val viewModel = viewModel<StreakViewModel>()
    val timerValue by viewModel.timer.collectAsState()
    Text(
        modifier = Modifier
            .border(5.dp, Color.Black)
            .padding(10.dp),
        lineHeight = 42.sp,
        text = timerValue.formatTime(),
        fontSize = 40.sp)
}