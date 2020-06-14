package com.example.tvh.services

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings.Secure

interface IDeviceInfoProvider {
    val uid: String
}

@SuppressLint("HardwareIds")
class DeviceInfoProvider(private val context: Context) : IDeviceInfoProvider {
    override val uid: String by lazy {
        Secure.getString(context.contentResolver, Secure.ANDROID_ID)
    }
}
