package com.glantrox.dicoding_android_fundamental.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.glantrox.dicoding_android_fundamental.core.viewmodel.DetailViewModel
import com.glantrox.dicoding_android_fundamental.core.viewmodel.FavouriteViewModel
import com.glantrox.dicoding_android_fundamental.core.viewmodel.HomeViewModel
import com.glantrox.dicoding_android_fundamental.ui.screen.DetailScreen
import com.glantrox.dicoding_android_fundamental.ui.screen.FavouriteScreen
import com.glantrox.dicoding_android_fundamental.ui.screen.HomeScreen
import com.glantrox.dicoding_android_fundamental.ui.screen.SettingsScreen
import com.glantrox.dicoding_android_fundamental.ui.screen.SplashScreen

sealed class Screen(val route: String) {
    object HomeScreen : Screen("i<3dIcoDiNg!!!!h0m3sCr33n!?#?!i<3dIcoDiNg!!!!#akubutuhbintang5")
    object DetailScreen : Screen("i<3dIcoDiNg!!!!d3t41lsCr33n!?#?!i<3dIcoDiNg!!!!#pengenbintang5")
    object SplashScreen : Screen("i<3dIcoDiNg!!!!sPl45hsCr33n!?#?!i<3dIcoDiNg!!!!#plsbintang5")
    object FavouriteScreen: Screen("i<3dIcoDiNg!!!!f4v0uR1t3sCr33n!?#?!i<3dIcoDiNg!!!!#inginbintang5LAGI")
    object SettingsScreen: Screen("i<3dIcoDiNg!!!!s3tt1n6sSCr33n!?#?!i<3dIcoDiNg!!!!#akuSukaBintang5")
}

class AppRouter {

    @Composable
    fun RouterDelegate(
        detailViewModel: DetailViewModel,
        homeViewModel: HomeViewModel,
        favouriteViewModel: FavouriteViewModel
    ) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
            composable(Screen.SplashScreen.route) {
                SplashScreen(navController)
            }
            composable(Screen.DetailScreen.route) {
                DetailScreen(
                    detailViewModel,
                    favouriteViewModel,
                    navController
                )
            }
            composable(Screen.HomeScreen.route) {
                HomeScreen(
                    homeViewModel,
                    detailViewModel,
                    navController
                )
            }
            composable(Screen.FavouriteScreen.route) {
                FavouriteScreen(navController = navController,
                    favouriteViewModel = favouriteViewModel,
                    detailViewModel = detailViewModel
                    )
            }
            composable(Screen.SettingsScreen.route) {
                SettingsScreen()
            }

        }
    }

companion object {
    fun push(navController: NavHostController, screen: String) {
        navController.navigate(screen)
    }

    fun pushAndReplace(navController: NavHostController ,screen: String) {
        navController.navigate(screen) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    fun pop(navController: NavHostController) {
        navController.popBackStack()
    }
}

}