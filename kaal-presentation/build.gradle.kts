import com.jfrog.bintray.gradle.BintrayExtension
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("com.jfrog.bintray")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Android.compileSdk)
    buildToolsVersion(Android.buildTools)

    defaultConfig {
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)

        versionCode = getGitCommits()
        versionName = "${project.version}"

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

    buildFeatures {
        dataBinding = true
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
    compileOnly(Dependencies.Android.recyclerView)
    compileOnly(Dependencies.Android.viewPager2)
    //implementation architectureComponents.lifecycleLiveDataCore

    // Koin
    implementation(Dependencies.Koin.android)
    implementation(Dependencies.Koin.scope)
    implementation(Dependencies.Koin.viewModel)

    implementation(Dependencies.timber)
    implementation(Dependencies.timberKtx)

    // Tests
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.kotlinTest)
}

val dokka by tasks.getting(DokkaTask::class) {
    outputFormat = "html"
    outputDirectory = "$buildDir/dokka/html"
}

val dokkaPages by tasks.creating(DokkaTask::class) {
    outputFormat = "gfm"
    outputDirectory = "$rootDir/docs/api"

    configuration {
        moduleName = "presentation"
    }
}

val androidSourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

val androidDokkaHtmlJar by tasks.creating(Jar::class) {
    archiveClassifier.set("kdoc-html")
    from(dokka.outputDirectory)
    dependsOn(dokka)
}

val productionPublicName = "production"

bintray {
    user = findPropertyOrNull("bintray.user")
    key = findPropertyOrNull("bintray.apikey")
    publish = true
    setPublications(productionPublicName)
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "maven"
        name = "cz.eman.kaal.presentation"
        userOrg = "emanprague"
        override = true
        websiteUrl = "https://www.emanprague.com/en/"
        githubRepo = "eManPrague/kaal"
        vcsUrl = "https://github.com/eManPrague/kaal"
        description = "Kotlin Android Architecture Library by eMan"
        setLabels(
            "kotlin",
            "android",
            "clean-architecture",
            "architecture",
            "architecture-components",
            "kaal"
        )
        setLicenses("MIT")
        desc = description
    })
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>(productionPublicName) {
                from(components["release"])
                artifact(androidSourcesJar)
                artifact(androidDokkaHtmlJar)

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
            maven(url = "http://dl.bintray.com/emanprague/maven")
        }
    }
}
