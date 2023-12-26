package com.susu.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.susu.data.security.SecurityPreferences
import com.susu.data.security.generateSecurityPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.thdev.useful.encrypted.data.store.preferences.security.generateUsefulSecurity
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() },
            ),
            produceFile = { context.preferencesDataStoreFile(DATASTORE_NAME) },
        )
    }

    @EncryptedDataStore
    @Singleton
    @Provides
    fun providedEncryptedDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() },
            ),
            produceFile = { context.preferencesDataStoreFile(ENCRYPTED_DATASTORE_NAME) },
        )
    }

    @Singleton
    @Provides
    fun provideSecurityPreference(
        @EncryptedDataStore dataStore: DataStore<Preferences>,
    ): SecurityPreferences = dataStore.generateSecurityPreferences(generateUsefulSecurity())

    private const val DATASTORE_NAME = "susu-datastore"
    private const val ENCRYPTED_DATASTORE_NAME = "susu-datastore-encrypted"
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class EncryptedDataStore
