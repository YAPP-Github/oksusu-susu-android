@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  id("susu.android.library")
  id("susu.android.hilt")
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.susu.local"
}

dependencies {
  implementation(projects.core.model)
  implementation(projects.core.database)
  implementation(projects.data)

  ksp(libs.room.compiler)
  implementation(libs.room.runtime)
  implementation(libs.room.ktx)

  implementation(libs.bundles.coroutine)
  implementation(libs.androidx.datastore.core)
  implementation(libs.androidx.datastore.preferences)
}
