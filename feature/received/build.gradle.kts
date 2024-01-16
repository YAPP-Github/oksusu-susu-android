@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.susu.android.feature.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.susu.feature.received"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}
