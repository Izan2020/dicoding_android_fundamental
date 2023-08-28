package com.glantrox.dicoding_android_fundamental.ui.screen



import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.glantrox.dicoding_android_fundamental.R
import com.glantrox.dicoding_android_fundamental.core.constant.ServiceState.*
import com.glantrox.dicoding_android_fundamental.core.viewmodel.DetailViewModel
import com.glantrox.dicoding_android_fundamental.core.viewmodel.HomeEvent
import com.glantrox.dicoding_android_fundamental.core.viewmodel.HomeViewModel
import com.glantrox.dicoding_android_fundamental.navigator.AppRouter
import com.glantrox.dicoding_android_fundamental.navigator.Screen
import com.glantrox.dicoding_android_fundamental.ui.widget.ItemUser
import com.glantrox.dicoding_android_fundamental.ui.widget.WarningMessage
import com.glantrox.dicoding_android_fundamental.ui.widget.WarningMessages


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    detailViewModel: DetailViewModel = hiltViewModel(),
    navHostController: NavHostController = rememberNavController()
) {
    var searchQuery by remember { mutableStateOf("")}

    LaunchedEffect(true) {
        homeViewModel.getListOfUsers()
    }

    val listOfUsers by homeViewModel.listOfUser

    return Scaffold(
        topBar = {
            TopAppBar(
                title = {
               Text("Github.",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp)) },
                actions = {
                    IconButton(onClick = {
                        AppRouter.push(navHostController, Screen.SettingsScreen.route)
                    }) {
                        Icon(imageVector = Icons.Rounded.Settings , contentDescription = "")
                    }
                }
                 )
        },
        floatingActionButton = {
            ElevatedButton(
                   colors = ButtonDefaults.buttonColors(Color.DarkGray),
                onClick = {
                    AppRouter.push(navHostController, Screen.FavouriteScreen.route)
                }) {
                Icon(imageVector = Icons.Rounded.Star, contentDescription = "")
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(visible = homeViewModel.listOfUserState.value == Success ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .padding(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                ) {
                    TextField(
                        value = searchQuery, onValueChange = { query ->
                            searchQuery = query
                        },
                        leadingIcon = {
                            if (homeViewModel.eventState.value == HomeEvent.OnSearch || searchQuery != "") {
                                IconButton(onClick = {
                                    homeViewModel.setHomeEvent(HomeEvent.OnDefault)
                                    searchQuery = ""
                                    homeViewModel.getListOfUsers()
                                }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Close,
                                        contentDescription = ""
                                    )
                                }
                            }
                        },
                        trailingIcon = {
                            IconButton(onClick = {

                                homeViewModel.setHomeEvent(HomeEvent.OnSearch)
                                homeViewModel.getListOfUsers()
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_search_24),
                                    contentDescription = ""
                                )
                            }
                        },
                        placeholder = { Text("Search User") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Gray,
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
            when(homeViewModel.listOfUserState.value) {
                Empty -> WarningMessage(WarningMessages.EMPTY)
                Loading -> WarningMessage(WarningMessages.LOADING)
                Error -> WarningMessage(WarningMessages.ERROR,
                    errorCode = homeViewModel.currentMessage.value) {
                    homeViewModel.getListOfUsers()
                }
                Success -> LazyColumn {
                    items(listOfUsers) { user ->
                        ItemUser(user = user, onTap = { loginId ->
                            detailViewModel.getCurrentUserDetail(loginId)
                            AppRouter.push(navHostController, Screen.DetailScreen.route)
                        })
                    }
                }
            }
        }
    }
}