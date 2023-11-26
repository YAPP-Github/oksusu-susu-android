plugins {
  id("susu.java.library")
}

dependencies {
  implementation(projects.core.model)
  implementation(projects.core.common)

  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.hilt.core)
}
