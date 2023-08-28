package com.glantrox.dicoding_android_fundamental.core.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glantrox.dicoding_android_fundamental.core.data.local.AppPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferenceViewModel @Inject constructor(): ViewModel() {

    // Dark Mode
    private val _isDarkMode = mutableStateOf(AppPreference.darkMode)
    val isDarkMode : State<Boolean> = _isDarkMode

    fun setDarkMode(value: Boolean) {
        viewModelScope.launch {
            AppPreference.darkMode = value
            _isDarkMode.value = value
        }
    }


}