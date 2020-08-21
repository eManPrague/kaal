import com.jfrog.bintray.gradle.BintrayExtension
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.dokka")
    id("base")
    id("maven-publish")
    // id("com.jfrog.bintray")
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

    lintOptions {
        setLintConfig(rootProject.file("lint.xml"))
    }
}

dependencies {

    // Kotlin
    implementation(Dependencies.Kotlin.stdlibJdk)

    // Tests
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.kotlinTest)
}

val dokka by tasks.getting(DokkaTask::class) {
    outputFormat = "html" // html, md, javadoc,
    outputDirectory = "$buildDir/dokka/html"
    configuration {
        moduleName = "kaal-core"
    }
}
val androidDokkaHtmlJar by tasks.creating(Jar::class) {
    archiveClassifier.set("kdoc-html")
    from("$buildDir/dokka/html")
    dependsOn(dokka)
}

val androidSourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

System.setProperty("org.gradle.internal.publish.checksums.insecure", "true")

val productionPublicName = "production"

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>(productionPublicName) {
                from(components["release"])
                artifact(androidSourcesJar)
                artifact(androidDokkaHtmlJar)
            }
        }

        val user = "emanprague"
        val repo = "maven"
        val name = "cz.eman.kaal.core"

        repositories {
            maven {
                authentication {
                    create<BasicAuthentication>("basic")
                }
                credentials {
                    username = findPropertyOrNull("bintray.user")
                    password = findPropertyOrNull("bintray.apikey")
                }

                url = uri("https://api.bintray.com/maven/$user/$repo/$name/;publish=0;override=1")
            }
        }
    }
}

/*val productionPublicName = "production"

bintray {
    user = findPropertyOrNull("bintray.user")
    key = findPropertyOrNull("bintray.apikey")
    publish = true
    setPublications(productionPublicName)
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "maven"
        name = "cz.eman.kaal.core"
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
            }
        }

        repositories {
            maven(url = "http://dl.bintray.com/emanprague/maven")
        }
    }
}*/
