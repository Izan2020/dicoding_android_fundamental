package com.glantrox.dicoding_android_fundamental.ui.pages

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.glantrox.dicoding_android_fundamental.core.constant.ServiceState
import com.glantrox.dicoding_android_fundamental.core.models.UsersResponse
import com.glantrox.dicoding_android_fundamental.ui.widget.ItemUser
import com.glantrox.dicoding_android_fundamental.ui.widget.WarningMessage
import com.glantrox.dicoding_android_fundamental.ui.widget.WarningMessages

@Composable
fun FollowingPage(
    user: UsersResponse,
    state: ServiceState,
    callback: (String) -> Unit,
    callbackRefresh: () -> Unit
    ) {
    when(state) {
        ServiceState.Empty -> WarningMessage(WarningMessages.EMPTY)
        ServiceState.Loading -> WarningMessage(WarningMessages.LOADING)
        ServiceState.Error -> WarningMessage(WarningMessages.ERROR) {
            callbackRefresh()
        }
        ServiceState.Success -> LazyColumn {
            items(user) { user ->
                ItemUser(user = user, onTap = {
                    callback(user.login)
                })
            }
        }
    }
}