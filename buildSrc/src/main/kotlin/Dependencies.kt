import org.gradle.api.JavaVersion


/* =============================  BUILD-PLUGINS ======================= */

private object Versions {
    const val supportLib = "1.0.0"
    const val archLifecycle = "2.1.0-alpha03"
    const val navigationComponent = "2.1.0-alpha02"
    const val constraintLayout = "1.1.2"

    const val kotlin = "1.3.40"
    const val coroutinesCore = "1.1.1"
    const val coroutinesAndroid = "1.1.1"
    const val dokka = "0.9.17"

    const val gradle = "5.2.1"
    const val gradleBuildTools = "3.4.0"

    const val mavenPublish = "3.6.2"
    const val mavenGradleGithub = "1.5"
    const val bintrayGradle = "1.8.4"

    const val timber = "4.7.1"
    const val timberKtx = "0.1.0"
    const val koin = "2.0.0-beta-1"
    const val espresso = "3.0.1"
    const val junit = "4.12"
    const val kotlinTest = "3.3.0"
}


/* =============================  BUILD-PLUGINS ======================= */

object GradlePlugins {
    const val encoding = "UTF-8"
    const val gradle = Versions.gradle

    const val androidGradle = "com.android.tools.build:gradle:${Versions.gradleBuildTools}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${Versions.dokka}"
    const val mavenPublish = "digital.wup:android-maven-publish:${Versions.mavenPublish}"
    const val androidMavenGradle = "com.github.dcendents:android-maven-gradle-plugin:${Versions.mavenGradleGithub}"
    const val bintrayGradle = "com.jfrog.bintray.gradle:gradle-bintray-plugin:${Versions.bintrayGradle}"
}

/* =============================  ANDROID ============================= */

object Android {
    const val applicationId = "cz.eman.kaal.sample"
    const val groupId = "cz.eman.kaal"

    const val minSdk = 21
    const val targetSdk = 28
    const val compileSdk = 28

    const val versionCode = 1
    const val versionName = "1"

    const val testInstrumentRunner = "android.support.test.runner.AndroidJUnitRunner"
    val sourceCompatibilityJava = JavaVersion.VERSION_1_8
    val targetCompatibilityJava = JavaVersion.VERSION_1_8
}

object Dependencies {
    /* =============================  KOTLIN ============================== */

    object Kotlin {
        const val kotlinStbLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
    }

    /* =============================  LIBS ================================ */

    object Libs {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.supportLib}"
        const val supportFragment = "androidx.fragment:fragment:${Versions.supportLib}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val lifecycleExtension = "androidx.lifecycle:lifecycle-extensions:${Versions.archLifecycle}"
        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.archLifecycle}"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationComponent}"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationComponent}"

        const val koinScope = "org.koin:koin-androidx-scope:${Versions.koin}" // Koin Android Scope feature
        const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
        const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}" // Koin Android ViewModel feature

        const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
        const val timberKtx = "cz.eman.logger:timber-ktx:${Versions.timberKtx}"
    }

    /* =============================  TEST-LIBS =========================== */

    object TestLibs {
        const val junit = "junit:junit:${Versions.junit}"
        const val espressoCore = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
        const val kotlinTest = "io.kotlintest:kotlintest-runner-junit5:${Versions.kotlinTest}"
    }
}
