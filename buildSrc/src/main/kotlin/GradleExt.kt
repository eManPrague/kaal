import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

inline fun <reified T> Project.findPropertyOrNull(name: String): T? = findProperty(name) as T?

inline fun <reified T> Project.getProperty(name: String): T = property(name) as T

/**
 * Gets number of commits in projects repository.
 * @return Number of commits in projects repository.
 */
fun Project.getGitCommits(vararg paths: String): Int {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine = if (paths.isNotEmpty()) {
            listOf("git", "rev-list", "--count", "HEAD", "--", paths.joinToString(separator = " "))
        } else {
            listOf("git", "rev-list", "--count", "HEAD")
        }
        standardOutput = stdout
    }
    return try {
        Integer.parseInt(stdout.toString().trim())
    } catch (ex: NumberFormatException) {
        0
    }
}

fun Project.applyProperties(file: File) {
    file.readPropertiesFile()?.forEach { (name, value) -> extra.set(name as String, value) }
}

fun File.readPropertiesFile(): Properties? = takeIf { it.exists() && it.isFile }?.let {
    Properties().apply { it.reader().use(::load) }
}
