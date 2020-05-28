rootProject.buildFileName = "build.gradle.kts"

include(
    ":kaal-infrastructure",
    ":kaal-presentation",
    ":kaal-domain",
    ":kaal-core",
    ":addonlib:kaal-domain-addon",
    ":addonlib:kaal-apk-addon",
    // Sample
    ":sample:basic:app-apk",
    ":sample:addonapp:app:app-addon-apk",
    // ############# FEATURES ################
    ":sample:addonapp:app:feature:login:login-domain",
    ":sample:addonapp:app:feature:login:login-data"
)
