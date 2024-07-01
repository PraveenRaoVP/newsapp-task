package android.example.newsappcompose.data.manager

import android.content.Context
import android.example.newsappcompose.domain.manager.LocalUserManager
import android.example.newsappcompose.util.Constants
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManagerImpl(
    private val context: Context,
) : LocalUserManager {
    // Save the app entry value to the DataStore. Why do we need to save the app entry value?
    // The app entry value is used to determine if the user has opened the app for the first time.
    // If the user has opened the app for the first time, we can show the onboarding screen.
    override suspend fun saveAppEntry() {
        context.dataStore.edit {settings ->
            settings[PreferencesKeys.APP_ENTRY] = true
        }
    }

    // Read the app entry value from the DataStore. If the value is not found, return false.
    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { settings ->
            settings[PreferencesKeys.APP_ENTRY] ?: false
        }
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.USER_SETTINGS) // this is a read-only property
// why do we need to use datastore to save the app entry value? The DataStore is a data storage solution that allows you to store key-value
// pairs or typed objects with protocol buffers. It is designed to store data that is not sensitive and does not require encryption.
// The DataStore API is part of Jetpack DataStore, which is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers.
// Are they stored in the device's internal storage? Yes or no? - Yes, the DataStore API stores data in the device's internal storage. Location: app/data/data/<package_name>/files/datastore
private object PreferencesKeys {
    val APP_ENTRY = booleanPreferencesKey(Constants.APP_ENTRY)
}