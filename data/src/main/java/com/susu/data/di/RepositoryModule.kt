package com.susu.data.di

import com.susu.data.repository.AuthRepositoryImpl
import com.susu.data.repository.TokenRepositoryImpl
import com.susu.domain.repository.AuthRepository
import com.susu.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTokenRepository(
        tokenRepositoryImpl: TokenRepositoryImpl,
    ): TokenRepository

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl,
    ): AuthRepository
}
