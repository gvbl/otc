data class UserSession(var spaceIds: String = "") {
    companion object {
        const val Name = "user_session"
        const val MaxAge: Long = 400L * 24 * 3600 // 400 days
    }

    fun add(spaceId: String) {
        spaceIds = if (spaceIds.isEmpty()) {
            spaceId
        } else {
            "$spaceIds,${spaceId}"
        }
    }

    fun remove(spaceId: String) {
        val ids = spaceIds.split(",").toMutableList()
        ids.remove(spaceId)
        spaceIds = ids.joinToString(",")
    }
}