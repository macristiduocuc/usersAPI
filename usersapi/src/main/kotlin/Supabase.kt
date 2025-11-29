package com

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.server.application.*

fun Application.configureSupabaseClient(): SupabaseClient {
    // ⚠️ Leer directamente de las variables de entorno, que Railway debe inyectar.
    // Usamos getenv() para leer directamente del entorno, pero Ktor prefiere environment.config
    // Forzamos la lectura de la configuración de Ktor que ahora debe venir del entorno:
    val supabaseUrl = environment.config.property("supabase.url").getString()
    val supabaseKey = environment.config.property("supabase.key").getString()

    return createSupabaseClient(
        supabaseUrl = supabaseUrl,
        supabaseKey = supabaseKey
    ) {
        install(Postgrest)
        install(Auth)
    }
}