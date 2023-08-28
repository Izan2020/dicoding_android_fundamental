package com.glantrox.dicoding_android_fundamental.ui.screen

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.glantrox.dicoding_android_fundamental.core.data.local.AppPreference
import com.glantrox.dicoding_android_fundamental.navigator.AppRouter

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController : NavHostController = rememberNavController()
) {
    var isDarkMode by remember { mutableStateOf(AppPreference.darkMode)}

    when(AppPreference.darkMode) {
        true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

        return Scaffold(
            topBar = {
                TopAppBar(
                    title = {Text("Settings")},
                    navigationIcon = {
                        IconButton(onClick = {
                            AppRouter.pop(navController)
                        }) {
                            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "")
                        }
                    }
                )
            }
        ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Card {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Dark Mode")
                    Switch(checked = isDarkMode, onCheckedChange = { darkMode ->
                        isDarkMode = darkMode
                        AppPreference.darkMode = darkMode
                    } )
                }
            }
        }
    }
}