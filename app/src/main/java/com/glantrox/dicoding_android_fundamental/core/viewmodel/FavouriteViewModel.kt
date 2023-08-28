package com.glantrox.dicoding_android_fundamental.core.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glantrox.dicoding_android_fundamental.core.constant.ServiceState
import com.glantrox.dicoding_android_fundamental.core.data.local.FavouriteDatabase
import com.glantrox.dicoding_android_fundamental.core.models.Favourite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class Favourites(
     var listOfFavourites: List<Favourite> = emptyList()
 )

@HiltViewModel
class  FavouriteViewModel @Inject constructor(
private val database: FavouriteDatabase
): ViewModel() {

    // List Of Favourites
    private val _listOfFavourites = mutableStateOf(Favourites())
    val listOfFavourites : State<Favourites> = _listOfFavourites

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
               delay(2500)
               val localSource = database.FavouriteDao().getFavourites()
               if(localSource.isNotEmpty()) {
                   _listOfFavourites.value.listOfFavourites = localSource
                   setListOfFavouriteState(ServiceState.Success)
               } else {
                   setListOfFavouriteState(ServiceState.Empty)
               }
           } catch (e: Exception) {
               setListOfFavouriteState(ServiceState.Error)
           }
       }
    }

    fun deleteFavourite(user: Favourite) {
        viewModelScope.launch {
            database.FavouriteDao().deleteFavourite(user)
            database.FavouriteDao().getFavourites()
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
            database.FavouriteDao().getFavourites()
            setListOfFavouriteState(ServiceState.Empty)
        }
    }


    private fun setListOfFavouriteState(value: ServiceState) {
        _listOfFavouriteState.value = value
    }

}
