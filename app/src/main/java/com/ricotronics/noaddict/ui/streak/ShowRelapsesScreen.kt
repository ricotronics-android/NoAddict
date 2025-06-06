package com.ricotronics.noaddict.ui.streak

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ricotronics.noaddict.R
import com.ricotronics.noaddict.ui.theme.DarkBlue
import com.ricotronics.noaddict.ui.theme.DeepBlue
import com.ricotronics.noaddict.utils.UiEvent
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowRelapsesScreen(
    modifier: Modifier = Modifier,
    onPopBackstack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: StreakViewModel = hiltViewModel()
) {
    val state by viewModel.streakList.collectAsState(emptyList())
    val metaData by viewModel.metaData.collectAsState(emptyList())
    LaunchedEffect(key1=true) {
        viewModel.uiEvent.collect {
            event -> when (event) {
                is UiEvent.PopBackStack -> {
                    onPopBackstack()
                }
                is UiEvent.Navigate -> onNavigate(event)
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.background),
                contentScale = ContentScale.FillHeight
            )
            .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {viewModel.streakEvent(StreakEvent.OnShowSettingsClick)},
                containerColor = DeepBlue, contentColor = Color.White) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Open Settings")
            }
        },
        containerColor = Color.Transparent
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            item {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    text = "Total Relapses",
                    textAlign = TextAlign.Center,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DarkBlue
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
            items(state) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .drawBehind {
                                drawLine(
                                    color = Color.White,
                                    start = Offset(0f, size.height + 20),
                                    end = Offset(size.width, size.height + 20),
                                    strokeWidth = 4F,
                                )},
                        text = "Date: " + Instant
                            .ofEpochMilli(it.startDate)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString()
                                + "\nTime: " +
                                Instant
                                    .ofEpochMilli(it.startDate)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")).toString(),
                        textAlign = TextAlign.Left,
                        fontSize = 20.sp,
                        color = Color.White
                    )
            }
        }
    }
}