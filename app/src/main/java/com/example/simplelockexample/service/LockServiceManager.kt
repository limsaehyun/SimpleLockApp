package com.example.simplelockexample.service

import android.content.Context
import com.example.simplelockexample.service.core.BaseForegroundServiceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LockServiceManager @Inject constructor(
    @ApplicationContext val applicationContext: Context
): BaseForegroundServiceManager<LockService>(
    context = applicationContext,
    targetClass = LockService::class.java,
)