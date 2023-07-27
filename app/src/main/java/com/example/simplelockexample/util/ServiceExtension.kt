package com.example.simplelockexample.util

import android.app.ActivityManager
import android.content.Context

fun Context.isServiceRunning(serviceClass: Class<*>): Boolean {
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