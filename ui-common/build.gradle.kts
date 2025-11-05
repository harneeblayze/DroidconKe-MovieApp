plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.gyg.codelab.movies.ui"
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

    // Compose
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.material3)
    api(libs.androidx.compose.material.icons.extended)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.compose.foundation)
    
    // Coil for image loading
    api(libs.coil.compose)

    // Core
    implementation(libs.androidx.core.ktx)

    debugImplementation(libs.androidx.ui.tooling)
}
