package com.rokiba.udacityshoestore.data.local

import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class MyPreferences @Inject constructor(val prefs: SharedPreferences, val gson: Gson) {

    fun delete(key: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            remove(key)
            commit()
        }
    }

    fun read(key: String, value: String): String? {
        return prefs.getString(key, value)
    }

    fun read(key: String, value: Long): Long? {
        return prefs.getLong(key, value)
    }

    fun read(key: String, value: Boolean): Boolean {
        return prefs.getBoolean(key, value)
    }

    fun write(key: String, value: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(key, value)
            commit()
        }
    }

    fun write(key: String, value: Long) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putLong(key, value)
            commit()
        }
    }

    fun write(key: String, value: Boolean) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putBoolean(key, value)
            commit()
        }
    }

    fun setLanguage(lang: String) {
        write("lang", lang)
    }

    fun getLanguage(): String {
        return read("lang", "en")!!
    }

    fun setLanguageName(lang: String) {
        write("langName", lang)
    }

    fun getLanguageName(): String {
        return read("langName", "English")!!
    }

}