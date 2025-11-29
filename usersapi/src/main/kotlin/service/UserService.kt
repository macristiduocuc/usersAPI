package com.service

import com.data.UserRepository
import com.model.User
import com.model.UserRequest
import com.model.LoginRequest
import com.model.LoginResponse

class UserService(private val repository: UserRepository) { //

    suspend fun getUsers(): List<User> {
        return repository.getUsersFromSupabase() //
    }

    suspend fun createUser(user: UserRequest): User {
        return repository.createUser(user) //
    }

    suspend fun loginUser(loginRequest: LoginRequest): LoginResponse {
        // 'loginRequest' está correctamente importado, por lo que 'user' y 'password' son accesibles.
        val user = repository.loginUser(loginRequest.user, loginRequest.password) //

        return if (user != null) {
            LoginResponse(
                success = true, //
                message = "Login exitoso", //
                user = user
            )
        } else {
            LoginResponse(
                success = false, //
                message = "Usuario no existe o credenciales incorrectas", //
                user = null
            )
        }
    }
}