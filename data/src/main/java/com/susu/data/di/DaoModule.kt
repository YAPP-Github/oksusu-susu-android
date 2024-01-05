package com.susu.data.di

import com.susu.data.RoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Singleton
    @Provides
    fun provideRecentSearchDao(db: RoomDataBase) = db.ledgerRecentSearchDao()
}
