package com.startup.museumapp.app

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class BluetoothRequestContract(): ActivityResultContract<Int, String?>(){
    override fun createIntent(context: Context, input: Int): Intent {
        return Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return null
    }

}