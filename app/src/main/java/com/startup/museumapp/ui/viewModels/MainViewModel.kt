package com.startup.museumapp.ui.viewModels

import androidx.lifecycle.ViewModel
import com.startup.museumapp.domain.useCases.CheckLoginUseCase
import com.startup.museumapp.domain.useCases.SignInUseCase

class MainViewModel(
    private val checkLoginUseCase: CheckLoginUseCase,
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    fun getUserAuthenticated(): Boolean = checkLoginUseCase.authenticated

    fun loginUser(login: String, password: String): Boolean =
        signInUseCase.signIn(login, password)
}