include(
    ":sample",
    ":kaal-infrastructure",
    ":kaal-presentation",
    ":kaal-domain",
    ":kaal-core",
    ":kaal-data",
    // ############# ADDON-LIB ################
    ":addonlib:kaal-domain-addon",
    ":addonlib:kaal-presentation-addon",
    ":addonlib:kaal-apk-addon",
    // ############# FEATURE-LIB ################
    ":featurelib:kaal-feature-app",
    // ############# SAMPLE-ADDONS ################
    ":sample:basic:app-apk",
    ":sample:addonapp:app:app-addon-apk",
    ":sample:addonapp:codebase:codebase-infrastructure",
    ":sample:addonapp:codebase:codebase-presentation",
    // ############# SAMPLE-FEATURES ################
    // Login
    ":sample:addonapp:app:feature:login:login-domain",
    ":sample:addonapp:app:feature:login:login-data",
    ":sample:addonapp:app:feature:login:login-infrastructure",
    ":sample:addonapp:app:feature:login:login-presentation",
    ":sample:addonapp:app:feature:login:login-app",
    // Splashscreen
    ":sample:addonapp:app:feature:splashscreen:splashscreen-presentation",
    ":sample:addonapp:app:feature:splashscreen:splashscreen-app"
)

rootProject.buildFileName = "build.gradle.kts"
