import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.dokka.gradle.DokkaTask
import com.jfrog.bintray.gradle.BintrayExtension
import org.gradle.kotlin.dsl.*

plugins {
    id("java-library")
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("maven")
    id("com.jfrog.bintray")
}

dependencies {
    //implementation(fileTree(dir: "libs", include: ["*.jar"]))

    // Kotlin
    implementation(Dependencies.Kotlin.kotlinStbLib)
    implementation(Dependencies.Kotlin.coroutinesCore)

    // Tests
    testImplementation(Dependencies.TestLibs.junit)
    testImplementation(Dependencies.TestLibs.kotlinTest)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val dokka by tasks.getting(DokkaTask::class) {
    moduleName = "kaal-domain"
    outputFormat = "html" // html, md, javadoc,
    outputDirectory = "$buildDir/dokka/html"
    sourceDirs = files("src/main/kotlin")
}

tasks.create<Jar>("sourcesJar") {
    from(files("src/main/kotlin"))
    archiveClassifier.set("sources")
}

tasks.create<Jar>("dokkaHtmlJar") {
    archiveClassifier.set("kdoc-html")
    from("$buildDir/dokka/html")
    dependsOn(dokka)
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
        name = "cz.eman.kaal.domain"
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
        create<MavenPublication>(productionPublicName) {
            artifactId = "kaal-domain"
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["dokkaHtmlJar"])
        }
    }

    repositories {
        maven(url = "http://dl.bintray.com/emanprague/maven")
    }
}

