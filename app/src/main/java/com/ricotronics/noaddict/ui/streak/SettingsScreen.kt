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

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onPopBackstack: () -> Unit,
    viewModel: StreakViewModel = hiltViewModel()
) {
    val openResetAppDialog = remember { mutableStateOf(false) }
    val openEditAddictionNameDialog = remember { mutableStateOf(false) }
    val addictionName = remember { mutableStateOf("") }
    val metaData by viewModel.metaData.collectAsState(emptyList())
    addictionName.value = if (metaData.isEmpty()) "" else metaData[0].addictionName
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
            function = {},
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