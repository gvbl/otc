package com.otc.application

import Assignment
import Desk
import Onboarding
import Person
import Space
import UserSession
import com.mongodb.ConnectionString
import com.mongodb.client.MongoDatabase
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.ratelimit.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.html.*
import org.litote.kmongo.*
import kotlin.time.Duration.Companion.seconds

val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let { ConnectionString(it) }
val client = if (connectionString != null) KMongo.createClient(connectionString) else KMongo.createClient()
val database: MongoDatabase = client.getDatabase(connectionString?.database ?: "otc")

val spaces = database.getCollection<Space>()

fun HTML.index() {
    head {
        meta {
            charset = "utf-8"
        }
        meta {
            name = "viewport"
            content = "width=device-width, initial-scale=1"
        }
        link {
            rel = "icon"
            type = "image/x-icon"
            href = "/static/favicon.ico"
        }
        link {
            rel = "stylesheet"
            href = "/static/styles.css"
        }
        link {
            rel = "stylesheet"
            href = "/static/bulma.min.css"
        }
        script {
            src = "https://kit.fontawesome.com/197ee059d9.js"
        }
    }
    body {
        classes = setOf("has-navbar-fixed-top")
        div {
            id = "root"
        }
        script {
            src = "/static/otc.js"
        }
    }
}

fun spaceIdParam(call: ApplicationCall) = call.parameters["spaceId"] ?: error("Invalid get request")

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(Netty, port) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            anyHost()
            allowMethod(HttpMethod.Get)
            allowMethod(HttpMethod.Post)
            allowMethod(HttpMethod.Patch)
            allowMethod(HttpMethod.Delete)
            allowHeader(HttpHeaders.ContentType)
        }
        install(Compression) {
            gzip()
        }
        install(RateLimit) {
            global {
                rateLimiter(300, 60.seconds)
            }
            register(RateLimitName("protected")) {
                rateLimiter(10, 60.seconds)
            }
        }
        install(Sessions) {
            cookie<UserSession>(UserSession.Name) {
                cookie.maxAgeInSeconds = UserSession.MaxAge
                cookie.extensions["SameSite"] = "lax"
                cookie.httpOnly = false
            }
        }
        routing {
            static("/static") {
                resources()
            }
            route(Space.path) {
                rateLimit(RateLimitName("protected")) {
                    post {
                        val persons = listOf(
                            Person(randomMaleName()),
                            Person(randomFemaleName()),
                            Person(randomNeutralName()),
                            Person(randomMaleName()),
                            Person(randomFemaleName()),
                            Person(randomNeutralName()),
                            Person(randomMaleName()),
                            Person(randomFemaleName())
                        )
                        val desks = listOf(
                            Desk("Window 1"),
                            Desk("Window 2"),
                            Desk("Left Wall"),
                            Desk("Shared Room 1: Desk 1"),
                            Desk("Shared Room 1: Desk 2"),
                            Desk("Mac Desk 1"),
                            Desk("Mac Desk 2"),
                            Desk("Design"),
                            Desk("Marketing"),
                        )
                        val space = Space(newId<Space>().toString(), "Shared space", persons, desks)
                        spaces.insertOne(space)
                        val userSession = call.sessions.get() ?: UserSession()
                        userSession.add(space.id)
                        call.sessions.set(userSession)
                        call.respond(HttpStatusCode.OK, space.id)
                    }
                }
                get {
                    val idsValue = call.parameters["id"]
                    if (idsValue == null) {
                        call.respond(HttpStatusCode.BadRequest)
                    } else {
                        val spaceIds = idsValue.split(",")
                        val spaces = spaces.find(Space::id `in` spaceIds).toList()
                        call.respond(spaces)
                    }
                }
                get("/{spaceId}") {
                    val spaceId = spaceIdParam(call)
                    val space = spaces.findOneById(spaceId)
                    if (space == null) {
                        call.respond(HttpStatusCode.NotFound)
                    } else {
                        call.respond(space)
                    }
                }
                patch("/{spaceId}") {
                    val spaceId = spaceIdParam(call)
                    val updatedSpace = call.receive<Space>()
                    if (isValidSpaceName(updatedSpace)) {
                        val space = spaces.findOneById(spaceId)
                        if (space == null) {
                            call.respond(HttpStatusCode.NotFound)
                        } else {
                            if (space.name != updatedSpace.name) {
                                spaces.updateOneById(
                                    spaceId,
                                    set(Space::name setTo updatedSpace.name)
                                )
                            }
                            if (space.onboarding.showHelp != updatedSpace.onboarding.showHelp) {
                                spaces.updateOneById(
                                    spaceId,
                                    set(Space::onboarding / Onboarding::showHelp setTo updatedSpace.onboarding.showHelp)
                                )
                            }
                            call.respond(HttpStatusCode.OK)
                        }
                    } else {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }
                delete("/{spaceId}") {
                    val spaceId = spaceIdParam(call)
                    spaces.deleteOneById(spaceId)
                    call.sessions.get<UserSession>()?.let {
                        it.remove(spaceId)
                        call.sessions.set(it)
                    }
                    call.respond(HttpStatusCode.OK)
                }
                route("/{spaceId}${Person.path}") {
                    post {
                        val spaceId = spaceIdParam(call)
                        val person = call.receive<Person>()
                        if (isValidPerson(person)) {
                            spaces.updateOneById(spaceId, push(Space::persons, person))
                            call.respond(HttpStatusCode.OK)
                        } else {
                            call.respond(HttpStatusCode.BadRequest)
                        }
                    }
                    delete("/{personId}") {
                        val spaceId = spaceIdParam(call)
                        val personId = call.parameters["personId"]?.toInt() ?: error("Invalid delete request")

                        client.startSession().use { clientSession ->
                            clientSession.startTransaction()
                            spaces.updateOneById(
                                clientSession,
                                spaceId,
                                pullByFilter(Space::persons, Person::id eq personId)
                            )
                            spaces.updateOneById(
                                clientSession,
                                spaceId,
                                pullByFilter(Space::assignments, Assignment::personId eq personId)
                            )
                            clientSession.commitTransaction()
                        }

                        call.respond(HttpStatusCode.OK)
                    }
                }
                route("/{spaceId}${Desk.path}") {
                    post {
                        val spaceId = spaceIdParam(call)
                        val desk = call.receive<Desk>()
                        if (isValidDesk(desk)) {
                            spaces.updateOneById(spaceId, push(Space::desks, desk))
                            call.respond(HttpStatusCode.OK)
                        } else {
                            call.respond(HttpStatusCode.BadRequest)
                        }
                    }
                    delete("/{deskId}") {
                        val spaceId = spaceIdParam(call)
                        val deskId = call.parameters["deskId"]?.toInt() ?: error("Invalid delete request")

                        client.startSession().use { clientSession ->
                            clientSession.startTransaction()
                            spaces.updateOneById(
                                clientSession,
                                spaceId,
                                pullByFilter(Space::desks, Desk::id eq deskId)
                            )
                            spaces.updateOneById(
                                clientSession,
                                spaceId,
                                pullByFilter(Space::assignments, Assignment::deskId eq deskId)
                            )
                            clientSession.commitTransaction()
                        }

                        call.respond(HttpStatusCode.OK)
                    }
                }
                route("/{spaceId}${Assignment.path}") {
                    post {
                        val spaceId = spaceIdParam(call)
                        val assignment = call.receive<Assignment>()

                        client.startSession().use { clientSession ->
                            clientSession.startTransaction()
                            spaces.updateOneById(
                                clientSession,
                                spaceId,
                                pullByFilter(
                                    Space::assignments,
                                    or(
                                        Assignment::personId eq assignment.personId,
                                        Assignment::deskId eq assignment.deskId
                                    )
                                )
                            )
                            spaces.updateOneById(clientSession, spaceId, push(Space::assignments, assignment))
                            clientSession.commitTransaction()
                        }

                        call.respond(HttpStatusCode.OK)
                    }
                    delete("/{assignmentId}") {
                        val spaceId = spaceIdParam(call)
                        val assignmentId = call.parameters["assignmentId"]?.toInt() ?: error("Invalid delete request")
                        spaces.updateOneById(
                            spaceId,
                            pullByFilter(Space::assignments, Assignment::id eq assignmentId)
                        )
                        call.respond(HttpStatusCode.OK)
                    }
                }
            }
            get("{...}") {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
        }
    }.start(wait = true)
}