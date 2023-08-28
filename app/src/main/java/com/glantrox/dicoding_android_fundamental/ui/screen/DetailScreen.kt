package com.glantrox.dicoding_android_fundamental.ui.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.glantrox.dicoding_android_fundamental.R
import com.glantrox.dicoding_android_fundamental.core.constant.ServiceState
import com.glantrox.dicoding_android_fundamental.core.models.Favourite
import com.glantrox.dicoding_android_fundamental.core.viewmodel.DetailViewModel
import com.glantrox.dicoding_android_fundamental.core.viewmodel.FavouriteViewModel
import com.glantrox.dicoding_android_fundamental.core.viewmodel.FollowsEvent
import com.glantrox.dicoding_android_fundamental.navigator.AppRouter
import com.glantrox.dicoding_android_fundamental.navigator.Screen
import com.glantrox.dicoding_android_fundamental.ui.pages.DetailPage
import com.glantrox.dicoding_android_fundamental.ui.pages.FollowerPage
import com.glantrox.dicoding_android_fundamental.ui.pages.FollowingPage
import com.glantrox.dicoding_android_fundamental.ui.widget.WarningMessage
import com.glantrox.dicoding_android_fundamental.ui.widget.WarningMessages
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


data class TabRowItem(
    val title: String,
    val screen: @Composable () -> Unit,
)

@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel = hiltViewModel(),
    favouriteViewModel: FavouriteViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current


    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val userDetailState by detailViewModel.currentDetailState
    val currentMessage by detailViewModel.currentMessage
    val userDetail by detailViewModel.userDetail
    val listOfUser by detailViewModel.userFollows
    val followsState by detailViewModel.currentFollowsState

    val favouriteData = Favourite(
        avatarUrl = userDetail.avatarUrl ?: "",
        id = userDetail.id ?: 0,
        login = userDetail.login ?: ""
    )

    when (pagerState.currentPage) {
        1 -> detailViewModel.setFollowsEvent(FollowsEvent.OnFollowers)
        2 -> detailViewModel.setFollowsEvent(FollowsEvent.OnFollowing)
    }

    LaunchedEffect(true) {
        detailViewModel.onEventFollows(userDetail.login ?: "")
    }

    LaunchedEffect(pagerState.currentPage) {
        detailViewModel.onEventFollows(userDetail.login ?: "")
    }

    val tabRowItems = listOf(
        TabRowItem(
            title = "Detail",
            screen = { DetailPage(userDetail) },
        ),
        TabRowItem(
            title = "${userDetail.followers} Followers",
            screen = {
                FollowerPage(listOfUser, followsState, callback = {
                    detailViewModel.setPreviousId(userDetail.login ?: "")
                    detailViewModel.getCurrentUserDetail(it)
                    AppRouter.push(navController, Screen.DetailScreen.route)
                }, callbackRefresh = {
                    detailViewModel.onEventFollows(userDetail.login ?: "")
                })
            },
        ),
        TabRowItem(
            title = "${userDetail.following} Following",
            screen = {
                FollowingPage(listOfUser, followsState,
                    callback = {
                        detailViewModel.setPreviousId(userDetail.login ?: "")
                        detailViewModel.getCurrentUserDetail(it)
                        AppRouter.push(navController, Screen.DetailScreen.route)
                    }, callbackRefresh = {
                        detailViewModel.onEventFollows(userDetail.login ?: "")
                    })
            },
        )
    )

    return Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.DarkGray),
                title = {
                    Text(
                        "User Detail",
                        style = TextStyle(
                            color = Color.White
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (detailViewModel.previousId.value != "") {
                            detailViewModel.getCurrentUserDetail(detailViewModel.previousId.value)
                        }
                        AppRouter.pop(navController)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    AnimatedVisibility(
                        visible = detailViewModel.currentDetailState.value == ServiceState.Success
                    ) {
                        IconButton(onClick = {
                            favouriteViewModel.insertFavourite(favouriteData)
                            Toast.makeText(context, "Added to Favourite!", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(imageVector = Icons.Rounded.Favorite, contentDescription = "")
                        }
                    }
                }
            )
        }
    ) {
            Column(
                modifier = Modifier.padding(it)
            ) {
               when(userDetailState) {
                   ServiceState.Empty -> WarningMessage(WarningMessages.EMPTY)
                   ServiceState.Loading -> WarningMessage(WarningMessages.LOADING)
                   ServiceState.Error -> WarningMessage(WarningMessages.ERROR, errorCode = currentMessage)
                   ServiceState.Success ->  Column(
                       modifier = Modifier
                           .background(Color.DarkGray)
                           .fillMaxWidth()
                           .padding(top = 32.dp),
                       horizontalAlignment = Alignment.CenterHorizontally,
                       verticalArrangement = Arrangement.Center
                   ) {
                       AsyncImage(
                           model = userDetail.avatarUrl,
                           contentDescription = "",
                           modifier = Modifier
                               .clip(CircleShape)
                               .size(82.dp)
                       )
                       Spacer(modifier = Modifier.height(12.dp))
                       Text(
                           userDetail.name ?: "",
                           style = TextStyle(
                               fontSize = 18.sp,
                               color = Color.White,
                               fontWeight = FontWeight.Bold
                           )
                       )
                       Text(
                           userDetail.login ?: "",
                           style = TextStyle(
                               fontSize = 14.sp,
                               color = Color.White
                           )
                       )
                       Spacer(modifier = Modifier.height(12.dp))
                       TabRow(
                           backgroundColor = Color.DarkGray,
                           selectedTabIndex = pagerState.currentPage,
                           indicator = { tabPositions ->
                               TabRowDefaults.Indicator(
                                   Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                                   color = Color.White
                               )
                           },
                       ) {
                           tabRowItems.forEachIndexed { index, item ->
                               Tab(
                                   selected = pagerState.currentPage == index,
                                   onClick = {
                                       coroutineScope.launch {
                                           pagerState.animateScrollToPage(
                                               index
                                           )
                                       }
                                   },
                                   text = {
                                       Text(
                                           text = item.title,
                                           style = TextStyle(color = Color.White),
                                           maxLines = 2,
                                           overflow = TextOverflow.Ellipsis,
                                       )
                                   }
                               )
                           }
                       }
                   }
               }
                HorizontalPager(
                    count = tabRowItems.size,
                    state = pagerState,
                ) {
                    tabRowItems[pagerState.currentPage].screen()
                }
            }
    }
}


