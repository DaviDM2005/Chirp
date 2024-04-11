plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
}

android {
    namespace = "dav.project.chirp"
    compileSdk = 34

    defaultConfig {
        applicationId = "dav.project.chirp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    // Firebase dependencies
    implementation(platform("com.google.firebase:firebase-bom:32.7.4")) // Firebase BOM (Bill of Materials) for version management
    implementation("com.google.firebase:firebase-analytics") // Firebase Analytics
    implementation(libs.androidx.core.ktx) // AndroidX Core KTX
    implementation(libs.androidx.appcompat) // AndroidX AppCompat
    implementation(libs.material) // Material Components for Android
    implementation(libs.androidx.activity) // AndroidX Activity
    implementation(libs.androidx.constraintlayout) // ConstraintLayout for AndroidX
    implementation(libs.firebase.auth) // Firebase Authentication
    implementation(libs.firebase.database) // Firebase Realtime Database

    // Testing dependencies
    testImplementation(libs.junit) // JUnit for unit testing
    androidTestImplementation(libs.androidx.junit) // AndroidX JUnit for instrumented testing
    androidTestImplementation(libs.androidx.espresso.core) // Espresso Core for UI testing

    // Material Design components
    implementation ("com.google.android.material:material:1.3.0-alpha03") // Material Design Components

    // Coil Compose (commented out)
    implementation ("io.coil-kt:coil-compose:2.1.0")


    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    implementation("com.google.android.material:material:1.11.0")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")



}
