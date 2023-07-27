package com.example.simplelockexample.service

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class LockServiceManager @Inject constructor(
    @ApplicationContext val context: Context
) {
    private fun Context.isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (manager.getRunningServices(Integer.MAX_VALUE).size == 0) {
            return false
        }
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }


    fun startService() = synchronized(this) {
        val intent = Intent(context, LockService::class.java)

        if(!context.isServiceRunning(LockService::class.java)) {
            context.startForegroundService(intent)
        }
    }

    fun stopService() = synchronized(this) {
        val intent = Intent(context, LockService::class.java)

        if(context.isServiceRunning(LockService::class.java)) {
            context.stopService(intent)
        }
    }
}