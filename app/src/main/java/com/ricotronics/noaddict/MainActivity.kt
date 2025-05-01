package com.ricotronics.noaddict

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ricotronics.noaddict.ui.streak.ShowRelapsesScreen
import com.ricotronics.noaddict.ui.streak.StreakScreen
import com.ricotronics.noaddict.ui.theme.NoAddictTheme
import com.ricotronics.noaddict.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoAddictTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = Routes.STREAK_VIEW) {
                    composable(Routes.STREAK_VIEW) {
                        StreakScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(Routes.RELAPSE_VIEW) {
                        ShowRelapsesScreen(
                            onPopBackstack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
