package com.susu.feature.loginsignup.di

import com.susu.domain.util.KakaoLoginProvider
import com.susu.feature.loginsignup.KakaoLoginProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class KakaoModule {

    @Binds
    abstract fun bindKakaoLoginProvider(
        kakaoLoginProviderImpl: KakaoLoginProviderImpl
    ): KakaoLoginProvider
}
