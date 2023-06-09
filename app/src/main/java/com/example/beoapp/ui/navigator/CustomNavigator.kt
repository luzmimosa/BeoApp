package com.example.beoapp.ui.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.beoapp.MainActivity
import com.example.beoapp.model.Routes
import com.example.beoapp.ui.avaiableLevelsScreen.AvaiableLevelsScreen
import com.example.beoapp.ui.avaiableLevelsScreen.AvaiableLevelsViewModel
import com.example.beoapp.ui.hostScreen.HostScreen
import com.example.beoapp.ui.hostScreen.HostScreenViewModel
import com.example.beoapp.ui.levelScreen.LevelScreen
import com.example.beoapp.ui.levelScreen.LevelScreenViewModel

@Composable
fun CustomNavigator(context: MainActivity) {

    val navigationController = rememberNavController()
    
    NavHost(
        navController = navigationController,
        startDestination = Routes.HostScreen.route
    ) {

        composable(Routes.HostScreen.route) {
            HostScreen(HostScreenViewModel(context), navigationController)
        }

        composable(Routes.AvaiableLevelsScreen.route) {
            AvaiableLevelsScreen(AvaiableLevelsViewModel(context), navigationController)
        }

        composable(
            route = Routes.LevelScreen.route,
            arguments = listOf(
                navArgument("levelID") {
                    type = NavType.StringType
                    this.nullable = false
                }
            )
        ) {
            val levelID = it.arguments?.getString("levelID") ?: ""

            LevelScreen(levelScreenViewModel = LevelScreenViewModel(levelID))

        }

    }

}