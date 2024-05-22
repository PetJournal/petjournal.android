plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.soujunior.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 27
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":database"))

    val dependencies = rootProject.ext["dependencies"] as Map<String, String>
    implementation(dependencies["securityCrypto"]!!)
    implementation(dependencies["retrofit2Retrofit"]!!)
    implementation(dependencies["moshiKotlin"]!!)
    implementation(dependencies["retrofit2ConverterMoshi"]!!)
    implementation(dependencies["coroutineCore"]!!)
    implementation(dependencies["coroutineAndroid"]!!)
    implementation(dependencies["coreKtx"]!!)
    implementation(dependencies["preference"]!!)

    testImplementation("junit:junit")
    testImplementation("io.mockk:mockk")
    testImplementation("com.willowtreeapps.assertk:assertk")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
}