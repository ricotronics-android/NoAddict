package com.ricotronics.noaddict.ui.streak

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ricotronics.noaddict.utils.UiEvent
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowRelapsesScreen(
    modifier: Modifier = Modifier,
    onPopBackstack: () -> Unit,
    viewModel: StreakViewModel = hiltViewModel()
) {
    val state by viewModel.streakList.collectAsState(emptyList())
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
    // TODO Fix this
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {viewModel.streakEvent(StreakEvent.DeleteAllStreaks)}) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "Delete all Streaks")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            item {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    text = "Total Relapses",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
            items(state) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = Instant
                        .ofEpochMilli(it.startDate)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString()
                            + " " +
                            Instant
                                .ofEpochMilli(it.startDate)
                                .atZone(ZoneId.systemDefault())
                                .toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:SS")).toString(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}