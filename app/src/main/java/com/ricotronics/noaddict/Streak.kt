package com.ricotronics.noaddict

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ricotronics.noaddict.ui.theme.LightWhite
import java.text.DateFormat
import java.util.Date

@Composable
fun Streak(
    modifier: Modifier = Modifier,
    state: StreakState,
    onAction: (StreakAction) -> Unit,
    dateFormat: DateFormat,
    timeFormat: DateFormat
    ) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(LightWhite)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Timer()
            Text(
                text = "Start: " + dateFormat.format(state.startDate) + " - " + timeFormat.format(state.startDate),
                modifier = Modifier
                    .padding(vertical = 32.dp),
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                fontSize = 20.sp
            )
            Text(
                text = (Math.abs((state.startDate.time - Date().time) / (24 * 60 * 60 * 1000)) - (state.streakCount)).toString() + " Good days!",
                modifier = Modifier
                    .padding(vertical = 32.dp),
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                fontSize = 20.sp
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Cyan)
                    .padding(10.dp)
                    .clickable { onAction(StreakAction.Reset) }
            ) {
                Text(
                    text = if (state.streakCount == 0) "Start Streak" else "Reset Streak",
                    fontSize = 20.sp,
                    color = Color.Black,
                )
            }
        }


    }
}