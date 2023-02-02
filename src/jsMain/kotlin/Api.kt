import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

val jsonClient = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun addSpace(): String {
    val response = jsonClient.post(Space.path)
    if (response.status == HttpStatusCode.OK) {
        return response.body()
    }
    throw Exception("Cannot create space")
}

suspend fun getSpace(id: String): Space {
    val response = jsonClient.get(Space.path + "/${id}")
    return when (response.status) {
        HttpStatusCode.OK -> response.body()
        HttpStatusCode.NotFound -> throw NotFoundException()
        else -> throw Exception("Something went wrong")
    }
}

suspend fun getSpaces(ids: String): List<Space> {
    val response = jsonClient.get(Space.path + "?id=$ids")
    return when (response.status) {
        HttpStatusCode.OK -> response.body()
        HttpStatusCode.NotFound -> throw NotFoundException()
        else -> throw Exception("Something went wrong")
    }
}

suspend fun patchSpace(space: Space) {
    jsonClient.patch("${Space.path}/${space.id}") {
        contentType(ContentType.Application.Json)
        setBody(space)
    }
}

suspend fun deleteSpace(space: Space) {
    jsonClient.delete("${Space.path}/${space.id}")
}

suspend fun addPerson(space: Space, person: Person) {
    jsonClient.post("${Space.path}/${space.id}${Person.path}") {
        contentType(ContentType.Application.Json)
        setBody(person)
    }
}

suspend fun deletePerson(space: Space, person: Person) {
    jsonClient.delete("${Space.path}/${space.id}${Person.path}/${person.id}")
}

suspend fun addDesk(space: Space, desk: Desk) {
    jsonClient.post("${Space.path}/${space.id}${Desk.path}") {
        contentType(ContentType.Application.Json)
        setBody(desk)
    }
}

suspend fun deleteDesk(space: Space, desk: Desk) {
    jsonClient.delete("${Space.path}/${space.id}${Desk.path}/${desk.id}")
}

suspend fun addAssignment(space: Space, assignment: Assignment) {
    jsonClient.post("${Space.path}/${space.id}${Assignment.path}") {
        contentType(ContentType.Application.Json)
        setBody(assignment)
    }
}

suspend fun deleteAssignment(space: Space, assignment: Assignment) {
    jsonClient.delete("${Space.path}/${space.id}${Assignment.path}/${assignment.id}")
}