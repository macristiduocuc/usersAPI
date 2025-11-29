package com

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.server.application.*

fun Application.configureSupabaseClient(): SupabaseClient {
    // Lee directamente de las variables de entorno del sistema (standard environment variables).
    // Si no existen, lanza una excepción (nunca debería ocurrir en Railway).
    val supabaseUrl = System.getenv("SUPABASE_URL")
        ?: throw IllegalStateException("SUPABASE_URL environment variable is not set.")
    val supabaseKey = System.getenv("SUPABASE_KEY")
        ?: throw IllegalStateException("SUPABASE_KEY environment variable is not set.")

    return createSupabaseClient(
        supabaseUrl = supabaseUrl,
        supabaseKey = supabaseKey
    ) {
        install(Postgrest)
        install(Auth)
    }
}