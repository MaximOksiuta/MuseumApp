package com.startup.museumapp.di

import com.startup.museumapp.domain.useCases.CheckLoginUseCase
import com.startup.museumapp.domain.useCases.ConnectDeviceUseCase
import com.startup.museumapp.domain.useCases.GetDeviceConnectionStateUseCase
import com.startup.museumapp.domain.useCases.GetMuseumsListUseCase
import com.startup.museumapp.domain.useCases.HandlingUserPositionUseCase
import com.startup.museumapp.domain.useCases.SignInUseCase
import org.koin.dsl.module

val domainModule = module {
    single <SignInUseCase> {
        SignInUseCase(loginRepository = get())
    }

    single <CheckLoginUseCase>{
        CheckLoginUseCase(loginRepository = get())
    }

    single <ConnectDeviceUseCase> {
        ConnectDeviceUseCase(deviceRepository = get())
    }

    single <GetMuseumsListUseCase> {
        GetMuseumsListUseCase(museumRepository = get())
    }

    single <GetDeviceConnectionStateUseCase>{
        GetDeviceConnectionStateUseCase(deviceRepository = get())
    }

    single <HandlingUserPositionUseCase>{
        HandlingUserPositionUseCase(deviceRepository = get())
    }
}