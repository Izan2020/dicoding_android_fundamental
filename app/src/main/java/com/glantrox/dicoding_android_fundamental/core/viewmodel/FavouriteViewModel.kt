package com.glantrox.dicoding_android_fundamental.core.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glantrox.dicoding_android_fundamental.core.constant.ServiceState
import com.glantrox.dicoding_android_fundamental.core.data.local.FavouriteDatabase
import com.glantrox.dicoding_android_fundamental.core.models.Favourite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class  FavouriteViewModel @Inject constructor(
private val database: FavouriteDatabase
): ViewModel() {
    // List Of Favourites
    private val _listOfFavourites = mutableStateListOf<Favourite>()
    val listOfFavourites : SnapshotStateList<Favourite>
        get() = _listOfFavourites

    // List Of Favourites State
    private val _listOfFavouriteState = mutableStateOf(ServiceState.Empty)
    val listOfFavouriteState : State<ServiceState> = _listOfFavouriteState

    // Current Message
    private val _currentMessage = mutableStateOf("")
    val currentMessage : State<String> = _currentMessage



    fun getListOfFavourites() {
       viewModelScope.launch {
           try {
               setListOfFavouriteState(ServiceState.Loading)
               delay(1500)
               val localSource = database.FavouriteDao().getFavourites()
               if(localSource.isNotEmpty()) {
                   _listOfFavourites.addAll(localSource)
                   setListOfFavouriteState(ServiceState.Success)
               } else {
                   setListOfFavouriteState(ServiceState.Empty)
               }
           } catch (e: Exception) {
               Log.d("favouriteDebug", "${e.message}")
               setCurrentMessage(e.message.toString())
               setListOfFavouriteState(ServiceState.Error)
           }
       }
    }

    fun deleteFavourite(user: Favourite) {
        viewModelScope.launch {
            database.FavouriteDao().deleteFavourite(user)
            _listOfFavourites.remove(user)
            if(_listOfFavourites.toList().isEmpty()) {
                setListOfFavouriteState(ServiceState.Empty)
            }

        }
    }

    fun insertFavourite(user: Favourite) {
        viewModelScope.launch {
            database.FavouriteDao().insertFavourite(user)
        }
    }

    fun deleteAllFavourite() {
        viewModelScope.launch {
            database.FavouriteDao().deleteAllFavourites()
            _listOfFavourites.removeAll(emptyList())
            setListOfFavouriteState(ServiceState.Empty)
        }
    }


    private fun setListOfFavouriteState(value: ServiceState) {
        _listOfFavouriteState.value = value
    }

    private fun setCurrentMessage(value: String) {
        _currentMessage.value = value
    }

}
