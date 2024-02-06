package com.startup.museumapp.domain.useCases

import android.util.Log
import androidx.compose.ui.geometry.isSpecified
import com.startup.museumapp.data.api.Exhibit
import com.startup.museumapp.data.api.mapInformation
import com.startup.museumapp.domain.dataModels.ExhibitInfo
import com.startup.museumapp.domain.interfaces.DeviceRepository
import com.startup.museumapp.ui.screens.ObjectCoordinates
import com.startup.museumapp.ui.screens.inCoordinates
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

class HandlingUserPositionUseCase (private val deviceRepository: DeviceRepository){
    fun getDevicePosition() = deviceRepository.getUserPositionFlow()

    suspend fun getNearExhibit(listener: (id: Int) -> Unit) {
        deviceRepository.getUserPositionFlow().collectLatest { position ->
            Log.d("UseCase", "use case collect $position")
            if (position.isSpecified){
                val exhibit = mapInformation.exhibits.find {
//                    Log.d("UseCase", "top - ${it.position.y - 10}")
//                    Log.d("UseCase", "bottom - ${it.position.y + it.size.height + 10}")
//                    Log.d("UseCase", "start - ${it.position.x - 10}")
//                    Log.d("UseCase", "end - ${it.position.x + it.size.width + 10}")
                    Log.d("UseCase", it.toString())
                    position.inCoordinates(ObjectCoordinates(
                        top = it.position.y - 10,
                        bottom = it.position.y + it.size.height + 10,
                        start = it.position.x - 10,
                        end = it.position.x + it.size.width + 10
                    ))
                }
                Log.d("UseCase", "finded $exhibit")
                if (exhibit == null){
                    listener(-1)
                } else if (exhibit.id != -1){
                    listener(exhibit.id)
                }
            }
        }
//        return nearExhibit
    }

}