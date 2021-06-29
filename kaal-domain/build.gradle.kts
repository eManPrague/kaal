import org.jetbrains.dokka.gradle.DokkaTask
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
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

//val dokka by tasks.getting(DokkaTask::class) {
//    moduleName = "kaal-domain"
//    outputFormat = "html" // html, md, javadoc,
//    outputDirectory = "$buildDir/dokka/html"
//    sourceDirs = files("src/main/kotlin")
//}

tasks.create<Jar>("sourcesJar") {
    from(files("src/main/kotlin"))
    archiveClassifier.set("sources")
}

//tasks.create<Jar>("dokkaHtmlJar") {
//    archiveClassifier.set("kdoc-html")
//    from("$buildDir/dokka/html")
//    dependsOn(dokka)
//}

publishing {
    publications {
        create<MavenPublication>("production") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
//            artifact(tasks["dokkaHtmlJar"])

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
