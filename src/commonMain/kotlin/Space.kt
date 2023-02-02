import kotlinx.serialization.Serializable

@Serializable
data class Space(
    val _id: String? = null,
    val name: String = DefaultName,
    val persons: List<Person> = emptyList(),
    val desks: List<Desk> = emptyList(),
    val assignments: List<Assignment> = emptyList(),
    val onboarding: Onboarding = Onboarding(),
) {
    val id get() = _id!!

    companion object {
        const val path = "/api/spaces"
        const val DefaultName = "Shared space"
    }
}