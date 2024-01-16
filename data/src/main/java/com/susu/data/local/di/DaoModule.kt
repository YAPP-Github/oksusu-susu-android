package com.susu.data.local.di

import com.susu.data.local.RoomDataBase
import com.susu.data.local.RoomInMemoryDataBase
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

    @Singleton
    @Provides
    fun provideCategoryConfigDao(db: RoomInMemoryDataBase) = db.categoryConfigDao()
}
