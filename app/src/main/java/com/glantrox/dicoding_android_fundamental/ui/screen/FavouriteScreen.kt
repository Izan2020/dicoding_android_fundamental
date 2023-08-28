package com.glantrox.dicoding_android_fundamental.ui.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.glantrox.dicoding_android_fundamental.core.constant.ServiceState

import com.glantrox.dicoding_android_fundamental.core.viewmodel.DetailViewModel
import com.glantrox.dicoding_android_fundamental.core.viewmodel.FavouriteViewModel
import com.glantrox.dicoding_android_fundamental.navigator.AppRouter
import com.glantrox.dicoding_android_fundamental.navigator.Screen
import com.glantrox.dicoding_android_fundamental.ui.widget.ItemFavourite

import com.glantrox.dicoding_android_fundamental.ui.widget.WarningMessage
import com.glantrox.dicoding_android_fundamental.ui.widget.WarningMessages

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavouriteScreen(
    detailViewModel: DetailViewModel = hiltViewModel(),
    favouriteViewModel: FavouriteViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current

    val listOfFavouritesState by favouriteViewModel.listOfFavouriteState
    val listOfFavourites by favouriteViewModel.listOfFavourites

    LaunchedEffect(true) {
        favouriteViewModel.getListOfFavourites()
    }

    return Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Favourites")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        AppRouter.pop(navController)
                    }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "")
                    }
                },
                actions = {
                  if(listOfFavouritesState == ServiceState.Success) {
                      IconButton(onClick = {
                          favouriteViewModel.deleteAllFavourite()
                          Toast.makeText(
                              context,
                              "All Saved Favorites Deleted!",
                              Toast.LENGTH_SHORT
                          ).show()
                      }) {
                          Icon(imageVector = Icons.Rounded.Delete, contentDescription = "")
                      }
                  }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            when(listOfFavouritesState) {
                ServiceState.Empty -> WarningMessage(WarningMessages.EMPTY)
                ServiceState.Loading -> WarningMessage(WarningMessages.LOADING)
                ServiceState.Error -> WarningMessage(WarningMessages.ERROR, errorCode = favouriteViewModel.currentMessage.value)
                ServiceState.Success -> LazyColumn {
                    items(listOfFavourites.listOfFavourites) { user ->
                        ItemFavourite(
                            user = user,
                            onTap = { loginId ->
                            detailViewModel.getCurrentUserDetail(loginId)
                            AppRouter.push(navController, Screen.DetailScreen.route)
                        },
                        isManage = true,
                        onTapDelete = {
                            favouriteViewModel.deleteFavourite(user)
                            Toast.makeText(context, "User Deleted!", Toast.LENGTH_SHORT).show()

                        }
                        )
                    }
                }
            }
        }
    }
}