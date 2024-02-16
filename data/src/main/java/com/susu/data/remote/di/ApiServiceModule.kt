package com.susu.data.remote.di

import com.susu.data.remote.api.AuthService
import com.susu.data.remote.api.BlockService
import com.susu.data.remote.api.CategoryService
import com.susu.data.remote.api.EnvelopesService
import com.susu.data.remote.api.FriendService
import com.susu.data.remote.api.LedgerService
import com.susu.data.remote.api.OnboardService
import com.susu.data.remote.api.ReportService
import com.susu.data.remote.api.SignUpService
import com.susu.data.remote.api.StatisticsService
import com.susu.data.remote.api.TermService
import com.susu.data.remote.api.TokenService
import com.susu.data.remote.api.UserService
import com.susu.data.remote.api.VoteService
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
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
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

    @Singleton
    @Provides
    fun providesTermService(retrofit: Retrofit): TermService {
        return retrofit.create(TermService::class.java)
    }

    @Singleton
    @Provides
    fun providesLedgerService(retrofit: Retrofit): LedgerService {
        return retrofit.create(LedgerService::class.java)
    }

    @Singleton
    @Provides
    fun providesCategoryService(retrofit: Retrofit): CategoryService {
        return retrofit.create(CategoryService::class.java)
    }

    @Singleton
    @Provides
    fun providesEnvelopeService(retrofit: Retrofit): EnvelopesService {
        return retrofit.create(EnvelopesService::class.java)
    }

    @Singleton
    @Provides
    fun providesUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun providesFriendService(retrofit: Retrofit): FriendService {
        return retrofit.create(FriendService::class.java)
    }

    @Singleton
    @Provides
    fun providesStatisticsService(retrofit: Retrofit): StatisticsService {
        return retrofit.create(StatisticsService::class.java)
    }

    @Singleton
    @Provides
    fun providesVoteService(retrofit: Retrofit): VoteService {
        return retrofit.create(VoteService::class.java)
    }

    @Singleton
    @Provides
    fun providesReportService(retrofit: Retrofit): ReportService {
        return retrofit.create(ReportService::class.java)
    }

    @Singleton
    @Provides
    fun providesBlockService(retrofit: Retrofit): BlockService {
        return retrofit.create(BlockService::class.java)
    }

    @Singleton
    @Provides
    fun providesOnboardService(retrofit: Retrofit): OnboardService {
        return retrofit.create(OnboardService::class.java)
    }
}
