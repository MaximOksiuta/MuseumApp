package com.startup.museumapp.domain.interfaces

interface LoginRepository {
    fun login(login: String, password: String): Boolean

    fun getUserAuthenticatedStatus(): Boolean
}