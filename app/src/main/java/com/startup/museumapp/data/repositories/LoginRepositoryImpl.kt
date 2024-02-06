package com.startup.museumapp.data.repositories

import com.startup.museumapp.data.loginSaver.LocalAccountInfo
import com.startup.museumapp.domain.interfaces.LoginRepository

class LoginRepositoryImpl(private val localAccountInfo: LocalAccountInfo) : LoginRepository {
    override fun login(login: String, password: String): Boolean {
        localAccountInfo.loginToken = "someToken"
        return true
    }

    override fun getUserAuthenticatedStatus(): Boolean = localAccountInfo.loginToken != ""

}