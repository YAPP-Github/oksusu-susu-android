package com.susu.feature.loginsignup.di

import com.susu.domain.util.KakaoSdkProvider
import com.susu.feature.loginsignup.social.KakaoSdkProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class KakaoModule {

    @Binds
    @Singleton
    abstract fun bindsKakaoLoginProvider(
        kakaoLoginProviderImpl: KakaoSdkProviderImpl
    ): KakaoSdkProvider
}

