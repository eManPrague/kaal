import org.gradle.api.JavaVersion
import kotlin.String


/* =============================  BUILD-PLUGINS ======================= */

private object Versions {

    val supportLib = "1.0.0"
    val archLifecycle = "2.1.0-alpha03"
    val navigationComponent = "2.1.0-alpha02"
    val constraintLayout = "1.1.2"

    val kotlin = "1.3.30"
    val coroutinesCore = "1.1.1"
    val coroutinesAndroid = "1.1.1"
    val dokka = "0.9.17"

    val gradle = "5.2.1"
    //val gradleBuildTools = "3.5.0-alpha07"
    val gradleBuildTools = "3.4.0"

    val mavenPublish = "3.6.2"
    val mavenGradleGithub = "1.5"
    val bintrayGradle = "1.8.4"

    val timber = "4.7.1"
    val timberKtx = "0.1.0"
    val mirrorLink = "1.1"
    val koin = "2.0.0-beta-1"
    val espresso = "3.0.1"
    val junit = "4.12"
    val kotlinTest = "3.3.0"

}


/* =============================  BUILD-PLUGINS ======================= */

object GradlePlugins {
    val encoding = "UTF-8"
    val gradle = Versions.gradle

    val androidGradle = "com.android.tools.build:gradle:${Versions.gradleBuildTools}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${Versions.dokka}"
    val mavenPublish = "digital.wup:android-maven-publish:${Versions.mavenPublish}"
    val androidMavenGradle = "com.github.dcendents:android-maven-gradle-plugin:${Versions.mavenGradleGithub}"
    val bintrayGradle = "com.jfrog.bintray.gradle:gradle-bintray-plugin:${Versions.bintrayGradle}"
}

/* =============================  ANDROID ============================= */

object Android {
    val applicationId = "cz.eman.kaal.sample"
    val groupId = "cz.eman.kaal"

    val minSdk = 21
    val targetSdk = 28
    val compileSdk = 28

    val versionCode = 1
    val versionName = "1"

    val testInstrumentRunner = "android.support.test.runner.AndroidJUnitRunner"
    val sourceCompatibilityJava = JavaVersion.VERSION_1_8
    val targetCompatibilityJava = JavaVersion.VERSION_1_8
}

object Dependencies {
    /* =============================  KOTLIN ============================== */

    object Kotlin {
        val kotlinStbLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
        val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
    }

    /* =============================  LIBS ================================ */

    object Libs {
        val appCompat = "androidx.appcompat:appcompat:${Versions.supportLib}"
        val supportFragment = "androidx.fragment:fragment:${Versions.supportLib}"
        val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        val lifecycleExtension = "androidx.lifecycle:lifecycle-extensions:${Versions.archLifecycle}"
        val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.archLifecycle}"
        val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.navigationComponent}"
        val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationComponent}"

        val koinScope =
            "org.koin:koin-androidx-scope:${Versions.koin}" // Koin Android Scope feature
        val koinAndroid = "org.koin:koin-android:${Versions.koin}"
        val koinViewModel =
            "org.koin:koin-androidx-viewmodel:${Versions.koin}" // Koin Android ViewModel feature

        val timber = "com.jakewharton.timber:timber:${Versions.timber}"
        val timberKtx = "cz.eman.logger:timber-ktx:${Versions.timberKtx}"
    }

    /* =============================  TEST-LIBS =========================== */

    object TestLibs {
        val junit = "junit:junit:${Versions.junit}"
        val espressoCore = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
        val kotlinTest = "io.kotlintest:kotlintest-runner-junit5:${Versions.kotlinTest}"
    }
}