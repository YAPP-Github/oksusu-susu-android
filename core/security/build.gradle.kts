@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  id("susu.android.library")
  id("susu.android.hilt")
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.susu.core.security"
}

dependencies {
  implementation(projects.core.model)

  implementation(libs.bundles.coroutine)

  implementation(libs.androidx.datastore.core)
  implementation(libs.androidx.datastore.preferences)

  ksp(libs.encrypted.datastore.preference.ksp)
  implementation(libs.encrypted.datastore.preference.ksp.annotations)
  implementation(libs.encrypted.datastore.preference.security)

  implementation(libs.timber)
}
