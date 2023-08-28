package com.glantrox.dicoding_android_fundamental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.hilt.navigation.compose.hiltViewModel
import com.chibatching.kotpref.Kotpref
import com.glantrox.dicoding_android_fundamental.core.viewmodel.PreferenceViewModel
import com.glantrox.dicoding_android_fundamental.navigator.AppRouter
import com.glantrox.dicoding_android_fundamental.ui.theme.Dicoding_android_fundamentalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Kotpref.init(this@MainActivity)

        setContent {
             val preferenceViewModel: PreferenceViewModel = hiltViewModel()
            Dicoding_android_fundamentalTheme(
                darkTheme = preferenceViewModel.isDarkMode.value) {
                AppRouter().RouterDelegate(
                    detailViewModel = hiltViewModel(),
                    homeViewModel = hiltViewModel(),
                    favouriteViewModel = hiltViewModel(),
                    preferenceViewModel = hiltViewModel()
                )
            }


        }
    }
}

