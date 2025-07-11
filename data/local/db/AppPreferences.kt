package com.example.travelapp.data.local.db

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("app_prefs")

@Singleton
class AppPreferences @Inject constructor(@ApplicationContext private val context: Context) {


    private val insertedProvincesKey = stringSetPreferencesKey("inserted_hotel_provinces")

    suspend fun getInsertedProvinces(): Set<String> {
        val prefs = context.dataStore.data.first()
        return prefs[insertedProvincesKey] ?: emptySet()
    }

    suspend fun clearAllProvinces() {
        context.dataStore.edit { prefs ->
            prefs[insertedProvincesKey] = emptySet()
        }
    }


    suspend fun addInsertedProvince(province: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[insertedProvincesKey] ?: emptySet()
            prefs[insertedProvincesKey] = current + province
        }
    }

    suspend fun isProvinceInserted(province: String): Boolean {
        return getInsertedProvinces().contains(province)
    }
}
