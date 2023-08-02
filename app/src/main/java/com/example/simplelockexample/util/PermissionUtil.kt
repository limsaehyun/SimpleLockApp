
package com.example.simplelockexample.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.ContextThemeWrapper
import dagger.hilt.android.qualifiers.ActivityContext


private const val REQ_CODE_OVERLAY_PERMISSION: Int = 0

object PermissionUtil {
    fun onObtainingPermissionOverlayWindow(context: Activity) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse(context.packageName.addPackagePrefix())
        )
        context.startActivityForResult(intent, REQ_CODE_OVERLAY_PERMISSION)
    }


    fun alertPermissionCheck(context: Context?): Boolean {
        return !Settings.canDrawOverlays(context)
    }
}
