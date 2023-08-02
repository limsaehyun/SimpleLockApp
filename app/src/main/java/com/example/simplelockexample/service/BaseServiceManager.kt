package com.example.simplelockexample.service

import android.app.Service
import android.content.Context
import android.content.Intent
import com.example.simplelockexample.util.isServiceRunning

abstract class BaseServiceManager<T : Service>(
    val context: Context,
    val targetClass: Class<T>,
) {
    fun startService() = synchronized(this) {
        val intent = Intent(context, targetClass)

        if (!context.isServiceRunning<Class<T>>()) {
            context.startForegroundService(intent)
        }
    }

    fun stopService() = synchronized(this) {
        val intent = Intent(context, targetClass)

        if (context.isServiceRunning<Class<T>>()) {
            context.stopService(intent)
        }
    }
}
