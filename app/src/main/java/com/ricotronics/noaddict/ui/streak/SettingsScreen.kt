package com.ricotronics.noaddict.ui.streak

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ricotronics.noaddict.ui.AlertDialogWrapper
import com.ricotronics.noaddict.utils.UiEvent
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onPopBackstack: () -> Unit,
    viewModel: StreakViewModel = hiltViewModel()
) {
    val openResetAppDialog = remember { mutableStateOf(false) }
    val openEditAddictionNameDialog = remember { mutableStateOf(false) }
    val openEditStreakStartDialog = remember { mutableStateOf(false) }
    val addictionName = remember { mutableStateOf("") }

    // states for streak start edit
    val year = remember { mutableStateOf("") }
    val month = remember { mutableStateOf("") }
    val day = remember { mutableStateOf("") }
    val hour = remember { mutableStateOf("") }
    val minute = remember { mutableStateOf("") }
    val second = remember { mutableStateOf("") }
    val streakId = remember { mutableStateOf(-1) }

    val metaData by viewModel.metaData.collectAsState(emptyList())
    val state by viewModel.streakList.collectAsState(emptyList())

    // set current addiction name to edit field if not empty
    addictionName.value = if (metaData.isEmpty()) "" else metaData[0].addictionName

    // set last streak date to streak start edit state
    if(!state.isEmpty()) {
        val lastStreakStart = Instant
        .ofEpochMilli(state.get(state.lastIndex).startDate)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

        year.value = lastStreakStart.year.toString()
        month.value = lastStreakStart.month.value.toString()
        day.value = lastStreakStart.dayOfMonth.toString()
        hour.value = lastStreakStart.hour.toString()
        minute.value = lastStreakStart.minute.toString()
        second.value = lastStreakStart.second.toString()
        streakId.value = state.get(state.lastIndex).id!!
    }


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
        openResetAppDialog.value -> {
            AlertDialogWrapper(
                onDismissRequest = { openResetAppDialog.value = false },
                onConfirmation = {
                    viewModel.streakEvent(StreakEvent.DeleteAllStreaks)
                    viewModel.streakEvent(StreakEvent.StopStreakCounter)
                    openResetAppDialog.value = false
                },
                dialogTitle = "Warning",
                dialogText = "Do you really want to delete the whole streak history? This option cannot be undone!",
                icon = Icons.Default.Info,
                content = {}
            )
        }
        openEditAddictionNameDialog.value -> {
            AlertDialogWrapper(
                dialogTitle = "Edit Addiction Name",
                content = {OutlinedTextField(addictionName.value, { addictionName.value = it }, label = {Text("Addiction Name")})},
                onDismissRequest = { openEditAddictionNameDialog.value = false },
                onConfirmation = {
                    viewModel.streakEvent(StreakEvent.ChangeAddictionName(addictionName.value))
                    openEditAddictionNameDialog.value = false
                },
                icon = Icons.Default.Edit
            )
        }
        openEditStreakStartDialog.value -> {
            AlertDialogWrapper(
                dialogTitle = "Edit last streak start",
                content = {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(year.value, { year.value = it }, label = {Text("Year")})
                        OutlinedTextField(month.value, { month.value = it }, label = {Text("Month")})
                        OutlinedTextField(day.value, { day.value = it }, label = {Text("Day")})
                        OutlinedTextField(hour.value, { hour.value = it }, label = {Text("Hour")})
                        OutlinedTextField(minute.value, { minute.value = it }, label = {Text("Minute")})
                        OutlinedTextField(second.value, { second.value = it }, label = {Text("Second")})
                    }
                          },
                onDismissRequest = { openEditStreakStartDialog.value = false },
                onConfirmation = {

                    val date = LocalDateTime.of(
                        year.value.toInt(),
                        month.value.toInt(),
                        day.value.toInt(),
                        hour.value.toInt(),
                        minute.value.toInt(),
                        second.value.toInt())
                    viewModel.streakEvent(StreakEvent.ChangeStreakStart(date, streakId.value))
                    openEditStreakStartDialog.value = false
                },
                icon = Icons.Default.Edit
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
        SettingsOption(
            modifier = modifier,
            function = { openEditStreakStartDialog.value = true},
            text = "Edit last streak start",
            textColor = Color.Black
        )
        SettingsOption(
            modifier = modifier,
            function = { openEditAddictionNameDialog.value = true},
            text = "Edit Addiction Name",
            textColor = Color.Black
        )
        SettingsOption(
            modifier = modifier,
            function = { openResetAppDialog.value = true },
            text = "Reset App!",
            textColor = Color.Red
        )
    }
}