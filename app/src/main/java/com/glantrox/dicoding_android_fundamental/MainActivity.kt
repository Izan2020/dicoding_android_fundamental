package com.glantrox.dicoding_android_fundamental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.glantrox.dicoding_android_fundamental.core.viewmodel.DetailViewModel
import com.glantrox.dicoding_android_fundamental.core.viewmodel.HomeViewModel
import com.glantrox.dicoding_android_fundamental.navigator.AppRouter

class MainActivity : ComponentActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var detailViewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        setContent {
            AppRouter().RouterDelegate(
                detailViewModel = detailViewModel,
                homeViewModel = homeViewModel
            )
        }
    }
}

