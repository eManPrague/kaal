plugins {
    `kotlin-dsl`
}
repositories {
    jcenter()
}

val jvmTarget = JavaVersion.VERSION_17
java {
    sourceCompatibility = jvmTarget
    targetCompatibility = jvmTarget
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = jvmTarget.toString()
}
