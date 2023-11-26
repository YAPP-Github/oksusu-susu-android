@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  id("susu.android.library")
  id("susu.android.hilt")
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.susu.core.network"
}

dependencies {
  implementation(projects.core.model)

  implementation(libs.bundles.coroutine)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.retrofit.core)
  implementation(libs.retrofit.kotlin.serialization)
  implementation(libs.okhttp.logging)

  implementation(libs.timber)
}
