plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
}

android {

    //        applicationId = application.id
//        versionName = version
//        versionCode = gitCommitsCount

    compileSdkVersion(Android.compileSdk)
    buildToolsVersion(Android.buildTools)

    defaultConfig {
        applicationId = "${Android.applicationId}.addon"

        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)

        versionCode = getGitCommits()
        versionName = Android.versionName

        testInstrumentationRunner = Android.testInstrumentRunner
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }

        getByName("release") {
            isMinifyEnabled = false
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
    }

    compileOptions {
        sourceCompatibility = Android.sourceCompatibilityJava
        targetCompatibility = Android.targetCompatibilityJava
    }

    lintOptions {
        setLintConfig(rootProject.file("lint.xml"))
    }

    packagingOptions {
        exclude("META-INF/kaal-domain.kotlin_module")
    }
}

dependencies {
    implementation(project(":kaal-presentation"))

    // Support Libraries
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.constraintLayout)

    // Kotlin
    implementation(Dependencies.Kotlin.kotlinStbLib)

    // Tests
    testImplementation(Dependencies.Test.junit)

}

