package com.ruicomp.downloader.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


const val PREFERENCE_SETTING = "Common Setting"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_SETTING)

object MyDatastore {

    suspend fun saveToDatastore(context: Context, key:String, value: Boolean) = withContext(
        Dispatchers.IO){
//        dlog("on saveToDataStore: value= $value")
        val preferencesKey = booleanPreferencesKey(key)
        context.dataStore.edit { preference ->
            preference[preferencesKey] = value
        }
    }

    suspend fun getDatastore(context: Context, key: String, default:Boolean = true): Boolean = withContext(
        Dispatchers.IO) {
        val preferencesKey = booleanPreferencesKey(key)
        val valueFlow: Flow<Boolean> = context.dataStore.data.map { preference ->
            preference[preferencesKey] ?: default
        }
        return@withContext valueFlow.first()
    }

    fun getVip(context: Context): Boolean {
        var isVip = true
        runBlocking {
            val isSwitch = async {
                getDatastore(context = context, key = "isVip", false)
            }

            isVip = isSwitch.await()
//            dlog("on getVip local DataStore")
        }
        return isVip
    }

    fun saveDatastore(context: Context, key:String, value: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            saveToDatastore(context, key, value)
        }

    }

}

const val n1 = "tps://www.iesdo"