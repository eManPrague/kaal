import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("maven")
}

dependencies {
    api(project(":addonlib:kaal-domain-addon"))

    // Kotlin
    implementation(Dependencies.Kotlin.stdlibJdk)
    implementation(Dependencies.Kotlin.coroutinesCore)

    // Tests
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.kotlinTest)
    testImplementation(Dependencies.Test.mockkUnit)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.getByName<DokkaTask>("dokka") {
    outputFormat = "html"
    outputDirectory = "${rootProject.buildDir}/documentation"
}
