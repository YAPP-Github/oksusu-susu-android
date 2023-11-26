plugins {
  id("susu.android.application")
  id("susu.android.application.compose")
  id("susu.android.hilt")
}

android {
  namespace = "com.oksusu.susu"

  defaultConfig {
    applicationId = "com.oksusu.susu"
    versionCode = 1
    versionName = "1.0"
  }
}

dependencies {

  implementation(libs.timber)
}
