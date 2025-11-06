// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.compose) apply false
  alias(libs.plugins.spotless)
}

spotless {
  kotlin {
    target("**/*.kt")
    targetExclude("**/build/**/*.kt")
    ktlint().editorConfigOverride(
      mapOf(
        "ktlint_standard_function-naming" to "disabled",
        "ktlint_standard_property-naming" to "disabled",
        "ktlint_standard_class-naming" to "disabled",
        "ktlint_standard_filename" to "disabled",
        "ktlint_standard_package-name" to "disabled",
        "ktlint_standard_no-wildcard-imports" to "disabled",
        "ktlint_standard_comment-wrapping" to "disabled",
        "ktlint_standard_discouraged-comment-location" to "disabled",
        "ktlint_standard_no-empty-file" to "disabled",
      )
    )
  }
  kotlinGradle {
    target("*.gradle.kts")
    trimTrailingWhitespace()
    indentWithSpaces()
    endWithNewline()
  }
  java {
    target("**/*.java")
    removeUnusedImports()
    trimTrailingWhitespace()
    indentWithSpaces()
    endWithNewline()
  }
}
