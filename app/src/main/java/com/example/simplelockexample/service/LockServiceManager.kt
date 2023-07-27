package com.example.simplelockexample.service

import android.content.Context
import android.content.Intent
import com.example.simplelockexample.util.BaseServiceManager
import com.example.simplelockexample.util.isServiceRunning
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LockServiceManager @Inject constructor(
    @ApplicationContext val context: Context
): BaseServiceManager() {

    override fun startService() = synchronized(this) {
        val intent = Intent(context, LockService::class.java)

        if (!context.isServiceRunning(LockService::class.java)) {
            context.startForegroundService(intent)
        }
    }

    override fun stopService() = synchronized(this) {
        val intent = Intent(context, LockService::class.java)

        if (context.isServiceRunning(LockService::class.java)) {
            context.stopService(intent)
        }
    }
}