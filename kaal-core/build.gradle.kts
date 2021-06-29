import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.dokka")
    id("maven-publish")
}

android {
    compileSdkVersion(Android.compileSdk)
    buildToolsVersion(Android.buildTools)

    defaultConfig {
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)

        versionCode = Android.versionCode
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

//    lintOptions {
//        setLintConfig(rootProject.file("lint.xml"))
//    }
}

dependencies {
    // Kotlin
    api(Dependencies.Kotlin.kotlinStbLib)

    // Tests
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.kotlinTest)
}

//tasks.withType<DokkaTask>().configureEach {
//    dokkaSourceSets {
//        named("main") {
//            moduleName.set("kaal-core")
//            includes.from("Module.md")
//            sourceLink {
//                localDirectory.set(file("src/main/kotlin"))
//                remoteUrl.set(URL("https://github.com/Kotlin/kotlin-examples/tree/master/" +
//                    "gradle/dokka/dokka-gradle-example/src/main/kotlin"
//                ))
//                remoteLineSuffix.set("#L")
//            }
//        }
//    }
//}

//val dokka by tasks.getting(DokkaTask::class) {
//    moduleName = "kaal-core"
//    outputFormat = "html" // html, md, javadoc,
//    outputDirectory = "$buildDir/dokka/html"
//    sourceDirs = files("src/main/kotlin")
//}

val androidSourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

//val androidDokkaHtmlJar by tasks.creating(Jar::class) {
//    archiveClassifier.set("kdoc-html")
//    from("$buildDir/dokka/html")
//    dependsOn(dokka)
//}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("production") {
                from(components["release"])
                artifact(androidSourcesJar)
//                artifact(androidDokkaHtmlJar)

                pom {
                    name.set("Kotlin Android Architecture Library")
                    description.set("Android core module of Kaal library")
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
