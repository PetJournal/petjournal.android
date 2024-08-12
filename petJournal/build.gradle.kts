buildscript {
    apply(from = "dependencies.gradle")
    extra["compose_ui_version"] = "1.4.2"
    val agp_version by extra("8.2.2")

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$agp_version")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.21")
        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.9.21-1.0.16")
//      classpath("com.google.dagger:hilt-android-gradle-plugin:2.46.1")
    }
//    plugins{
        //id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
//    }
}

//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//    }
//}


//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//    }
//}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
