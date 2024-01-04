import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.implementation
import org.gradle.kotlin.dsl.projects

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.susu.android.library)
    alias(libs.plugins.susu.android.library.compose)
}

android {
    namespace = "com.susu.core.designsystem"
}

dependencies {
    implementation(projects.core.ui)
    implementation(libs.kotlinx.immutable)
}
