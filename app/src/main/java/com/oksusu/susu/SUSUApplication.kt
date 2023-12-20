package com.oksusu.susu

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SUSUApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(CustomTimberTree())
        }

        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }
}
