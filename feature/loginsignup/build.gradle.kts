import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.susu.android.feature.compose)
}

android {
    namespace = "com.susu.feature.loginsignup"
    defaultConfig {
        manifestPlaceholders["KAKAO_APP_KEY"] = "kakao${getApiKey("KAKAO_APP_KEY")}"
    }
}

dependencies {
    implementation(libs.kakao.sdk.user)
}

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}
