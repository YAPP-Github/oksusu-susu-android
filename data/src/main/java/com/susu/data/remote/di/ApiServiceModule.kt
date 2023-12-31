package com.susu.data.remote.di

import com.susu.data.remote.api.SignUpService
import com.susu.data.remote.api.TokenService
import com.susu.data.remote.api.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideSignUpService(retrofit: Retrofit): SignUpService {
        return retrofit.create(SignUpService::class.java)
    }

    @Singleton
    @Provides
    fun provideTokenService(@AuthRetrofit retrofit: Retrofit): TokenService {
        return retrofit.create(TokenService::class.java)
    }
}
