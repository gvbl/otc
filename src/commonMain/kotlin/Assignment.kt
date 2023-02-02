import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class Assignment(val personId: Int, val deskId: Int) {

    val id = Random.nextInt(Int.MAX_VALUE)

    companion object {
        const val path = "/assignments"
    }
}