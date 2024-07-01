package android.example.newsappcompose.domain.usecases.appentry

import android.example.newsappcompose.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserManager: LocalUserManager // inject LocalUserManager interface for better testability
) {
    operator fun invoke(): Flow<Boolean> = localUserManager.readAppEntry() // Read the app entry value from the DataStore.
}