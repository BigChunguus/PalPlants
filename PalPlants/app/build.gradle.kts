plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.palplants"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.palplants"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.compose.ui:ui:1.2.0-beta01")
    implementation("androidx.compose.ui:ui-graphics:1.2.0-beta01")
    implementation("androidx.compose.ui:ui-tooling:1.2.0-beta01")
    implementation("androidx.compose.material3:material3:1.0.0-beta01")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("jp.wasabeef:glide-transformations:4.3.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
    implementation(files("C:\\Users\\alexc\\Documents\\GitHub\\PalPlants\\BotanicaCC\\dist\\BotanicaCC.jar"))
    implementation(files("C:\\Users\\alexc\\Documents\\GitHub\\PalPlants\\PojosBotanica\\dist\\PojosBotanica.jar"))
    implementation("androidx.viewpager2:viewpager2:1.1.0-alpha01")
    implementation("com.google.android.material:material:1.5.0-alpha01")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.0-beta01")
    debugImplementation("androidx.compose.ui:ui-tooling:1.2.0-beta01")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.2.0-beta01")
}