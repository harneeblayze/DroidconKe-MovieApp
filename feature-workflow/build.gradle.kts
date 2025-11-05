plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.gyg.codelab.movies.workflow"
    compileSdk = 36

    defaultConfig {
        minSdk = 29
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":ui-common"))

    // Workflow
    implementation(libs.workflow.ui.compose)
    implementation(libs.workflow.runtime)
    implementation(libs.workflow.core)

  // Compose
  implementation(libs.androidx.activity.compose)

  implementation(libs.coil.compose)

  // Koin
  implementation(libs.koin.androidx.compose)

  // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
  // testImplementation(libs.workflow.testing) // Removed - not available
    testImplementation(libs.kotlinx.coroutines.test)
}
