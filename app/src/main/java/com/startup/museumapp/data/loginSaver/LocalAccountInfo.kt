package com.startup.museumapp.data.loginSaver

import android.content.Context
import androidx.core.content.edit

class LocalAccountInfo(context: Context){
    companion object{
        private const val SharedPrefsKey = "LocalAccountInfoPrefs"
        private const val TokenPrefKey = "TokenKey"
    }

    private val sharedPreferences = context.getSharedPreferences(SharedPrefsKey, Context.MODE_PRIVATE)

    var loginToken: String
        get() = sharedPreferences.getString(TokenPrefKey, "")?: ""
        set(value) {sharedPreferences.edit { putString(TokenPrefKey, value) }}
}