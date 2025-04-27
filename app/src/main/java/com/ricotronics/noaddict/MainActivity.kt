package com.ricotronics.noaddict

import android.os.Bundle
import android.text.format.DateFormat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ricotronics.noaddict.ui.theme.NoAddictTheme
import java.text.SimpleDateFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoAddictTheme {
                val viewModel = viewModel<StreakViewModel>()
                val state = viewModel.state
                Streak(
                    modifier = Modifier,
                    state = state,
                    onAction = viewModel::onAction,
                    dateFormat = DateFormat.getDateFormat(applicationContext),
                    timeFormat = DateFormat.getTimeFormat(applicationContext)
                )
            }
        }
    }
}
