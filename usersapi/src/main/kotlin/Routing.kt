package com

import com.data.UserRepository
import com.model.UserRequest
import com.model.LoginRequest
import com.service.UserService
import io.github.jan.supabase.SupabaseClient
import io.ktor.server.application.*
import io.ktor.server.request.* // Necesario para call.receive()
import io.ktor.server.response.* // Necesario para call.respond()
import io.ktor.server.routing.*

// Se modifica para recibir el cliente Supabase
fun Application.configureRouting(supabaseClient: SupabaseClient) {

    // Inicialización manual de dependencias
    val userRepository = UserRepository(supabaseClient)
    val userService = UserService(userRepository) //

    routing {
        // Ruta base
        get("/") { //
            call.respondText("Hello World!")
        }

        // Rutas para /users (basado en UserControllers.kt)
        route("/users") { //

            // GET /users
            get {
                call.respond(userService.getUsers()) //
            }

            // POST /users (Crear Usuario)
            post {
                val userRequest = call.receive<UserRequest>() // call.receive para deserializar el JSON en UserRequest
                val newUser = userService.createUser(userRequest)
                call.respond(newUser)
            }

            // POST /users/login
            post("/login") { //
                val loginRequest = call.receive<LoginRequest>() // call.receive para deserializar el JSON en LoginRequest
                val loginResponse = userService.loginUser(loginRequest)
                call.respond(loginResponse)
            }
        }
    }
}
