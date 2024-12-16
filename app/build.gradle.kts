plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.devtools.ksp)
 }

android {
    namespace = "com.example.digitalact"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.digitalact"
        minSdk = 27
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
    buildFeatures {
        viewBinding = true     }

    configurations.all {
        resolutionStrategy {
            force("org.jetbrains:annotations:23.0.0")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.zxing.android.embedded)
    implementation(libs.poi.ooxml)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.room.runtime) {
        exclude(group= "com.intellij", module = "annotations") }
    runtimeOnly(libs.com.google.devtools.ksp.gradle.plugin)
    annotationProcessor(libs.androidx.room.compiler) {
        exclude(group= "com.intellij", module = "annotations") }
    ksp(libs.androidx.room.compiler) {
        exclude(group= "com.intellij", module = "annotations") }
    implementation(libs.androidx.room.ktx) {
        exclude(group= "com.intellij", module = "annotations") }
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)



}