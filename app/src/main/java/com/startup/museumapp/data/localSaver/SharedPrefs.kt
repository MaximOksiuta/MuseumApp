package com.startup.museumapp.data.localSaver

import android.content.Context
import okhttp3.internal.cache2.Relay.Companion.edit

class SharedPrefs(context: Context) {
    companion object {
        private const val PREFS_KEY = "MUSEUMINFORMATION"
        private const val LIKES_KEY = "LIKES"
    }

    private val sharedPrefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

    var likedMuseums: List<Int>
        get() = (sharedPrefs.getStringSet(LIKES_KEY, emptySet())
            ?: emptySet<String>()).map { it.toInt() }
        set(value) {
            sharedPrefs.edit().putStringSet(LIKES_KEY, value.map { it.toString() }.toSet()).apply()
        }
}