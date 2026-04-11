plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.diffplug.spotless") version "6.25.0"
    id("io.github.takahirom.roborazzi")
}

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

tasks.register("installLocalGitHook") {
    doLast {
        var currentDir: File? = rootProject.rootDir
        var gitDir: File? = null

        while (currentDir != null) {
            val checkGit = File(currentDir, ".git")
            if (checkGit.exists() && checkGit.isDirectory) {
                gitDir = checkGit
                break
            }
            currentDir = currentDir.parentFile
        }

        if (gitDir == null) {
            println("❌ ERRO: Não encontrei a pasta .git em nenhum lugar acima de: ${rootProject.rootDir}")
            return@doLast
        }

        val hooksDir = File(gitDir, "hooks")
        if (!hooksDir.exists()) hooksDir.mkdirs()

        val isNested = gitDir.parentFile != rootProject.rootDir
        val projectFolderName = rootProject.rootDir.name
        val cdCommand = if (isNested) "cd $projectFolderName || exit 1" else ""

        println("📍 .git encontrado em: ${gitDir.parent}")
        if (isNested) println("📂 Projeto está dentro da subpasta: $projectFolderName")

        val preCommitFile = File(hooksDir, "pre-commit")

        val scriptContent =
            """
            #!/bin/bash
            echo "🐶 PetJournal: Verificando e limpando o código com Spotless..."
            
            # Garante que o terminal vá para a pasta onde está o gradlew
            $cdCommand
            
            ./gradlew spotlessApply
            
            if [ $? -eq 0 ]; then
                git add .
                echo "✨ Código limpo e formatado com sucesso!"
            else
                echo "❌ Erro ao rodar o Spotless."
                exit 1
            fi
            """.trimIndent()

        preCommitFile.writeText(scriptContent)
        preCommitFile.setExecutable(true)
        println("✅ Git Hook instalado com sucesso em: ${preCommitFile.absolutePath}")
    }
}

tasks.getByPath("preBuild").dependsOn("installLocalGitHook")

android {
    namespace = "com.soujunior.petjournal"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.soujunior.petjournal"
        minSdk = 27
        targetSdk = 36
        versionCode = 6
        versionName = "1.0.6"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
    val composeUiVersion = "1.4.2"

    implementation(project(":domain"))
    implementation(project(":database"))
    implementation(project(":data"))
    implementation("androidx.compose.runtime:runtime-android:1.9.5")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("io.insert-koin:koin-core:3.4.1")
    implementation("io.insert-koin:koin-android:3.4.1")
//    implementation("io.insert-koin:koin-androidx-compose:3.1.2")
    implementation("io.insert-koin:koin-androidx-compose:3.4.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.github.Kaaveh:sdp-compose:1.1.0")
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
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.13.0")

    // MATERIAL 2
    implementation("androidx.compose.material:material:$composeUiVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeUiVersion")
    // MATERIAL 3 - MATERIAL YOU
    implementation("com.google.android.material:material:1.9.0")

    // MATERIAL 3 - STATUS BAR
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    implementation("androidx.navigation:navigation-compose:2.6.0-beta01")
    implementation("androidx.compose.material3:material3:1.3.0")

    // COMPOSE
    implementation("androidx.activity:activity-compose:1.7.1")
    implementation("androidx.compose.ui:ui:$composeUiVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")
    implementation("androidx.compose.ui:ui-util:$composeUiVersion")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    ksp("com.github.bumptech.glide:ksp:4.14.2")
    // kapt("com.github.bumptech.glide:compiler:4.12.0")

    // TESTE - COMPOSE
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeUiVersion")

    testImplementation("androidx.arch.core:core-testing:2.2.0")
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")

    androidTestImplementation("io.mockk:mockk-android:1.12.0")
    androidTestImplementation("io.mockk:mockk-agent:1.12.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // UI Tests na JVM (Robolectric + Screenshot Testing Roborazzi)
    testImplementation("org.robolectric:robolectric:4.10.3")
    testImplementation("androidx.compose.ui:ui-test-junit4:$composeUiVersion")
    testImplementation("io.github.takahirom.roborazzi:roborazzi:1.7.0-alpha-1")
    testImplementation("io.github.takahirom.roborazzi:roborazzi-compose:1.7.0-alpha-1")
    testImplementation("io.github.takahirom.roborazzi:roborazzi-junit-rule:1.7.0-alpha-1")
    testImplementation("androidx.navigation:navigation-testing:2.6.0-beta01")
}

roborazzi {
    outputDir.set(file("src/test/snapshots/images"))
}
