package com

import com.data.UserRepository // Importa el repositorio
import com.model.UserRequest // Importa el modelo
import com.model.LoginRequest // Importa el modelo (corrige el error de clase no encontrada)
import com.service.UserService // Importa el servicio
import io.github.jan.supabase.SupabaseClient
import io.ktor.server.application.*
import io.ktor.server.request.* // Necesario para call.receive() (corrige el error de 'receive')
import io.ktor.server.response.* // Necesario para call.respond() (corrige el error de 'respond')
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

        // Rutas para /users
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
