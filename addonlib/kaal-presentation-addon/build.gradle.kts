import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-kapt")
    id("org.jetbrains.dokka")
}

android {
    compileSdkVersion(Android.compileSdk)

    defaultConfig {
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)

        versionCode = getGitCommits(projectDir.path)
        versionName = "${project.version}"

        testInstrumentationRunner = Android.testInstrumentRunner

        proguardFile("proguard-rules.pro")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }

        getByName("release") {
            isMinifyEnabled = true

            proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }

    compileOptions {
        encoding = Android.encoding
        sourceCompatibility = Android.sourceCompatibilityJava
        targetCompatibility = Android.targetCompatibilityJava
    }

    kotlinOptions {
        this as KotlinJvmOptions
        jvmTarget = "1.8"
    }

    lintOptions {
        lintConfig = rootProject.file("config/lint/lint.xml")
        htmlOutput = File(buildDir, "reports/lint/report-lint.html")
        xmlOutput = File(buildDir, "reports/lint/report-lint.xml")
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    api(project(":kaal-presentation"))
    api(project(":addonlib:kaal-domain-addon"))
    implementation(project(":kaal-core"))

    // Kotlin
    implementation(Dependencies.Kotlin.stdlibJdk)
    implementation(Dependencies.Kotlin.coroutinesCore)
    implementation(Dependencies.Kotlin.coroutinesAndroid)

    // Google Android Libraries
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.navigationFragment)
    implementation(Dependencies.AndroidX.navigationUi)
    kapt(Dependencies.AndroidX.bindingCompiler)
    implementation(Dependencies.Google.materialDesign)

    // eMan
    implementation(Dependencies.Tools.timberKtx)

    // Third Party - others
    implementation(Dependencies.Koin.android)
    implementation(Dependencies.Koin.viewModel)
    implementation(Dependencies.Tools.timber)
    implementation(Dependencies.Tools.timberKtx)
    api(Dependencies.UI.MaterialDialogs.core)

    // Test
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.kotlinTest)
    testImplementation(Dependencies.Test.mockkUnit)
    testImplementation(Dependencies.Test.mockkInstrument)
    // testImplementation(Dependencies.Test.testRunner)
    // testImplementation(Dependencies.Test.archCoreTest)
}

tasks.getByName<DokkaTask>("dokka") {
    outputFormat = "html"
    outputDirectory = "${rootProject.buildDir}/documentation"
}
