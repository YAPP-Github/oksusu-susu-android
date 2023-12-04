@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.susu.android.library)
    alias(libs.plugins.susu.android.hilt)
}

android {
    namespace = "com.susu.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.domain)

    implementation(libs.bundles.coroutine)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)

    implementation(libs.junit)
    implementation(libs.timber)
}
