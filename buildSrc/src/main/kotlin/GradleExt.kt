import org.gradle.api.Project
import java.io.ByteArrayOutputStream

fun Project.findPropertyOrNull(s: String) = findProperty(s) as String?

fun Project.getProperty(propertyName: String) = property(propertyName)

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
