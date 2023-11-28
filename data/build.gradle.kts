@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.susu.android.data)
}

android {
    namespace = "com.susu.data"
}

dependencies {
    implementation(projects.domain)
}
