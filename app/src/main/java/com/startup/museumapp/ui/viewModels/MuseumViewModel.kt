package com.startup.museumapp.ui.viewModels

import android.bluetooth.BluetoothDevice
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.startup.museumapp.app.PermissionManager
import com.startup.museumapp.data.api.Exhibit
import com.startup.museumapp.domain.dataModels.ExhibitInfo
import com.startup.museumapp.domain.dataModels.MuseumInfo
import com.startup.museumapp.domain.useCases.ConnectDeviceUseCase
import com.startup.museumapp.domain.useCases.GetDeviceConnectionStateUseCase
import com.startup.museumapp.domain.useCases.GetMuseumsListUseCase
import com.startup.museumapp.domain.useCases.HandlingUserPositionUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MuseumViewModel(
    private val connectDeviceUseCase: ConnectDeviceUseCase,
    private val getMuseumsListUseCase: GetMuseumsListUseCase,
    private val getDeviceConnectionStateUseCase: GetDeviceConnectionStateUseCase,
    private val handlingUserPositionUseCase: HandlingUserPositionUseCase
) : ViewModel() {

    val userPosition = handlingUserPositionUseCase.getDevicePosition()

    private val _likedMuseumsIds = MutableStateFlow(mutableStateListOf<Int>())
    val likedMuseumsIds
        get() = _likedMuseumsIds.asStateFlow()
    val deviceIsConnected
        get() = getDeviceConnectionStateUseCase.state
    var selectedMuseum: MuseumInfo? = null
    private var bluetoothRequester: () -> Unit = {}
    private val _museumInfo = MutableStateFlow(mutableStateListOf<MuseumInfo>())
    val museumInfo
        get() = _museumInfo.asStateFlow()

//    fun setNotifyViewer(listener: (exhibitInfo: ExhibitInfo) -> Unit){
//        viewModelScope.launch {
//            handlingUserPositionUseCase.getNearExhibit()
//            nearExhibit.collectLatest {
//                Log.d("MuseumViewModel", "nearExhibit collected $it")
//                if (it != null){
//                    Log.d("MuseumViewModel", it.toString())
//                    listener(it)
//                }
//            }
//        }
//    }
    var showExhibitFun: (exhibitInfo: ExhibitInfo?) -> Unit = {}
    fun updateMuseumsList() {
        startHandlingExhibits()
        viewModelScope.launch {
            _museumInfo.value.clear()
            _museumInfo.value.addAll(getMuseumsListUseCase.getMuseumsList())
            _likedMuseumsIds.value.clear()
            _likedMuseumsIds.value.addAll(getMuseumsListUseCase.getLikedMuseumsList())
        }
    }

    fun setBluetoothRequester(bluetoothRequester: () -> Unit) {
        this.bluetoothRequester = bluetoothRequester
    }

    fun connectDevice(number: String) {
        connectDeviceUseCase.connectDevice(number, bluetoothRequester)
    }

    private fun startHandlingExhibits() {
        Log.d("MuseumViewModel", "start")
        viewModelScope.launch {
            var lastHandled = -1
            handlingUserPositionUseCase.getNearExhibit {
                Log.d("MuseumViewModel", "handled new position $it")
                if (lastHandled != it) {
                    lastHandled = it
                    if (it == -1) {
                        showExhibitFun(null)
                        Log.d("MuseumViewModel", "nearExhibit emitting null")
                    } else {
                        showExhibitFun(selectedMuseum?.exhibits?.find { exhibitInfo -> exhibitInfo.id == it })
                        Log.d("MuseumViewModelExhibit", "nearExhibit emitting value with id $it")
//                        Log.d("MuseumViewModelExhibit", "nearExhibit emitting value with id ${}")
                    }
                }
            }
        }.start()
    }

    fun setListeners(
        onConnected: (device: BluetoothDevice?) -> Unit,
        onDisconnected: (device: BluetoothDevice?) -> Unit
    ) {
        viewModelScope.launch {
            connectDeviceUseCase.setListeners(onConnected, onDisconnected)
        }
    }
}