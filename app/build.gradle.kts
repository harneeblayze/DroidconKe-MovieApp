plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
}

android {
  namespace = "com.harny.droidconkemovieapp"
  compileSdk = 36

  defaultConfig {
    applicationId = "com.harny.droidconkemovieapp"
    minSdk = 29
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    // Read API key from local.properties
    val localPropertiesFile = rootProject.file("local.properties")
    val apiKey = if (localPropertiesFile.exists()) {
      val props = org.jetbrains.kotlin.konan.properties.Properties()
      props.load(localPropertiesFile.inputStream())
      props.getProperty("TMDB_API_KEY", "")
    } else {
      ""
    }

    buildConfigField("String", "TMDB_API_KEY", "\"$apiKey\"")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
    jniLibs {
      pickFirsts += listOf("**/*.so")
    }
  }
}

dependencies {
  implementation(project(":domain"))
  implementation(project(":data"))
  implementation(project(":feature-mvvm"))
  implementation(project(":feature-mvi"))
  implementation(project(":feature-workflow"))
  implementation(project(":ui-common"))

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.lifecycle.runtime.compose)

  // Navigation
  implementation(libs.androidx.navigation.compose)

  implementation(libs.koin.android)
  implementation(libs.koin.androidx.compose)

  implementation(libs.retrofit)
  implementation(libs.retrofit.converter.gson)
  implementation(libs.okhttp)
  implementation(libs.okhttp.logging.interceptor)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}