buildscript {

    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        // https://github.com/gradle/kotlin-dsl/issues/1291
        classpath(Dependencies.GradlePlugins.androidGradle)

        // Kotlin Grade plugin
        classpath(Dependencies.GradlePlugins.kotlin)

        // Build Tool to generate Kotlin KDoc documentation
        classpath(Dependencies.GradlePlugins.dokka)

        classpath(Dependencies.GradlePlugins.safeArgs)

        classpath(Dependencies.GradlePlugins.bintrayGradle)
    }
}

allprojects {

    repositories {
        google()
        jcenter()
    }

    group = Android.groupId
}

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}
