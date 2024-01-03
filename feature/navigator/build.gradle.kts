@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.susu.android.feature.compose)
}

android {
    namespace = "com.susu.feature.navigator"
}

dependencies {
    implementation(projects.feature.community)
    implementation(projects.feature.loginsignup)
    implementation(projects.feature.mypage)
    implementation(projects.feature.received)
    implementation(projects.feature.sent)
    implementation(projects.feature.statistics)

    implementation(libs.androidx.splashscreen)
}
