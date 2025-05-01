package com.ricotronics.noaddict.ui.streak

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ricotronics.noaddict.utils.UiEvent

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
    LazyColumn {
        items(state) { streakDate -> {
            Text(
                text = streakDate.startDate.toString()
            )
        }

        }
    }
}