import org.gradle.api.JavaVersion


object Android {
    const val applicationId = "cz.eman.kaal.sample"
    const val groupId = "cz.eman.kaal"

    const val minSdk = 21
    const val targetSdk = 30
    const val compileSdk = 30
    const val buildTools = "30.0.2"

    const val versionCode = 1
    const val versionName = "1"

    const val testInstrumentRunner = "androidx.test.runner.AndroidJUnitRunner"
    val sourceCompatibilityJava = JavaVersion.VERSION_1_8
    val targetCompatibilityJava = JavaVersion.VERSION_1_8
}

object Dependencies {
    /* =============================  Gradle ============================== */
    object GradlePlugins {
        const val gradle = Versions.gradle

        const val androidGradle = "com.android.tools.build:gradle:${Versions.gradleBuildTools}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${Versions.dokka}"
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
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val lifecycleExtension = "androidx.lifecycle:lifecycle-extensions:${Versions.archLifecycle}"
        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.archLifecycle}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
        const val viewPager2 = "androidx.viewpager2:viewpager2:${Versions.viewPager2}"
        const val databinding = "androidx.databinding:databinding-runtime:${Versions.databinding}"
        const val material = "com.google.android.material:material:${Versions.material}"
        const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
    }

    /* =============================  TEST ================================ */
    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val kotlinTest = "io.kotest:kotest-runner-junit5:${Versions.kotlinTest}"
        const val kotlinCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    /* =============================  OTHERS ================================ */
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val timberKtx = "cz.eman.logger:timber-ktx:${Versions.timberKtx}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"

}
