plugins {
    id("com.android.library") // Plugin para módulo Android Library
    id("org.jetbrains.kotlin.android") // Plugin para suporte ao Kotlin
}

android {
    namespace = "com.soujunior.domain" // Definindo o namespace
    compileSdk = 34 // Definindo a versão da SDK de compilação

    defaultConfig {
        minSdk = 27 // Definindo a versão mínima da SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Configurando o testInstrumentationRunner
        consumerProguardFiles("consumer-rules.pro") // Adicionando arquivos de regras Proguard
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Desativando a minificação no build release
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            ) // Adicionando arquivos Proguard
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Configurando a compatibilidade do código fonte
        targetCompatibility = JavaVersion.VERSION_1_8 // Configurando a compatibilidade do código alvo
    }
    kotlinOptions {
        jvmTarget = "1.8" // Definindo a versão do JVM target
    }
}

dependencies {
    val dependencies = rootProject.ext["dependencies"] as Map<String, String> // Obtendo as dependências do arquivo ext
    implementation(dependencies["coroutineCore"]!!) // Implementação da dependência coroutineCore
    implementation(dependencies["coroutineAndroid"]!!) // Implementação da dependência coroutineAndroid
    implementation(dependencies["moshiKotlin"]!!) // Implementação da dependência moshiKotlin
    implementation(dependencies["retrofit2ConverterMoshi"]!!) // Implementação da dependência retrofit2ConverterMoshi

    testImplementation(rootProject.ext["testJunit"] as String) // Implementação da dependência de teste JUnit
    testImplementation(rootProject.ext["testMockk"] as String) // Implementação da dependência de teste MockK
    testImplementation(rootProject.ext["testAssertk"] as String) // Implementação da dependência de teste AssertK
    testImplementation(rootProject.ext["testKotlinxCoroutines"] as String) // Implementação da dependência de teste para coroutines
}