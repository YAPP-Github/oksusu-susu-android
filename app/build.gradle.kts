import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.susu.android.application)
    alias(libs.plugins.susu.android.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.oksusu.susu"

    defaultConfig {
        applicationId = "com.oksusu.susu"
        versionCode = 1
        versionName = "1.0.0-beta.0"
        buildConfigField("String", "KAKAO_APP_KEY", getApiKey("KAKAO_APP_KEY"))
        manifestPlaceholders["KAKAO_APP_KEY"] = "kakao${getApiKey("KAKAO_REDIRECT_KEY")}"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.feature.navigator)
    implementation(projects.data)
    implementation(libs.kakao.sdk.user)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    implementation(libs.timber)
}

@Suppress
fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey) ?: "default"
}
