buildscript {
    extra.apply{
        set("hilt_version", "2.48.1")
        set("ktlint_version", "1.0.1")
        set("compose_bom_version", "2023.10.01")
        set("compose_compiler_version", "1.5.4")
        set("kotlin_version", "1.9.20")
        set("ksp_version", "1.9.20-1.0.13")
    }
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
            url= uri("https://maven.google.com")
        }
        gradlePluginPortal()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.1.4")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra.get("kotlin_version")}")
        classpath("com.pinterest.ktlint:ktlint-cli:${project.extra.get("ktlint_version")}")
        classpath ("com.google.devtools.ksp:symbol-processing-gradle-plugin:${project.extra.get("ksp_version")}")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:${project.extra.get("hilt_version")}")
        classpath ("com.google.gms:google-services:4.4.0")

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
tasks {
    val ktlintFormat by registering {
        group = "formatting"
        description = "Format Kotlin code using ktlint"
    }

    val customBuild by creating {
        group = "build"
        description = "Custom build task"
        dependsOn(ktlintFormat)
        doLast {
            println("Custom build task completed.")
        }
    }
}
