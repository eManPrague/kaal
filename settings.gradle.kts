rootProject.buildFileName = "build.gradle.kts"

enableFeaturePreview("GRADLE_METADATA")

include(
    ":sample",
    ":kaal-infrastructure",
    ":kaal-presentation",
    ":kaal-domain",
    ":kaal-core"
)
