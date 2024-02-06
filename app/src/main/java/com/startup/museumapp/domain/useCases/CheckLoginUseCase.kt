package com.startup.museumapp.domain.useCases

import com.startup.museumapp.domain.interfaces.LoginRepository

class CheckLoginUseCase(loginRepository: LoginRepository) {

    val authenticated = loginRepository.getUserAuthenticatedStatus()
}