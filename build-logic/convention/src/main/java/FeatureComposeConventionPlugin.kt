import com.susu.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

@Suppress("UNUSED")
internal class FeatureComposeConventionPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("susu.android.library")
        apply("susu.android.library.compose")
        apply("susu.android.hilt")
      }

      dependencies {
        "implementation"(project(":core:model"))
        "implementation"(project(":core:ui"))

        "implementation"(libs.findLibrary("kotlinx.coroutines.android").get())
        "implementation"(libs.findLibrary("kotlinx.coroutines.core").get())

        "androidTestImplementation"(libs.findLibrary("junit").get())
        "implementation"(libs.findLibrary("timber").get())
      }
    }
  }
}
