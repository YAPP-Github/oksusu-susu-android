package com.susu.data.di

import com.susu.data.repository.LedgerRecentSearchRepositoryImpl
import com.susu.data.repository.LoginRepositoryImpl
import com.susu.data.repository.SignUpRepositoryImpl
import com.susu.data.repository.TokenRepositoryImpl
import com.susu.domain.repository.LedgerRecentSearchRepository
import com.susu.domain.repository.LoginRepository
import com.susu.domain.repository.SignUpRepository
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
    abstract fun bindSignUpRepository(
        signUpRepositoryImpl: SignUpRepositoryImpl,
    ): SignUpRepository

    @Binds
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl,
    ): LoginRepository

    @Binds
    abstract fun bindLedgerRecentSearchRepository(
        ledgerRecentSearchRepositoryImpl: LedgerRecentSearchRepositoryImpl,
    ): LedgerRecentSearchRepository
}
