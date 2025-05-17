package com.ricotronics.noaddict.ui.streak

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ricotronics.noaddict.ui.AlertDialogWrapper
import com.ricotronics.noaddict.utils.UiEvent

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onPopBackstack: () -> Unit,
    viewModel: StreakViewModel = hiltViewModel()
) {
    val openAlertDialog = remember { mutableStateOf(false) }
    LaunchedEffect(key1=true) {
        viewModel.uiEvent.collect {
                event -> when (event) {
                is UiEvent.PopBackStack -> {
                    onPopBackstack()
                }
                else -> Unit
            }
        }
    }
    when {
        openAlertDialog.value -> {
            AlertDialogWrapper(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    viewModel.streakEvent(StreakEvent.DeleteAllStreaks)
                    viewModel.streakEvent(StreakEvent.StopStreakCounter)
                    openAlertDialog.value = false
                },
                dialogTitle = "Warning",
                dialogText = "Do you really want to delete the whole streak history? This option cannot be undone!",
                icon = Icons.Default.Info
            )
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            modifier = Modifier.padding(5.dp)
                .fillMaxWidth(),
            text = "Settings",
            textAlign = TextAlign.Center,
            fontSize = 35.sp
        )
        Spacer(modifier = Modifier.height(30.dp))
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
                    // TODO
                }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Color.Black,
                fontSize = 22.sp,
                text = "Edit last Streak Start",
            )
        }
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
                    openAlertDialog.value = true
                }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Color.Red,
                fontSize = 22.sp,
                text = "Reset App!",
            )
        }
    }
}