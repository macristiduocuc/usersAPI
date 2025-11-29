package com

import io.github.jan.supabase.SupabaseClient
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.http.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    // 1. Inicializar Cliente Supabase
    val supabaseClient = configureSupabaseClient()

    // 2. Configurar Ktor Features
    configureSerialization()
    configureCors()

    // 3. Configurar Rutas, inyectando el cliente
    configureRouting(supabaseClient)
}

// Configuración para la negociación de contenido (manejo de JSON)
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

// Configuración de CORS basada en huertabejaUsersAPI/src/main/kotlin/dev/huertabeja/users/CorsConfig.kt
fun Application.configureCors() {
    install(CORS) {
        // Equivale a .allowedOrigins("*")
        anyHost()

        // Equivale a .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)

        // Equivale a .allowedHeaders("*")
        allowHeaders { true }

        // Equivale a .allowCredentials(false)
        allowCredentials = false
    }
}
