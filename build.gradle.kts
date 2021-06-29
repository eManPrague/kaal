applyProperties(file("local.properties"))

buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.GradlePlugins.androidGradle)
        classpath(Dependencies.GradlePlugins.kotlin)
        classpath(Dependencies.GradlePlugins.dokka)
    }
}

allprojects {

    repositories {
        google()
        mavenCentral()
        maven(url = "https://nexus.eman.cz/repository/maven-public")
    }

    group = Android.groupId
}

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}
