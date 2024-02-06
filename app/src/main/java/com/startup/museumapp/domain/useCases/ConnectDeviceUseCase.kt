package com.startup.museumapp.domain.useCases

import android.bluetooth.BluetoothDevice
import com.startup.museumapp.domain.interfaces.DeviceRepository

class ConnectDeviceUseCase(private val deviceRepository: DeviceRepository) {
    fun connectDevice(number: String, bluetoothConnect: () -> Unit) {
        deviceRepository.connectDevice(number, bluetoothConnect)
    }

    suspend fun setListeners(
        onConnected: (device: BluetoothDevice?) -> Unit,
        onDisconnected: (device: BluetoothDevice?) -> Unit
    ) {
        deviceRepository.setListeners(onConnected, onDisconnected)
    }
}