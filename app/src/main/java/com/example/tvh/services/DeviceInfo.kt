package com.example.tvh.services

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings.Secure

@SuppressLint("HardwareIds")
class DeviceInfo(private val applicationContext: Context) {
    val uid: String by lazy {
        Secure.getString(applicationContext.contentResolver, Secure.ANDROID_ID)
    }
}