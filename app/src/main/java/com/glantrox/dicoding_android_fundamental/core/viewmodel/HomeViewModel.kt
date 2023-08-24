package com.glantrox.dicoding_android_fundamental.core.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glantrox.dicoding_android_fundamental.core.constant.ServiceState
import com.glantrox.dicoding_android_fundamental.core.data.Repository
import com.glantrox.dicoding_android_fundamental.core.models.UsersResponse
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch
import java.lang.Exception

enum class HomeEvent { OnSearch, OnDefault }


class HomeViewModel : ViewModel() {
    val repository = Repository.create()

    // Event State
    private val _eventState = mutableStateOf(HomeEvent.OnDefault)
    val eventState: State<HomeEvent> = _eventState

    // List of User
    private val _listOfUser = mutableStateOf(UsersResponse())
    val listOfUser: State<UsersResponse> = _listOfUser

    // List of Videos State
    private val _listOfUserState = mutableStateOf(ServiceState.Empty)
    val listOfUserState: State<ServiceState> = _listOfUserState

    // Message
    private val _currentMessage = mutableStateOf("")
    val currentMessage: State<String> = _currentMessage

    fun getListOfUsers(value: String = "") {
        when (_eventState.value) {
            HomeEvent.OnSearch -> {
                viewModelScope.launch {
                    try {
                        _setCurrentListOfUserState(ServiceState.Loading)
                        delay(1200)
                        val response = repository.searchUserByUsername(value)
                        _listOfUser.value = response.items
                        _setCurrentListOfUserState(ServiceState.Success)
                    } catch (e: Exception) {
                        Log.d("debugHome", "Error OnSearch ${e.message}")
                        _setCurrentListOfUserState(ServiceState.Error)
                        _setCurrentMessage(e.message ?: "Unknown Error Occured")
                    }
                }
            }
            HomeEvent.OnDefault -> {
                viewModelScope.launch {
                    try {
                        _setCurrentListOfUserState(ServiceState.Loading)
                        delay(1200)
                        val response = repository.fetchListOfUsers()
                        _listOfUser.value = response
                        _setCurrentListOfUserState(ServiceState.Success)
                    } catch (e: Exception) {
                        Log.d("debugHome", "Error OnDefault ${e.message}")
                        _setCurrentListOfUserState(ServiceState.Error)
                        _setCurrentMessage(e.message ?: "Unknown Error Occured")
                    }
                }
            }
        }
    }

    fun _setCurrentMessage(value: String) {
        _currentMessage.value = value
    }

    fun _setCurrentListOfUserState(value: ServiceState) {
        _listOfUserState.value = value
    }

    fun setHomeEvent(value: HomeEvent) {
        _eventState.value = value
    }

}