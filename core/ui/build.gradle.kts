@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.susu.android.library)
    alias(libs.plugins.susu.android.library.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.susu.core.ui"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}
