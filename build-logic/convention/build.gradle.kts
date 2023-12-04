@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    `kotlin-dsl`
}

group = "com.susu.buildlogic.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "susu.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "susu.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "susu.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "susu.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "susu.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("javaLibrary") {
            id = "susu.java.library"
            implementationClass = "JavaLibraryConventionPlugin"
        }
        register("featureCompose") {
            id = "susu.android.feature.compose"
            implementationClass = "FeatureComposeConventionPlugin"
        }
        register("remote") {
            id = "susu.android.remote"
            implementationClass = "RemoteConventionPlugin"
        }
    }
}
