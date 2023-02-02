import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class Desk(val name: String) {

    val id = Random.nextInt(Int.MAX_VALUE)
    val imagePath = randomImagePath()

    companion object {
        const val path = "/desks"
    }

    private fun randomImagePath(): String {
        val imageFiles = arrayOf(
            "desk1.png",
            "desk2.png",
            "desk3.png",
            "desk4.png",
            "desk5.png",
            "desk6.png",
            "desk7.png",
            "desk8.png",
            "desk9.png",
            "desk10.png",
            "desk11.png",
            "desk12.png",
            "desk13.png",
            "desk14.png",
            "desk15.png",
            "desk16.png",
            "desk17.png",
            "desk18.png"
        )
        return "https://storage.googleapis.com/otc-assets/resources/images/desks/${imageFiles[Random.nextInt(imageFiles.size)]}"
    }
}