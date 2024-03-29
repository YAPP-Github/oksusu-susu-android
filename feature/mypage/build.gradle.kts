@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.susu.android.feature.compose)
}

android {
    namespace = "com.susu.feature.mypage"
}

dependencies {
    implementation(libs.kakao.sdk.user)
    implementation(libs.play.app.update)
}
