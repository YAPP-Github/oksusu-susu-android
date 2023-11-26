@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  id("susu.android.remote")
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.susu.remote"
}

dependencies {
  implementation(libs.retrofit.core)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.kotlinx.datetime)
}
