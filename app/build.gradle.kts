plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
<<<<<<< HEAD
=======
    id("org.jetbrains.kotlin.kapt")
    id("kotlin-parcelize")
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3
}

android {
    namespace = "com.satria.gymer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.satria.gymer"
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

    buildFeatures {
        viewBinding = true
        mlModelBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

<<<<<<< HEAD
    buildFeatures {
        viewBinding = true
    }
=======
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
<<<<<<< HEAD
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
=======
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("com.google.guava:guava:30.1-android")

    implementation(libs.tensorflow.lite)
    implementation(libs.tensorflow.lite.support.v043)
    implementation(libs.tensorflow.lite.task.vision)

    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.lifecycle.v100)
    implementation(libs.androidx.camera.view.v100)
    implementation(libs.glide)

    implementation(libs.androidx.activity)
    annotationProcessor(libs.compiler)
    implementation("com.google.mlkit:object-detection:17.0.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.android)
    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
    implementation(libs.androidx.camera.view)
    implementation(libs.firebase.inappmessaging)
    implementation(libs.androidx.camera.lifecycle)
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.glide)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.camera.core)
    implementation(libs.camera.camera2)
    implementation(libs.camera.view)
    implementation(libs.camera.lifecycle)
    implementation(libs.okhttp)

    implementation(libs.tensorflow.lite)
    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)

}
