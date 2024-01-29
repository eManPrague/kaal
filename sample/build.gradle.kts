plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "cz.eman.kaal.sample"

    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = Android.applicationId

        minSdk = Android.minSdk

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

    kotlinOptions {
        jvmTarget = Android.targetCompatibilityJava.toString()
    }

    lint {
        lintConfig = rootProject.file("lint.xml")
    }

    packaging {
        resources {
            excludes += listOf(
                "META-INF/kaal-domain.kotlin_module",
            )
        }
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
