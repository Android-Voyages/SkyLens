plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.observer.weatherappcompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.observer.weatherappcompose"
        minSdk = 26
        targetSdk = 33
        versionCode = 4
        versionName = "1.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        debug {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "${rootProject.extra.get("compose_compiler_version")}"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "kotlin/internal/internal.kotlin_builtins"
            excludes += ("kotlin/reflect/reflect.kotlin_builtins")
            excludes += ("kotlin/collections/collections.kotlin_builtins")
            excludes += ("kotlin/annotation/annotation.kotlin_builtins")
            excludes += ("kotlin/kotlin.kotlin_builtins")
            excludes += ("kotlin/ranges/ranges.kotlin_builtins")
            excludes += ("kotlin/coroutines/coroutines.kotlin_builtins")
            excludes += ("META-INF/INDEX.LIST")
        }
    }
}

dependencies {
    //android sdk
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    //compose
    implementation(platform("androidx.compose:compose-bom:${rootProject.extra.get("compose_bom_version")}"))
    implementation("androidx.activity:activity-compose")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material:1.5.4")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")

    //Permision
    implementation("com.google.accompanist:accompanist-permissions:0.33.2-alpha")

    //Hilt ksp
    implementation("com.google.dagger:hilt-android:${rootProject.extra.get("hilt_version")}")
    ksp("com.google.dagger:hilt-android-compiler:${rootProject.extra.get("hilt_version")}")

    //Google play
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("com.google.firebase:firebase-analytics")

    //Volley
    implementation("com.android.volley:volley:1.2.1")

    //ktlint
    implementation("com.pinterest.ktlint:ktlint-cli:${rootProject.extra.get("ktlint_version")}")

    //coil for photo
    implementation("io.coil-kt:coil-compose:2.0.0-rc01")

    //Pager
    implementation("com.google.accompanist:accompanist-pager:0.19.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.19.0")

    //Android test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Compose test
    androidTestImplementation(platform("androidx.compose:compose-bom:${rootProject.extra.get("compose_bom_version")}"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
