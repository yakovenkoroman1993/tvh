package com.example.tvh.services

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings.Secure

interface IDeviceInfoProvider {
    val uid: String
}

@SuppressLint("HardwareIds")
class DeviceInfoProvider(private val applicationContext: Context) : IDeviceInfoProvider {
    override val uid: String by lazy {
        Secure.getString(applicationContext.contentResolver, Secure.ANDROID_ID)
    }
}