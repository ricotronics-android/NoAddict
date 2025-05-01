package com.ricotronics.noaddict.ui.Timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ricotronics.noaddict.ui.theme.DeepBlue
import java.util.Locale

@Composable
fun TimerRow(
    modifier: Modifier = Modifier,
    timerValue: Long = 0,
    timerUnit: String = ""
) {
    Row (
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp),
            lineHeight = 42.sp,
            text = String.format(Locale.GERMAN, "%02d", timerValue),
            fontSize = 40.sp,
            color = DeepBlue
        )
        Text(
            modifier = Modifier
                .padding(10.dp),
            lineHeight = 42.sp,
            text = timerUnit,
            fontSize = 40.sp)
    }
}