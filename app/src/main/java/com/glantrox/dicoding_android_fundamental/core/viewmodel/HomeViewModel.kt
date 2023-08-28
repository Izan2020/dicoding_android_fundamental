package com.glantrox.dicoding_android_fundamental.core.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glantrox.dicoding_android_fundamental.core.constant.ServiceState
import com.glantrox.dicoding_android_fundamental.core.data.remote.ApiInterface
import com.glantrox.dicoding_android_fundamental.core.errorParser
import com.glantrox.dicoding_android_fundamental.core.models.UsersResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

enum class HomeEvent { OnSearch, OnDefault }


@HiltViewModel
class HomeViewModel @Inject  constructor(
    private val apiInterface: ApiInterface
) : ViewModel() {


    // Event State
    private val _eventState = mutableStateOf(HomeEvent.OnDefault)
    val eventState: State<HomeEvent> = _eventState

    // List of User
    @SuppressLint("MutableCollectionMutableState")
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
                    if (value == "") return@launch
                    try {
                        setCurrentListOfUserState(ServiceState.Loading)
                        delay(1200)
                        val response = apiInterface.searchUserByUsername(value)
                        _listOfUser.value = response.items
                        setCurrentListOfUserState(ServiceState.Success)
                    } catch (e: Exception) {
                        Log.d("debugHome", "Error OnSearch ${e.message}")
                        setCurrentListOfUserState(ServiceState.Error)

                        setCurrentMessage(errorParser(e) )
                    }
                }
            }
            HomeEvent.OnDefault -> {
                viewModelScope.launch {
                    try {
                        setCurrentListOfUserState(ServiceState.Loading)
                        delay(1200)
                        val response = apiInterface.fetchListOfUsers()
                        _listOfUser.value = response
                        setCurrentListOfUserState(ServiceState.Success)
                    } catch (e: Exception) {
                        Log.d("debugHome", "Error OnDefault \nLocalized :${e.localizedMessage}\n" +
                                "Default :${e.message}")
                        setCurrentListOfUserState(ServiceState.Error)
                        setCurrentMessage(errorParser(e) )
                    }
                }
            }
        }
    }

    private fun setCurrentMessage(value: String) {
        _currentMessage.value = value
    }

    private fun setCurrentListOfUserState(value: ServiceState) {
        _listOfUserState.value = value
    }

    fun setHomeEvent(value: HomeEvent) {
        _eventState.value = value
    }

}