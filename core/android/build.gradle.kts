@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.susu.android.library)
    alias(libs.plugins.susu.android.hilt)
}

android {
    namespace = "com.susu.core.android"
}
