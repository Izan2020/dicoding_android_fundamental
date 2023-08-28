package com.glantrox.dicoding_android_fundamental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.hilt.navigation.compose.hiltViewModel
import com.glantrox.dicoding_android_fundamental.core.data.local.AppPreference
import com.glantrox.dicoding_android_fundamental.navigator.AppRouter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            when(AppPreference.darkMode) {
                true ->  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            AppRouter().RouterDelegate(
                detailViewModel = hiltViewModel(),
                homeViewModel = hiltViewModel(),
                favouriteViewModel = hiltViewModel()
            )
        }
    }
}

