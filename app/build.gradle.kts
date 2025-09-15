plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.detekt)
    id("kotlin-parcelize")
}

android {
    compileSdk = 36

    defaultConfig {
        applicationId = "otus.gpb.homework.activities"
        minSdk = 23
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
    namespace = "otus.gpb.homework.activities"
    buildFeatures {
        viewBinding = true
    }
}

detekt {
    source = files("src/main/java", "src/main/kotlin")
    config = files("$rootDir/config/detekt/detekt.yml")
}

tasks.named<io.gitlab.arturbosch.detekt.Detekt>("detekt").configure {
    reports {
        txt.required.set(false)
        html.required.set(true)
        md.required.set(false)
        xml.required.set(false)
        sarif.required.set(false)
        html.outputLocation.set(file("build/reports/detekt/detekt.html"))
    }
}

dependencies {
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.androidx.material)
    implementation(libs.androidx.fragment)
    implementation(libs.google.material)
    implementation(libs.picasso)
}