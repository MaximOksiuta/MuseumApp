package com.startup.museumapp.data.bluetoothProvider

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.compose.ui.geometry.Offset
import com.startup.museumapp.data.api.Marker
import com.startup.museumapp.data.api.Point
import com.startup.museumapp.data.api.mapInformation
import com.startup.museumapp.domain.dataModels.ExhibitInfo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.util.UUID
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

@SuppressLint("MissingPermission")
class BluetoothConnector(
    private val context: Context
) {

    var deviceConnected = false

    private var onDeviceConnected: (device: BluetoothDevice?) -> Unit = {}
    private var onDeviceDisconnected: (device: BluetoothDevice?) -> Unit = {}

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var outputStream: OutputStream
    private lateinit var bluetoothSocket: BluetoothSocket
    private var dataBuffer = ""

    var userPosition = MutableStateFlow(Offset.Unspecified)
    private var lastHandledTime: Long = 0
//    private var positionHandled: Boolean = false
//    var nearObject = MutableSharedFlow<ExhibitInfo?>()
    suspend fun setListeners(
        onConnected: (device: BluetoothDevice?) -> Unit,
        onDisconnected: (device: BluetoothDevice?) -> Unit
    ) {
        onDeviceConnected = onConnected
        onDeviceDisconnected = onDisconnected
        userPosition.collectLatest {
            Log.d("Collector", it.toString())
        }
//        userPosition.collect{
//            if (positionHandled.not()){
//                positionHandled = true
//
////                nearObject.emit()
//            }
//        }
    }

    @SuppressLint("MissingPermission")
    fun connectDevice(deviceNumber: String, enableBluetooth: () -> Unit) {
        with(context) {
            Log.d("bluetooth connector", "trying connect to device with number $deviceNumber")
            // creating adapter
            bluetoothAdapter = getSystemService(BluetoothManager::class.java).adapter

            // set events handler
            val filter = IntentFilter()
            filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
            registerReceiver(
                object : BroadcastReceiver() {
                    @SuppressLint("MissingPermission")
                    override fun onReceive(context: Context, intent: Intent) {
                        val action = intent.action
                        val device: BluetoothDevice? =
                            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
                            onDeviceConnected(device)
                            deviceConnected = true
                            sendToHC05("SstartE")
                        } else if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
                            onDeviceDisconnected(device)
                            deviceConnected = false
                        }
                    }
                }, filter
            )

            // set bluetooth enable
            if (!bluetoothAdapter.isEnabled) {
                enableBluetooth()
            }


            // get device info
            val device =
                bluetoothAdapter.bondedDevices.find { device -> device.name == "VACCINE UPDATER" }
//                bluetoothAdapter.bondedDevices.find { device -> device.name.endsWith(deviceNumber) }
            if (device != null) {
                Thread {
                    val bluetoothDevice =
                        bluetoothAdapter.getRemoteDevice(device.address)
                    try {
                        val bluetoothHandler = BluetoothHandler(
                            bluetoothAdapter,
                            device.address
                        )
                        bluetoothHandler.start()
                        bluetoothSocket =
                            bluetoothHandler.createSocket(bluetoothDevice)
                        bluetoothSocket.connect()
                        outputStream = bluetoothSocket.outputStream
                        startHandler {
                            Log.d("Receiver", "handle $it")
                            if (dataBuffer == "") {
                                if (it.startsWith("S")) {
                                    dataBuffer = it
                                }
                            } else {
                                dataBuffer += it
                            }
                            if (dataBuffer.endsWith("E")) {
                                Log.d("Receiver", "received $dataBuffer")
                                userPosition.value = calculatePosition(dataBuffer)
                                lastHandledTime = System.currentTimeMillis()
                                dataBuffer = ""
                            }
                        }
                    } catch (e: Exception) {
                        bluetoothSocket.close()
                    }
                }.start()
            } else {
                onDeviceDisconnected(device)
            }

        }
    }

    @SuppressLint("MissingPermission")
    class BluetoothHandler(
        private val bluetoothAdapter: BluetoothAdapter,
        private val macAddress: String
    ) : Thread() {
        fun createSocket(device: BluetoothDevice): BluetoothSocket {
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
            return device.createRfcommSocketToServiceRecord(uuid)
        }


        override fun run() {
            var bluetoothSocket: BluetoothSocket? = null
            if (bluetoothSocket == null || !bluetoothSocket.isConnected) {
                val device = bluetoothAdapter.getRemoteDevice(macAddress)
                try {
                    bluetoothSocket = createSocket(device)
                } catch (e: IOException) {
                    Log.d("Connect Error", "Error Creating Socket!")
                }

                if (bluetoothSocket != null && bluetoothSocket.isConnected) {
                    Log.d("Connecting", "Connecting to ${device.name}")
                } else {
                    bluetoothSocket?.close()
                }
            }
        }
    }

    private fun startHandler(onReceive: (receivedDat: String) -> Unit) {
        Thread {
            if (bluetoothSocket.isConnected) {
                val inputStream = bluetoothSocket.inputStream
                val buffer = ByteArray(1024)
                var bytes: Int

                while (true) {
                    try {
                        // Read from the InputStream
                        bytes = inputStream.read(buffer)

                        // Convert the bytes to a string (assuming it's text data)
                        var receivedData = String(buffer, 0, bytes)
                        receivedData = receivedData.trim()
                        if (receivedData != "") {
                            onReceive(receivedData)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }.start()
    }

    private fun sendToHC05(valToSend: String) {
        try {
            // Check if the BluetoothSocket is connected
            if (bluetoothSocket.isConnected) {
                // Convert the string to bytes and send
                outputStream.write(valToSend.toByteArray())
                outputStream.flush()

            } else {
                // Handle the case where the BluetoothSocket is not connected
                // Maybe show an error message or try to reconnect
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun calculatePosition(receivedString: String): Offset {
        val markerList = receivedString.removePrefix("S").removeSuffix("E").split('-')
            .map { markerDistance ->
                val splitMarker = markerDistance.split(":")
                return@map Pair(
                    mapInformation.markers[splitMarker[0].toInt()],
                    splitMarker[1].toFloat()
                )
            }.take(3)
        return getUserPosition(
            markerList[0].first!!,
            markerList[0].second,
            markerList[1].first!!,
            markerList[1].second,
            markerList[2].first!!,
            markerList[2].second
        )
    }

    private fun getUserPosition(
        marker1: Marker,
        distance1: Float,
        marker2: Marker,
        distance2: Float,
        marker3: Marker,
        distance3: Float
    ): Offset {
        val pointFirst = getIntersections(
            marker1 = marker1,
            marker1Distance = distance1,
            marker2 = marker2,
            marker2Distance = distance2,
            checkMarker = marker3,
            checkMarkerDistance = distance3
        )
        val pointSecond = getIntersections(
            marker1 = marker1,
            marker1Distance = distance1,
            marker2 = marker3,
            marker2Distance = distance3,
            checkMarker = marker2,
            checkMarkerDistance = distance2
        )
        val pointThird = getIntersections(
            marker1 = marker2,
            marker1Distance = distance2,
            marker2 = marker3,
            marker2Distance = distance3,
            checkMarker = marker1,
            checkMarkerDistance = distance1
        )

        return Offset(
            x = calculateAverage(pointFirst.x, pointSecond.x, pointThird.x),
            y = calculateAverage(pointFirst.y, pointSecond.y, pointThird.y)
        )
    }

    fun calculateAverage(vararg numbers: Float): Float {
        return (numbers.sum() / numbers.size)
    }

    fun getIntersections(
        marker1: Marker,
        marker1Distance: Float,
        marker2: Marker,
        marker2Distance: Float,
        checkMarker: Marker,
        checkMarkerDistance: Float
    ): Point {

        // calculating delta between centers of circles
        val deltaX = marker1.x - marker2.x
        val deltaY = marker1.y - marker2.y

        val distanceBetweenCenters = sqrt(deltaX.pow(2) + deltaY.pow(2))

        // calculating coordinates of intersection point of altitude and circle centers line
        val distanceToAltitude =
            (marker1Distance.pow(2) - marker2Distance.pow(2) + distanceBetweenCenters.pow(2)) / (2 * distanceBetweenCenters)

        // calculate delta based on Thales' theorem
        val altitudeStartDeltaX = (distanceToAltitude / distanceBetweenCenters) * deltaX
        val altitudeStartDeltaY = (distanceToAltitude / distanceBetweenCenters) * deltaY

        val altitudeStartX = marker1.x - altitudeStartDeltaX
        val altitudeStartY = marker1.y - altitudeStartDeltaY

        // calculating intersection points coordinate
        val altitudeLength = sqrt(marker1Distance.pow(2) - distanceToAltitude.pow(2))

        val altitudeDeltaX = altitudeStartDeltaY * (altitudeLength / distanceToAltitude)
        val altitudeDeltaY = altitudeStartDeltaX * (altitudeLength / distanceToAltitude)

        val firstPointX = altitudeStartX + altitudeDeltaX
        val firstPointY = altitudeStartY - altitudeDeltaY

        val secondPointX = altitudeStartX - altitudeDeltaX
        val secondPointY = altitudeStartY + altitudeDeltaY

        // calculating distances to checking point
        val deltaOneX = firstPointX - checkMarker.x
        val deltaOneY = firstPointY - checkMarker.y

        val deltaTwoX = secondPointX - checkMarker.x
        val deltaTwoY = secondPointY - checkMarker.y

        val distanceToFirst = sqrt(deltaOneX.pow(2) + deltaOneY.pow(2))
        val distanceToSecond = sqrt(deltaTwoX.pow(2) + deltaTwoY.pow(2))

        val differenceFirst = abs(distanceToFirst - checkMarkerDistance)
        val differenceSecond = abs(distanceToSecond - checkMarkerDistance)

        if (differenceFirst < differenceSecond) {
            return Point(x = firstPointX, y = firstPointY)
        } else {
            return Point(x = secondPointX, y = secondPointY)
        }
    }
}