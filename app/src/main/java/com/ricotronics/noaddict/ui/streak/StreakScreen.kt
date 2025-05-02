package com.ricotronics.noaddict.ui.streak

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ricotronics.noaddict.data.StreakData
import com.ricotronics.noaddict.ui.AlertDialogWrapper
import com.ricotronics.noaddict.ui.Timer.Timer
import com.ricotronics.noaddict.ui.theme.DeepBlue
import com.ricotronics.noaddict.utils.UiEvent
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StreakScreen(
    modifier: Modifier = Modifier,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: StreakViewModel = hiltViewModel(),
    ) {
    val state by viewModel.streakList.collectAsState(emptyList())
    val openAlertDialog = remember { mutableStateOf(false) }
    LaunchedEffect(key1=true) {
        viewModel.uiEvent.collect {
            event -> when (event) {
            is UiEvent.Navigate -> onNavigate(event)
            else -> Unit
        }
        }
    }

    when {
        openAlertDialog.value -> {
            AlertDialogWrapper(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    viewModel.streakEvent(StreakEvent.ResetStreak)
                },
                dialogTitle = "Warning",
                dialogText = "Do you really want to reset your streak?",
                icon = Icons.Default.Info
            )
        }
    }

    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.streakEvent(StreakEvent.OnShowRelapsesClick)
            }) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "show relapses")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "NoAddict",
                fontSize = 50.sp,
                color = DeepBlue,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            Timer()
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Start: " + if (!state.isEmpty()) Instant
                    .ofEpochMilli(state.get(state.lastIndex).startDate)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString()
                        + " " +
                        Instant
                            .ofEpochMilli(state.get(state.lastIndex).startDate)
                            .atZone(ZoneId.systemDefault())
                            .toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")).toString() else "No Streak started",
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                fontSize = 20.sp
            )
            Text(
                text = "Good days since first streak: " + calculateGoodDays(state),
                modifier = Modifier
                    .padding(vertical = 32.dp),
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(DeepBlue)
                    .padding(10.dp)
                    .clickable {
                        openAlertDialog.value = true
                    }
            ) {
                Text(
                    text = if (state.isEmpty()) "Start Streak" else "Reset Streak",
                    fontSize = 20.sp,
                    color = Color.White,
                )
            }
        }


    }
}

fun calculateGoodDays(state: List<StreakData>): String {
    return if(state.isEmpty()) {
        (0).toString()
    } else {
        Math.max(0, Duration.between(
            Instant.ofEpochMilli(
                state.get(state.lastIndex).startDate).atZone(ZoneId.systemDefault()
            ).toLocalDate().atStartOfDay(),
            LocalDate.now().atStartOfDay()).toDays() - state.size).toString()
    }
}