import org.gradle.api.JavaVersion


object Android {
    const val applicationId = "cz.eman.kaal.sample"
    const val groupId = "cz.eman.kaal"

    const val minSdk = 21
    const val targetSdk = 29
    const val compileSdk = 29
    const val buildTools = "29.0.3"

    const val versionCode = 1
    const val versionName = "1"

    const val testInstrumentRunner = "androidx.test.runner.AndroidJUnitRunner"
    val sourceCompatibilityJava = JavaVersion.VERSION_1_8
    val targetCompatibilityJava = JavaVersion.VERSION_1_8
}

object Dependencies {
    /* =============================  Gradle ============================== */
    object GradlePlugins {
        const val encoding = "UTF-8"
        const val gradle = Versions.gradle

        const val androidGradle = "com.android.tools.build:gradle:${Versions.gradleBuildTools}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${Versions.dokka}"
        const val bintrayGradle = "com.jfrog.bintray.gradle:gradle-bintray-plugin:${Versions.bintrayGradle}"
    }

    /* =============================  KOTLIN ============================== */
    object Kotlin {
        const val kotlinStbLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    /* =============================  ANDROID ================================ */
    object Android {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val supportFragment = "androidx.fragment:fragment:${Versions.fragment}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val lifecycleExtension = "androidx.lifecycle:lifecycle-extensions:${Versions.archLifecycle}"
        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.archLifecycle}"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationComponent}"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationComponent}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
        const val viewPager2 = "androidx.viewpager2:viewpager2:${Versions.viewPager2}"
        const val databinding = "androidx.databinding:databinding-runtime:${Versions.databinding}"
    }

    /* =============================  KOIN ================================ */
    object Koin {
        const val scope = "org.koin:koin-androidx-scope:${Versions.koin}" // Koin Android Scope feature
        const val android = "org.koin:koin-android:${Versions.koin}"
        const val viewModel =
            "org.koin:koin-androidx-viewmodel:${Versions.koin}" // Koin Android ViewModel feature
    }

    /* =============================  TEST ================================ */
    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val kotlinTest = "io.kotlintest:kotlintest-runner-junit5:${Versions.kotlinTest}"
    }

    /* =============================  OTHERS ================================ */
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val timberKtx = "cz.eman.logger:timber-ktx:${Versions.timberKtx}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"

}
