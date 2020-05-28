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
    implementation(project(":sample:addonapp:app:feature:login:login-domain"))

    // Kotlin
    implementation(Dependencies.Kotlin.kotlinStbLib)
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
