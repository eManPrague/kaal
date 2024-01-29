plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("kotlin-kapt")
}

android {
    namespace = "cz.eman.kaal.presentation"

    compileSdk = Android.compileSdk

    defaultConfig {
        minSdk = Android.minSdk

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

    buildFeatures {
        dataBinding = true
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    api(project(":kaal-domain"))

    // Kotlin
    api(Dependencies.Kotlin.kotlinStbLib)
    implementation(Dependencies.Kotlin.coroutinesCore)
    implementation(Dependencies.Kotlin.coroutinesAndroid)

    api(Dependencies.Android.appCompat)
    api(Dependencies.Android.lifecycleExtension)
    api(Dependencies.Android.viewModelKtx)
    api(Dependencies.Android.coreKtx)
    compileOnly(Dependencies.Android.material)
    compileOnly(Dependencies.Android.recyclerView)
    compileOnly(Dependencies.Android.viewPager2)
    compileOnly(Dependencies.Android.paging)

    // Timber
    implementation(Dependencies.timber)
    implementation(Dependencies.timberKtx)

    // Tests
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.kotlinTest)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("production") {
                from(components["release"])

                pom {
                    name.set("Kotlin Android Architecture Library")
                    description.set("Presentation module of Kaal library")
                    url.set("https://emanprague.github.io/kaal")

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }

                    developers {
                        developer {
                            name.set("eMan a.s.")
                            email.set("info@eman.cz")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/eManPrague/kaal.git")
                        developerConnection.set("scm:git:ssh://git@github.com/eManPrague/kaal.git")
                        url.set("https://github.com/eManPrague/kaal")
                    }

                    issueManagement {
                        system.set("GitHub Issues")
                        url.set("https://github.com/eManPrague/kaal/issues")
                    }
                }
            }
        }

        repositories {
            maven(url = "https://nexus.eman.cz/repository/maven-public") {
                name = "Nexus"

                credentials {
                    username = findPropertyOrNull("nexus.username")
                    password = findPropertyOrNull("nexus.password")
                }
            }
        }
    }
}
