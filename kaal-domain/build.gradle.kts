import com.jfrog.bintray.gradle.BintrayExtension
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    kotlin("multiplatform")
    //kotlin("jvm")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("maven")
    id("com.jfrog.bintray")
}

kotlin {
    jvm()

    //Revert to just ios() when gradle plugin can properly resolve it
    val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos") ?: false
    if (onPhone) {
        iosArm64("ios")
    } else {
        iosX64("ios")
    }
    targets.getByName<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>("ios").compilations["main"].kotlinOptions.freeCompilerArgs +=
        listOf("-Xobjc-generics", "-Xg0")

    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
    }

    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(Dependencies.Kotlin.stdlibCommon)
                implementation(Dependencies.Kotlin.coroutinesCommon)
                //implementation(Dependencies.Kotlin.serializationRuntimeCommon)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        /* val androidMain by getting {
             dependsOn(commonMain)
             //dependsOn(jvmMain)

             dependencies {

             }
         }*/

        // JvmMain - Uncomment this and add jvmMain folder in case that you need it
        val jvmMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(Dependencies.Kotlin.stdlibJdk)
                implementation(Dependencies.Kotlin.coroutinesCore)

                //api(Dependencies.Kotlin.serializationRuntime)
            }
        }
        val jvmTest by getting {
            dependsOn(commonTest)
            dependencies {
                implementation(Dependencies.Test.kotlinTest)
                implementation(Dependencies.Test.kotlinTestJUnit)
            }
        }

        val nativeMain = create("nativeMain").apply {
            dependsOn(commonMain)
        }

        /* val iosMain = if (ideaActive) {
             getByName("iosMain")
         } else {
             create("iosMain")
         }*/

        /*val iosMain = create("iosMain")
        iosMain.apply {
            dependsOn(commonMain)

            dependencies {
                implementation(Dependencies.Kotlin.coroutinesCoreNative)
                //implementation(Dependencies.Kotlin.serializationRuntimeNative)
            }
        }*/

        /*val iosTest by getting {
            dependsOn(commonTest)
            dependencies {
                implementation(Dependencies.Kotlin.Ktor.mockNative)
            }
        }*/

        /* val iosArm32Main by getting
         val iosArm64Main by getting
         val iosX64Main by getting

         configure(listOf(iosArm32Main, iosArm64Main, iosX64Main)) {
             //dependsOn(iosMain)
             dependsOn(nativeMain)
         }*/

        /*all {
            languageSettings {

            }
        }*/

    }

    /*val frameworkName = "kaalDomainFramework"

    configure(listOf(iosArm32, iosArm64, iosX64)) {
        compilations {
            val main by getting {
                extraOpts("-Xobjc-generics")
            }
        }

        binaries.framework {
            export(Dependencies.Kotlin.coroutinesCommon)
            baseName = frameworkName
        }
    }

    tasks.register<org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask>("debugFatFramework") {
        baseName = frameworkName
        group = "Universal framework"
        description = "Builds a universal (fat) debug framework"

        from(
            iosArm64.binaries.getFramework("DEBUG"),
            iosArm32.binaries.getFramework("DEBUG"),
            iosX64.binaries.getFramework("DEBUG")
        )
    }

    tasks.register<org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask>("releaseFatFramework") {
        baseName = frameworkName
        group = "Universal framework"
        description = "Builds a universal (release) debug framework"

        from(
            iosArm64.binaries.getFramework("RELEASE"),
            iosArm32.binaries.getFramework("RELEASE"),
            iosX64.binaries.getFramework("RELEASE")
        )
    }*/
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

/*tasks.withType(DokkaTask::class.java) { task ->
    task.multiplatform.apply {
        kotlin.targets.forEach { create(it.name) }
    }
}*/

val dokka by tasks.getting(DokkaTask::class) {
    outputFormat = "html" // html, md, javadoc,
    outputDirectory = "$buildDir/dokka/html"
//    multiplatform {
//      commonMain {
//          moduleName = "kaal-domain"
//          //*//*sourceRoots = listOf(org.jetbrains.dokka.gradle.SourceRoot("src/main/kotlin"))
//          platform = "JVM"
//      }
//    }
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
        override = false
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
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["dokkaHtmlJar"])
        }
    }

    repositories {
        maven(url = "http://dl.bintray.com/emanprague/maven")
    }
}

