import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.soujunior.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 27
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("proguard-rules.pro")

        val envFile = project.rootProject.file(".env")
        val env = Properties()
        if (envFile.exists()) {
            env.load(FileInputStream(envFile))
        }

        buildConfigField("String", "DISCORD_WEBHOOK_URL", "\"${env.getProperty("DISCORD_WEBHOOK_URL") ?: ""}\"")
        buildConfigField("String", "GIST_RAW_URL", "\"${env.getProperty("GIST_RAW_URL") ?: ""}\"")
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
    implementation(project(":domain"))
    implementation(project(":database"))
    implementation("androidx.datastore:datastore-core:1.2.1")

    val dependencies = rootProject.ext["dependencies"] as Map<String, String>
    implementation(dependencies["securityCrypto"]!!)
    implementation(dependencies["retrofit2Retrofit"]!!)
    implementation(dependencies["moshiKotlin"]!!)
    implementation(dependencies["retrofit2ConverterMoshi"]!!)
    implementation(dependencies["coroutineCore"]!!)
    implementation(dependencies["coroutineAndroid"]!!)
    implementation(dependencies["coreKtx"]!!)
    implementation(dependencies["preference"]!!)
    implementation(dependencies["dataStorePreferences"]!!)

    testImplementation(rootProject.ext["testJunit"] as String)
    testImplementation(rootProject.ext["testMockk"] as String)
    testImplementation(rootProject.ext["testAssertk"] as String)
    testImplementation(rootProject.ext["testKotlinxCoroutines"] as String)
}
