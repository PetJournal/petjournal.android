plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
//    id("kotlin-kapt")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.soujunior.petjournal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.soujunior.petjournal"
        minSdk = 27
        targetSdk = 34
        versionCode = 6
        versionName = "1.0.6"

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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":database"))
    implementation(project(":data"))

    val composeUiVersion = "1.4.2"

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("io.insert-koin:koin-core:3.1.2")
    implementation("io.insert-koin:koin-android:3.1.2")
    implementation("io.insert-koin:koin-androidx-compose:3.1.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation ("com.github.Kaaveh:sdp-compose:1.1.0")
    // Room
//    val roomVersion = "2.3.0"
//    implementation("androidx.room:room-runtime:$roomVersion")
//    kapt("androidx.room:room-compiler:$roomVersion")
//    implementation("androidx.room:room-ktx:$roomVersion")

    // Room
//    val roomVersion = "2.3.0"
//    implementation("androidx.room:room-runtime:$roomVersion")
//    ksp("androidx.room:room-compiler:$roomVersion")
//    implementation("androidx.room:room-ktx:$roomVersion")
    // Room
    val roomVersion = "2.3.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    implementation("androidx.room:room-ktx:$roomVersion")

    // BANNERSLIDER
    implementation("io.coil-kt:coil-compose:1.4.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.13.0")

    // MATERIAL 2
    implementation("androidx.compose.material:material:$composeUiVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeUiVersion")
    // MATERIAL 3 - MATERIAL YOU
    implementation("com.google.android.material:material:1.9.0")

    // MATERIAL 3 - STATUS BAR
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    implementation("androidx.navigation:navigation-compose:2.6.0-beta01")
    implementation("androidx.compose.material3:material3:1.1.2")
    // COMPOSE
    implementation("androidx.activity:activity-compose:1.7.1")
    implementation("androidx.compose.ui:ui:$composeUiVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")
    implementation("androidx.compose.ui:ui-util:$composeUiVersion")

    implementation("com.github.bumptech.glide:glide:4.12.0")
    ksp("com.github.bumptech.glide:ksp:4.14.2")
    //kapt("com.github.bumptech.glide:compiler:4.12.0")

    // TESTE - COMPOSE
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeUiVersion")

    testImplementation("androidx.arch.core:core-testing:2.2.0")
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}