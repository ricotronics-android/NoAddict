package com.ricotronics.noaddict.ui.streak

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsOption(
    modifier: Modifier = Modifier,
    function: () -> Unit,
    text: String,
    textColor: Color
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .drawBehind {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 4F,
                )}
            .clickable {
                function()
            }
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            color = textColor,
            fontSize = 22.sp,
            text = text,
        )
    }
}