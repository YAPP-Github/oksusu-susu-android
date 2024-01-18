package com.susu.data.local.di

import android.content.Context
import androidx.room.Room
import com.susu.data.local.RoomDataBase
import com.susu.data.local.RoomInMemoryDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DB_NAME = "database-susu"

    @Provides
    @Singleton
    fun provideRoomDataBase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        RoomDataBase::class.java,
        DB_NAME,
    ).build()

    @Provides
    @Singleton
    fun provideRoomInMemoryDataBase(
        @ApplicationContext context: Context,
    ) = Room.inMemoryDatabaseBuilder(
        context,
        RoomInMemoryDataBase::class.java,
    ).build()
}
