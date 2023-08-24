package com.glantrox.dicoding_android_fundamental.core.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glantrox.dicoding_android_fundamental.core.constant.ServiceState
import com.glantrox.dicoding_android_fundamental.core.data.Repository
import com.glantrox.dicoding_android_fundamental.core.models.DetailResponse

import com.glantrox.dicoding_android_fundamental.core.models.UsersResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class FollowsEvent {OnFollowing, OnFollowers}

class DetailViewModel: ViewModel() {
    val repository = Repository.create()

    // Previous ID
    // To be honest i still couldnt find the solution for this, it can only stack 1 state of user ID
    // maybe i should use stack list of string that saves the state of IDs
    // Maybe you guys 'code-reviewer' can give me an advice for this case
    private val _previousId = mutableStateOf("")
    val previousId : State<String> = _previousId

    // Current Follows Event
    private val _followsEvent = mutableStateOf(FollowsEvent.OnFollowing)
    val followsEvent : State<FollowsEvent> = _followsEvent

    // Current Follows
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

    fun clearCurrentUserDetail() {
        _userDetail.value = DetailResponse()
    }

     fun getCurrentUserDetail(username: String) {
        viewModelScope.launch {
            try {
                _setCurrentDetailState(ServiceState.Loading)
                delay(1500)
                val response = repository.fetchUserDetailByUsername(username)
                _userDetail.value = response
                _setCurrentDetailState(ServiceState.Success)

            } catch (e: Exception) {
                Log.d("debugDetail", "Error Video Detail ${e.message} $username")
                _setCurrentDetailState(ServiceState.Error)
                _setCurrentMessage(e.message ?: "Unknown Error Occured")
            }
        }
    }

     fun onEventFollows(username: String) {
        when(_followsEvent.value) {
            FollowsEvent.OnFollowing -> {
                viewModelScope.launch {
                    try {
                        _setCurrentFollowsState(ServiceState.Loading)
                        val response = repository.fetchUsersFollowingByUsername(username)
                        _userFollows.value = response
                        _setCurrentFollowsState(ServiceState.Success)
                    } catch (e: Exception) {
                        Log.d("debugDetail", "Error OnFollowing ${e.message} \nUsername : $username")
                        _setCurrentFollowsState(ServiceState.Error)
                        _setCurrentMessage(e.message ?: "Unknown Error Occured")
                    }
                }
            }
            FollowsEvent.OnFollowers -> {
                viewModelScope.launch {
                    try {
                        _setCurrentFollowsState(ServiceState.Loading)
                        val response = repository.fetchUsersFollowersByUsername(username)
                        _userFollows.value = response
                        _setCurrentFollowsState(ServiceState.Success)
                    } catch (e: Exception) {
                        Log.d("debugDetail", "Error OnFollowers ${e.cause?.message} \nUsername : $username ")
                        _setCurrentFollowsState(ServiceState.Error)
                        _setCurrentMessage(e.message ?: "Unknown Error Occured")
                    }
                }
            }
        }
    }

    fun setFollowsEvent(value: FollowsEvent) {
        _followsEvent.value = value
    }

    fun _setCurrentDetailState(value: ServiceState) {
        _currentDetailState.value = value
    }

    fun _setCurrentFollowsState(value: ServiceState) {
        _currentFollowsState.value = value
    }

    fun _setCurrentMessage(value: String) {
        _currentMessage.value =value
    }

    fun setPreviousId(value: String) {
        _previousId.value = value
    }


}