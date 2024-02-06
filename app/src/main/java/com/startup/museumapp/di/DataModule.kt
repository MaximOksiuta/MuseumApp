package com.startup.museumapp.di

import com.startup.museumapp.data.bluetoothProvider.BluetoothConnector
import com.startup.museumapp.data.localSaver.SharedPrefs
import com.startup.museumapp.data.loginSaver.LocalAccountInfo
import com.startup.museumapp.data.repositories.DeviceRepositoryImpl
import com.startup.museumapp.data.repositories.LoginRepositoryImpl
import com.startup.museumapp.data.repositories.MuseumRepositoryImpl
import com.startup.museumapp.domain.interfaces.DeviceRepository
import com.startup.museumapp.domain.interfaces.LoginRepository
import com.startup.museumapp.domain.interfaces.MuseumRepository
import org.koin.dsl.module

val dataModule = module {
    single <LocalAccountInfo> {
        LocalAccountInfo(context = get())
    }

    single <LoginRepository> {
        LoginRepositoryImpl(localAccountInfo = get())
    }

    single <DeviceRepository> {
        DeviceRepositoryImpl(bluetoothConnector = get())
    }

    single <BluetoothConnector> {
        BluetoothConnector(context = get())
    }

    single <MuseumRepository> {
        MuseumRepositoryImpl(prefs = get())
    }

    single <SharedPrefs>{
        SharedPrefs(get())
    }
}