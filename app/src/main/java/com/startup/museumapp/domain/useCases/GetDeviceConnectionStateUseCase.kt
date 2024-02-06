package com.startup.museumapp.domain.useCases

import com.startup.museumapp.domain.interfaces.DeviceRepository

class GetDeviceConnectionStateUseCase (private val deviceRepository: DeviceRepository) {
    val state
        get() = deviceRepository.getState()
}