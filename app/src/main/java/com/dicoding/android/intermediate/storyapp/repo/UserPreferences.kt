package com.dicoding.android.intermediate.storyapp.repo

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>){
    private val tokenKey = stringPreferencesKey("token")
    private val nameKey = stringPreferencesKey("name")

    fun getUserName() : Flow<String> {
        return dataStore.data.map {
            it[nameKey] ?: ""
        }
    }

    suspend fun saveUserName(name: String) {
        dataStore.edit {
            it[nameKey] = name
        }
        Log.e(this::class.java.simpleName, "saveUserName token: $name")
    }

    fun getUserToken() : Flow<String> {
        return dataStore.data.map {
            it[tokenKey] ?: ""
        }
    }

    suspend fun saveUserToken(token: String) {
        dataStore.edit {
            it[tokenKey] = token
        }
        Log.e(this::class.java.simpleName, "saveUserInfo token: $token")
    }

    suspend fun clearData() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        private var INSTANCE : UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>) : UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}