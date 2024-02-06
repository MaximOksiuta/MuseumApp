package com.startup.museumapp.domain.useCases

import com.startup.museumapp.domain.interfaces.LoginRepository
import kotlin.math.log

class SignInUseCase(private val loginRepository: LoginRepository) {
    fun signIn(login: String, password: String): Boolean{
        return loginRepository.login(login, password)
    }
}