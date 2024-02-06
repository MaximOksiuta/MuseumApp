package com.startup.museumapp.data.repositories

import android.bluetooth.BluetoothDevice
import android.util.Log
import androidx.compose.ui.geometry.Offset
import com.startup.museumapp.data.bluetoothProvider.BluetoothConnector
import com.startup.museumapp.domain.dataModels.ExhibitInfo
import com.startup.museumapp.domain.interfaces.DeviceRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

class DeviceRepositoryImpl(private val bluetoothConnector: BluetoothConnector) : DeviceRepository {
    override fun connectDevice(number: String, enableBluetooth: () -> Unit) {
        bluetoothConnector.connectDevice(number, enableBluetooth)
    }

    override fun getState(): Boolean {
        return bluetoothConnector.deviceConnected
    }

    override suspend fun setListeners(
        onConnected: (device: BluetoothDevice?) -> Unit,
        onDisconnected: (device: BluetoothDevice?) -> Unit
    ) {
        bluetoothConnector.setListeners(onConnected, onDisconnected)
    }

    override fun getUserPositionFlow(): MutableStateFlow<Offset> = bluetoothConnector.userPosition

}