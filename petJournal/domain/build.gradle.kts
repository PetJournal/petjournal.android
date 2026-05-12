plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.soujunior.domain"
    compileSdk = 35

    defaultConfig {
        minSdk = 27
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("proguard-rules.pro")
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
        buildConfig = true
    }
}

dependencies {
    implementation("androidx.annotation:annotation-jvm:1.9.1")
    implementation("androidx.core:core-i18n:1.0.0")
    implementation("androidx.compose.ui:ui:1.10.3")
    val dependencies = rootProject.ext["dependencies"] as Map<String, String>
    implementation(dependencies["coroutineCore"]!!)
    implementation(dependencies["coroutineAndroid"]!!)
    implementation(dependencies["moshiKotlin"]!!)
    implementation(dependencies["retrofit2ConverterMoshi"]!!)
    implementation("com.google.code.gson:gson:2.10.1")

    testImplementation(rootProject.ext["testJunit"] as String)
    testImplementation(rootProject.ext["testMockk"] as String)
    testImplementation(rootProject.ext["testAssertk"] as String)
    testImplementation(rootProject.ext["testKotlinxCoroutines"] as String)
}
