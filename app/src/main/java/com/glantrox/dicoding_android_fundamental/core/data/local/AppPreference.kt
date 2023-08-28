package com.glantrox.dicoding_android_fundamental.core.data.local

import com.chibatching.kotpref.KotprefModel

object AppPreference: KotprefModel() {
    var darkMode by booleanPref(false)
}