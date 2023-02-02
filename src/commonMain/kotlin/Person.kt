import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class Person(val name: String) {

    val id = Random.nextInt(Int.MAX_VALUE)

    companion object {
        const val path = "/persons"
    }
}