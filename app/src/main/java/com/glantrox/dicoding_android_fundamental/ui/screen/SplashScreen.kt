package com.glantrox.dicoding_android_fundamental.ui.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.glantrox.dicoding_android_fundamental.R
import com.glantrox.dicoding_android_fundamental.navigator.AppRouter
import com.glantrox.dicoding_android_fundamental.navigator.Screen
import kotlinx.coroutines.delay

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    navController: NavHostController = rememberNavController()
) {
    LaunchedEffect(true) {
        delay(2500)
        AppRouter.pushAndReplace(navController = navController, Screen.HomeScreen.route)
    }
    return Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image( modifier = Modifier.size(180.dp),
            painter = painterResource(id = R.drawable.logo_dicoding),
            contentDescription = "",
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Github Users List",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
            )
            Text(text = "By Hamas Azizan",
                style = TextStyle(fontWeight = FontWeight.Light, fontSize = 14.sp)
            )
        }
    }
}