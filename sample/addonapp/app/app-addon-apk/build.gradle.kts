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
        lintConfig = rootProject.file("lint.xml")
    }

    packagingOptions {
        exclude("META-INF/kaal-domain.kotlin_module")
    }
}

dependencies {
    implementation(project(":addonlib:kaal-apk-addon"))
    implementation(project(":featurelib:kaal-feature-presentation"))
    implementation(project(":sample:addonapp:codebase:codebase-presentation"))

    // Features
    implementation(project(":sample:addonapp:app:feature:splashscreen:splashscreen-app"))
    implementation(project(":sample:addonapp:app:feature:login:login-app"))

    // Addons

    // AndroidX & Google
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.navigationFragment)
    implementation(Dependencies.AndroidX.navigationUi)

    // Kotlin
    implementation(Dependencies.Kotlin.stdlibJdk)

    // 3rd party
    implementation(Dependencies.Koin.android)
    implementation(Dependencies.Koin.viewModel)

    // Tests
    testImplementation(Dependencies.Test.junit)

}

