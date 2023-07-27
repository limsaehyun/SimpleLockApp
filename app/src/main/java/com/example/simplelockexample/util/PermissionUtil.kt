
package com.example.simplelockexample.util

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings


private const val REQ_CODE_OVERLAY_PERMISSION: Int = 0

object PermissionUtil {
    @TargetApi(Build.VERSION_CODES.M)
    fun onObtainingPermissionOverlayWindow(context: Context) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:" + context.packageName)
        )
        (context as Activity).startActivityForResult(intent, REQ_CODE_OVERLAY_PERMISSION)
    }


    fun alertPermissionCheck(context: Context?): Boolean {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.canDrawOverlays(context))
    }
}