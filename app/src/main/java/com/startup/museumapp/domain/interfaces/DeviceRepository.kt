package com.startup.museumapp.domain.interfaces

import android.bluetooth.BluetoothDevice
import androidx.compose.ui.geometry.Offset
import com.startup.museumapp.domain.dataModels.ExhibitInfo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

interface DeviceRepository {
    fun connectDevice(number: String, enableBluetooth: () -> Unit)

    fun getState(): Boolean

    suspend fun setListeners(
        onConnected: (device: BluetoothDevice?) -> Unit,
        onDisconnected: (device: BluetoothDevice?) -> Unit
    )

    fun getUserPositionFlow(): MutableStateFlow<Offset>
}