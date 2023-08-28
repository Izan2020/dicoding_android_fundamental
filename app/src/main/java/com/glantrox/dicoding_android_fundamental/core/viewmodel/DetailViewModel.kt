package com.glantrox.dicoding_android_fundamental.core.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glantrox.dicoding_android_fundamental.core.constant.ServiceState
import com.glantrox.dicoding_android_fundamental.core.data.remote.ApiInterface
import com.glantrox.dicoding_android_fundamental.core.models.DetailResponse

import com.glantrox.dicoding_android_fundamental.core.models.UsersResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class FollowsEvent { OnFollowing, OnFollowers }

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val apiInterface: ApiInterface
): ViewModel() {


    // Previous ID
    // To be honest i still couldn't find the solution for this, it can only stack 1 state of user ID
    // maybe i should use stack list of string that saves the state of IDs
    // Maybe you guys 'code-reviewer' can give me an advice for this case
    private val _previousId = mutableStateOf("")
    val previousId : State<String> = _previousId

    // Current Follows Event
    private val _followsEvent = mutableStateOf(FollowsEvent.OnFollowing)

    // Current Follows
    @SuppressLint("MutableCollectionMutableState")
    private val _userFollows = mutableStateOf(UsersResponse())
    val userFollows : State<UsersResponse> = _userFollows
    // Current Follow Details
    private val _currentFollowsState = mutableStateOf(ServiceState.Empty)
    val currentFollowsState : State<ServiceState> = _currentFollowsState

    // Current Detail
    private val _userDetail = mutableStateOf(DetailResponse())
    val userDetail : State<DetailResponse> = _userDetail

    // Current Detail State
    private val _currentDetailState = mutableStateOf(ServiceState.Empty)
    val currentDetailState : State<ServiceState> = _currentDetailState

    // Current Message
    private val _currentMessage = mutableStateOf("")
    val currentMessage : State<String> = _currentMessage

    fun getCurrentUserDetail(username: String) {
        viewModelScope.launch {
            try {
                setCurrentDetailState(ServiceState.Loading)
                delay(1500)
                val response = apiInterface.fetchUserDetailByUsername(username)
                _userDetail.value = response
                setCurrentDetailState(ServiceState.Success)

            } catch (e: Exception) {
                Log.d("debugDetail", "Error Video Detail ${e.message} $username")
                setCurrentDetailState(ServiceState.Error)
                setCurrentMessage(e.message ?: "Unknown Error Occurred")
            }
        }
    }

     fun onEventFollows(username: String) {
        when(_followsEvent.value) {
            FollowsEvent.OnFollowing -> {
                viewModelScope.launch {
                    try {
                        setCurrentFollowsState(ServiceState.Loading)
                        val response = apiInterface.fetchUsersFollowingByUsername(username)
                        _userFollows.value = response
                        setCurrentFollowsState(ServiceState.Success)
                    } catch (e: Exception) {
                        Log.d("debugDetail", "Error OnFollowing ${e.message} \nUsername : $username")
                        setCurrentFollowsState(ServiceState.Error)
                        setCurrentMessage(e.message ?: "Unknown Error Occurred")
                    }
                }
            }
            FollowsEvent.OnFollowers -> {
                viewModelScope.launch {
                    try {
                        setCurrentFollowsState(ServiceState.Loading)
                        val response = apiInterface.fetchUsersFollowersByUsername(username)
                        _userFollows.value = response
                        setCurrentFollowsState(ServiceState.Success)
                    } catch (e: Exception) {
                        Log.d("debugDetail", "Error OnFollowers ${e.cause?.message} \nUsername : $username ")
                        setCurrentFollowsState(ServiceState.Error)
                        setCurrentMessage(e.message ?: "Unknown Error Occurred")
                    }
                }
            }
        }
    }

    fun setFollowsEvent(value: FollowsEvent) {
        _followsEvent.value = value
    }

    private fun setCurrentDetailState(value: ServiceState) {
        _currentDetailState.value = value
    }

    private fun setCurrentFollowsState(value: ServiceState) {
        _currentFollowsState.value = value
    }

    private fun setCurrentMessage(value: String) {
        _currentMessage.value =value
    }

    fun setPreviousId(value: String) {
        _previousId.value = value
    }


}