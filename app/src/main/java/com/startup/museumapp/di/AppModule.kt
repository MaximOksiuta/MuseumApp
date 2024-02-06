package com.startup.museumapp.di

import com.startup.museumapp.app.PermissionManager
import com.startup.museumapp.ui.viewModels.MainViewModel
import com.startup.museumapp.ui.viewModels.MuseumViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    viewModel<MainViewModel> {
        MainViewModel(checkLoginUseCase = get(), signInUseCase = get())
    }

    viewModel<MuseumViewModel> {
        MuseumViewModel(
            connectDeviceUseCase = get(),
            getMuseumsListUseCase = get(),
            getDeviceConnectionStateUseCase = get(),
            handlingUserPositionUseCase = get()
        )
    }

//    single<PermissionManager> {
//        PermissionManager(context = get())
//    }
}