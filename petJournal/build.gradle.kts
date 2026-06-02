buildscript {
    apply(from = "dependencies.gradle")
    extra["compose_ui_version"] = "1.4.2"
    val agp_version by extra("8.13.1")

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$agp_version")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.21")
        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.9.21-1.0.16")
        classpath("io.github.takahirom.roborazzi:roborazzi-gradle-plugin:1.7.0-alpha-1")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
