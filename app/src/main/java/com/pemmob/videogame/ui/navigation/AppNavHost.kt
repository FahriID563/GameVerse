package com.pemmob.videogame.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pemmob.videogame.ui.detail.DetailScreen
import com.pemmob.videogame.ui.detail.DetailViewModel
import com.pemmob.videogame.ui.home.HomeScreen
import com.pemmob.videogame.ui.home.HomeViewModel

private object Routes {
    const val Home = "home"
    const val Detail = "detail/{gameId}"
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Home) {
        composable(Routes.Home) {
            HomeScreen(
                viewModel = viewModel<HomeViewModel>(),
                onGameClick = { id -> navController.navigate("detail/$id") }
            )
        }
        composable(
            route = Routes.Detail,
            arguments = listOf(navArgument("gameId") { type = NavType.IntType })
        ) { entry ->
            DetailScreen(
                gameId = entry.arguments?.getInt("gameId") ?: return@composable,
                viewModel = viewModel<DetailViewModel>(),
                onBack = { navController.popBackStack() }
            )
        }
    }
}