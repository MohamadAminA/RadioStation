package com.radioroam.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.media3.common.MediaItem
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.radioroam.android.domain.model.RadioStation
import com.radioroam.android.ui.screens.FavoritesScreen
import com.radioroam.android.ui.screens.HomeScreen
import com.radioroam.android.ui.screens.LoginScreen
import com.radioroam.android.ui.screens.RegisterScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Screen.Login.title,
    onNextPage: (List<RadioStation>) -> Unit = {},
    onRadioStationClick: (Int) -> Unit = {},
    isPlayerSetUp: Boolean = false
) {
    NavHost(
        navController = navController,
        startDestination = startDestination // ← اول باید بره صفحه لاگین
    ) {
        composable(route = Screen.Login.title) {
            LoginScreen(navController = navController)
        }

        composable(route = Screen.Register.title) {
            RegisterScreen(navController = navController)
        }

        composable(route = Screen.Home.title) {
            HomeScreen(
                navController = navController,
                onNextPage = onNextPage,
                onRadioStationClick = onRadioStationClick,
                isPlayerSetUp = isPlayerSetUp
            )
        }

        composable(route = Screen.Favorites.title) {
            FavoritesScreen(
                navController = navController
            )
        }
    }
}
