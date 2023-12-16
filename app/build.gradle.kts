@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.susu.android.application)
    alias(libs.plugins.susu.android.application.compose)
    alias(libs.plugins.susu.android.hilt)
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

    implementation(projects.domain)
    implementation(projects.data)
    implementation(libs.timber)
}
