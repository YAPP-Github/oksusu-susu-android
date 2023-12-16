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
    implementation(projects.core.android)
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.core.ui)

    implementation(projects.data)
    implementation(projects.domain)

    implementation(projects.feature.loginsignup)

    implementation(projects.domain)
    implementation(projects.data)
    implementation(libs.timber)
}
