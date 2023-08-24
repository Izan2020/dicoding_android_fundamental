package com.glantrox.dicoding_android_fundamental.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.glantrox.dicoding_android_fundamental.core.viewmodel.DetailViewModel
import com.glantrox.dicoding_android_fundamental.core.viewmodel.HomeViewModel
import com.glantrox.dicoding_android_fundamental.ui.screen.DetailScreen
import com.glantrox.dicoding_android_fundamental.ui.screen.HomeScreen
import com.glantrox.dicoding_android_fundamental.ui.screen.SplashScreen

sealed class Screen(val route: String) {
    object homeScreen : Screen("i<3dIcoDiNg!!!!h0m3sCr33n!?#?!i<3dIcoDiNg!!!!#akubutuhbintang5")
    object detailScreen : Screen("i<3dIcoDiNg!!!!d3t41lsCr33n!?#?!i<3dIcoDiNg!!!!#pengenbintang5")
    object splashScreen : Screen("i<3dIcoDiNg!!!!sPl45hsCr33n!?#?!i<3dIcoDiNg!!!!#plsbintang5")
}

class AppRouter {

    @Composable
    fun RouterDelegate(
        detailViewModel: DetailViewModel,
        homeViewModel: HomeViewModel,
    ) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screen.splashScreen.route) {
            composable(Screen.splashScreen.route) {
                SplashScreen(navController)
            }
            composable(Screen.detailScreen.route) {
                DetailScreen(
                    detailViewModel,
                    navController
                )
            }
            composable(Screen.homeScreen.route) {
                HomeScreen(
                    homeViewModel,
                    detailViewModel,
                    navController
                )
            }

        }
    }

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