package com.susu.data.remote.di

import android.app.DownloadManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExcelModule {

    @Provides
    @Singleton
    fun providesDownloadManager(@ApplicationContext context: Context): DownloadManager {
        return context.getSystemService(DownloadManager::class.java)
    }
}
