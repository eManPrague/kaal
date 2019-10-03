import com.jfrog.bintray.gradle.BintrayExtension
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.dokka")
    id("digital.wup.android-maven-publish")
    id("com.github.dcendents.android-maven")
    id("com.jfrog.bintray")
}

android {
    compileSdkVersion(Android.compileSdk)

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
        setLintConfig(File("lint.xml"))
    }
}

dependencies {

    // Kotlin
    implementation(Dependencies.Kotlin.kotlinStbLib)
    implementation(Dependencies.Kotlin.coroutinesCore)
    implementation(Dependencies.Kotlin.coroutinesAndroid)

    implementation(Dependencies.Libs.lifecycleExtension)

    // Tests
    testImplementation(Dependencies.TestLibs.junit)
    testImplementation(Dependencies.TestLibs.kotlinTest)
}


val dokka by tasks.getting(DokkaTask::class) {
    moduleName = "kaal-infrastructure"
    outputFormat = "html" // html, md, javadoc,
    outputDirectory = "$buildDir/dokka/html"
    sourceDirs = files("src/main/kotlin")
}

tasks {

    val androidSourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(android.sourceSets["main"].java.srcDirs)
    }

    val androidDokkaHtmlJar by creating(Jar::class) {
        archiveClassifier.set("kdoc-html")
        from("$buildDir/dokka/html")
        dependsOn(dokka)
    }

    artifacts {
        add("archives", androidSourcesJar)
        add("archives", androidDokkaHtmlJar)
    }
}

group = Android.groupId
version = "${project.version}"

val productionPublicName = "production"

bintray {
    user = findPropertyOrNull("bintray.user")
    key = findPropertyOrNull("bintray.apikey")
    publish = true
    setPublications(productionPublicName)
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "maven"
        name = "cz.eman.kaal.infrastructure"
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

publishing {
    publications {
        register(productionPublicName, MavenPublication::class) {
            from(components["android"])
            groupId = Android.groupId
            artifactId = "kaal-infrastructure"
            version = "${project.version}"
        }
    }

    repositories {
        maven(url = "http://dl.bintray.com/emanprague/maven")
    }
}
