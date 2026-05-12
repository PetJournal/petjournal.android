import java.util.Properties
import java.io.FileInputStream

// [CI-INFO] Estas variáveis recebem os valores passados pelo GitHub Actions via flag -P.
// Se rodar localmente sem flags, ele assume o padrão (versionCode 6 / versionName 1.0.6).
val appVersionCode = project.findProperty("versionCode")?.toString()?.toInt() ?: 6
val appVersionName = project.findProperty("versionName")?.toString() ?: "1.0.6"

// [CI-INFO] O Gradle procura este arquivo na raiz da pasta petJournal.
// O CI cria este arquivo dinamicamente para não deixar senhas expostas no Git.
val keystorePropertiesFile = rootProject.file("key.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.diffplug.spotless") version "6.25.0"
    id("io.github.takahirom.roborazzi")
}

// [CI-INFO] Task do Spotless garante que o código no CI esteja sempre formatado.
spotless {
    kotlin {
        target("**/*.kt")
        ktlint()
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}

android {
    namespace = "com.soujunior.petjournal"
    compileSdk = 35

    // [CI-INFO] Configuração de Assinatura.
    // Os dados (Alias, Passwords) são injetados aqui vindos do arquivo key.properties criado pelo CI.
    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String?
            keyPassword = keystoreProperties["keyPassword"] as String?
            // O caminho abaixo é relativo à pasta do projeto (petJournal/app/keystore.jks)
            storeFile = keystoreProperties["storeFile"]?.let { file(it) }
            storePassword = keystoreProperties["storePassword"] as String?
        }
    }

    defaultConfig {
        applicationId = "com.soujunior.petjournal"
        minSdk = 27
        targetSdk = 35
        
        // [CI-INFO] Versões injetadas dinamicamente pelo GitHub Actions.
        versionCode = appVersionCode
        versionName = appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            // [CI-INFO] Habilita R8 (Minificação). Essencial para reduzir o tamanho do .aab no deploy.
            isMinifyEnabled = true
            
            // [CI-INFO] VINCULA A ASSINATURA. Sem esta linha, o upload na Google Play falha.
            signingConfig = signingConfigs.getByName("release")
            
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
        buildConfig = true
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
    val composeUiVersion = "1.4.2"
    val roomVersion = "2.3.0"

    implementation(project(":domain"))
    implementation(project(":database"))
    implementation(project(":data"))

    implementation("androidx.compose.runtime:runtime-android:1.9.5")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("io.insert-koin:koin-core:3.4.1")
    implementation("io.insert-koin:koin-android:3.4.1")
    implementation("io.insert-koin:koin-androidx-compose:3.4.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.github.Kaaveh:sdp-compose:1.1.0")

    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.13.0")
    implementation("androidx.compose.material:material:$composeUiVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeUiVersion")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    implementation("androidx.navigation:navigation-compose:2.6.0-beta01")
    implementation("androidx.compose.material3:material3:1.3.0")

    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.ui:ui:$composeUiVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")
    implementation("androidx.compose.ui:ui-util:$composeUiVersion")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    ksp("com.github.bumptech.glide:ksp:4.14.2")

    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    testImplementation("org.robolectric:robolectric:4.10.3")
    testImplementation("io.github.takahirom.roborazzi:roborazzi:1.7.0-alpha-1")
    testImplementation("io.github.takahirom.roborazzi:roborazzi-compose:1.7.0-alpha-1")
    testImplementation("io.github.takahirom.roborazzi:roborazzi-junit-rule:1.7.0-alpha-1")
    
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeUiVersion")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeUiVersion")
}

roborazzi {
    outputDir.set(file("src/test/snapshots/images"))
}