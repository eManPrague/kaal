import org.gradle.api.Project
import java.io.ByteArrayOutputStream

fun Project.findPropertyOrNull(s: String) = findProperty(s) as String?

fun Project.getProperty(propertyName: String) = property(propertyName)

/**
 * Gets number of commits in projects repository.
 * @return Number of commits in projects repository.
 */
fun Project.getGitCommits(): Int {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine = listOf("git", "rev-list", "HEAD", "--count")
        standardOutput = stdout
    }
    return Integer.parseInt(stdout.toString().trim())
}
