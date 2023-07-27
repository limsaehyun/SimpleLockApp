package com.example.simplelockexample.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.IBinder
import androidx.annotation.StringRes
import com.example.simplelockexample.receiver.LockReceiver
import com.example.simplelockexample.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LockService : Service() {

    @Inject
    lateinit var lockServiceManager: LockServiceManager

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val builder = createNotificationBuilder()
        startForeground(SERVICE_ID, builder)

        startLockReceiver()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        stopLockReceiver()
        lockServiceManager.stopService()
        super.onDestroy()
    }

    private fun startLockReceiver() {
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        registerReceiver(LockReceiver.getInstance(), intentFilter)
    }

    private fun stopLockReceiver() {
        unregisterReceiver(LockReceiver.getInstance())
    }

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            LOCK_CHANNEL,
            getStringWithContext(R.string.app_name),
            NotificationManager.IMPORTANCE_HIGH,
        ).apply {
            setShowBadge(false)
            enableLights(true)
            description = getStringWithContext(R.string.lock_screen_description)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            lightColor = Color.BLACK
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun getStringWithContext(
        @StringRes stringRes: Int
    ): String {
        return applicationContext.getString(stringRes)
    }

    private fun createNotificationBuilder(): Notification {
        return Notification.Builder(this, LOCK_CHANNEL).apply {
            setOngoing(true)
            setShowWhen(true)
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle(getStringWithContext(R.string.app_name))
            setContentText(getStringWithContext(R.string.lock_screen_description))
        }.build()
    }

    private companion object {
        const val LOCK_CHANNEL = "LOCK_CHANNEL"
        const val SERVICE_ID: Int = 1
    }
}