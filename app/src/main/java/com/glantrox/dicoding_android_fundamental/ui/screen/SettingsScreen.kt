package com.glantrox.dicoding_android_fundamental.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.glantrox.dicoding_android_fundamental.core.viewmodel.PreferenceViewModel
import com.glantrox.dicoding_android_fundamental.navigator.AppRouter

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController : NavHostController = rememberNavController(),
    preferenceViewModel: PreferenceViewModel = hiltViewModel()
) {


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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Dark Mode")
                    Switch(checked = preferenceViewModel.isDarkMode.value, onCheckedChange = { darkMode ->
                        preferenceViewModel.setDarkMode(darkMode)

                    } )
                }
            }
        }
    }
}