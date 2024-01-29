import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("maven-publish")
}

dependencies {
    // Kotlin
    api(Dependencies.Kotlin.kotlinStbLib)
    implementation(Dependencies.Kotlin.coroutinesCore)

    // Tests
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.kotlinTest)
    testImplementation(Dependencies.Test.kotlinCoroutinesTest)
}

val jvmTarget = Android.targetCompatibilityJava
java {
    sourceCompatibility = jvmTarget
    targetCompatibility = jvmTarget
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = jvmTarget.toString()
}

tasks.dokkaHtml.configure {
    moduleName.set("kaal-domain")
    outputDirectory.set(buildDir.resolve("dokka/html"))
    dokkaSourceSets {
        configureEach {
            sourceRoot(file("src/main/kotlin"))
        }
    }
}

tasks.create<Jar>("sourcesJar") {
    from(files("src/main/kotlin"))
    archiveClassifier.set("sources")
}

tasks.create<Jar>("dokkaHtmlJar") {
    archiveClassifier.set("kdoc-html")
    from("$buildDir/dokka/html")
    dependsOn(tasks.dokkaHtml)
}

publishing {
    publications {
        create<MavenPublication>("production") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["dokkaHtmlJar"])

            pom {
                name.set("Kotlin Android Architecture Library")
                description.set("Domain module of Kaal library")
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
