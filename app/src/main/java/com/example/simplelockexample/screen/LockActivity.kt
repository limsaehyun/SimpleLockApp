package com.example.simplelockexample.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.simplelockexample.R
import com.example.simplelockexample.common.component.SwipeButton
import com.example.simplelockexample.common.theme.SimpleLockExampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val (isComplete, updateComplete) = remember {
                mutableStateOf(false)
            }

            SimpleLockExampleTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    SwipeButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.lock_screen_swipe_to_unlock),
                        isComplete = isComplete,
                        onSwipe = {
                            updateComplete(true)
                            finish()
                        },
                    )
                }
            }
        }
    }
}