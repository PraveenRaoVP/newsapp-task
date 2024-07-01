package android.example.newsappcompose.domain.usecases.appentry

import android.example.newsappcompose.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke() {
        localUserManager.saveAppEntry() // Save the app entry value to the DataStore.
    }
}