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
        versionName = "1.0"
    }
}

dependencies {
    implementation(projects.feature.navigator)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    implementation(libs.timber)
}
