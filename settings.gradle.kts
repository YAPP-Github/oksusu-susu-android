enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "Susu"
include(":app")
include(":core:model")
include(":core:common")
include(":core:designsystem")
include(":core:security")
include(":core:ui")
include(":local")
include(":remote")
include(":data")
include(":domain")
include(":feature")
