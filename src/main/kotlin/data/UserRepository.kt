package com.data

import com.model.User
import com.model.UserRequest
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class UserRepository(private val supabase: SupabaseClient) {

    suspend fun getUsersFromSupabase(): List<User> {
        return try {
            val response = supabase.from("Users").select() //
            response.decodeList<User>() //
        } catch (e: Exception) {
            println("Error al obtener usuarios de Supabase: ${e.message}") //
            throw e
        }
    }

    suspend fun createUser(user: UserRequest): User {
        return try {
            val response = supabase.from("Users").insert(user) { //
                select()
            }
            response.decodeSingle<User>() //
        } catch (e: Exception) {
            println("Error al crear usuario en Supabase: ${e.message}") //
            throw e
        }
    }

    suspend fun loginUser(email: String, password: String): User? {
        return try {
            val response = supabase.from("Users")
                .select {
                    filter {
                        eq("user", email) //
                        eq("password", password) //
                    }
                }

            val users = response.decodeList<User>() //
            users.firstOrNull() //
        } catch (e: Exception) {
            println("Error al hacer login en Supabase: ${e.message}") //
            null
        }
    }
}