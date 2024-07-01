package android.example.newsappcompose.domain.manager

import kotlinx.coroutines.flow.Flow


interface LocalUserManager {
    suspend fun saveAppEntry() // save the app entry i.e if the user is logging for the first time or not

    fun readAppEntry(): Flow<Boolean> // read the app entry value from the DataStore
}