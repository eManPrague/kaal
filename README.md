# Kotlin Android Architecture Library - Kaal - by eMan

[![Latest version](https://img.shields.io/github/v/release/eManPrague/kaal)](https://github.com/eManPrague/kaal/releases/tag/v0.8.0)

[![Slack channel](https://img.shields.io/badge/Chat-Slack-blue.svg)](https://kotlinlang.slack.com/messages/kaal/)

### Usage

:warning: The artifacts were moved from jCenter.

All artifacts are available and distributed using the [eMan Nexus](https://nexus.eman.cz/service/rest/repository/browse/maven-public/) repository.
Add the repository to project `build.gradle.kts` (`build.gradle`) file.

```kotlin
allprojects {

    repositories {
        ...
        maven(url = "https://nexus.eman.cz/repository/maven-public")
    }
}
```

```groovy
allprojects {

    repositories {
        ...
        maven { url 'https://nexus.eman.cz/repository/maven-public' }
    }
}
```

You should use artifact which you need. E.g. a domain artifact you will use in your domain layer,
but you can use it also in data and infrastructure, because you need e.g. instance to the `Result` class.

#### Kaal Core

```kotlin
// Gradle Kotlin DSL
implementation("cz.eman.kaal:kaal-core:0.8.0")
```

```groovy
implementation 'cz.eman.kaal:kaal-core:0.8.0'
```

TBD

#### Kaal Domain

```kotlin
// Gradle Kotlin DSL
implementation("cz.eman.kaal:kaal-domain:0.8.0")
```

```groovy
implementation 'cz.eman.kaal:kaal-domain:0.8.0'
```

TBD

#### Kaal Presentation

```kotlin
// Gradle Kotlin DSL
implementation("cz.eman.kaal:kaal-presentation:0.8.0")
```

```groovy
implementation 'cz.eman.kaal:kaal-presentation:0.8.0'
```

TBD

#### Kaal Infrastructure

```kotlin
// Gradle Kotlin DSL
implementation("cz.eman.kaal:kaal-infrastructure:0.8.0")
```

```groovy
implementation 'cz.eman.kaal:kaal-infrastructure:0.8.0'
```

TBD

##### Responsible Persons
* [Vaclav Souhrada](mailto:vaclav.souhrada@eman.cz)
- [Roman Holomek](mailto:roman.holomek@eman.cz)
- [Stefan Toth](mailto:stefan.toth@eman.cz)
- [Filip Šmíd](mailto:filip.smid@eman.cz)
