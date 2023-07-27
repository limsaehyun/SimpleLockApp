package com.example.simplelockexample

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
private fun SwipeIndicator(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxHeight()
            .padding(2.dp)
            .clip(CircleShape)
            .aspectRatio(
                ratio = 1.0F,
                matchHeightConstraintsFirst = true,
            )
            .background(Color.White),
    ) {
        Icon(
            imageVector = Icons.Rounded.Check,
            contentDescription = null,
            tint = backgroundColor,
            modifier = Modifier.size(36.dp),
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeButton(
    modifier: Modifier = Modifier,
    text: String,
    isComplete: Boolean,
    doneImageVector: ImageVector = Icons.Rounded.Done,
    backgroundColor: Color = Color(0xFF03A9F4),
    onSwipe: () -> Unit,
) {
    val maxWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp
    }
    val maxWidthInPx = with(LocalDensity.current) {
        maxWidth.toPx()
    }
    val anchors = mapOf(
        0F to 0,
        maxWidthInPx to 1,
    )
    val swipeableState = rememberSwipeableState(0)
    val (swipeComplete, setSwipeComplete) = remember {
        mutableStateOf(false)
    }
    val alpha: Float by animateFloatAsState(
        targetValue = if (swipeComplete) {
            0F
        } else {
            1F
        },
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing,
        ),
        label = "",
    )

    LaunchedEffect(
        key1 = swipeableState.currentValue,
    ) {
        if (swipeableState.currentValue == 1) {
            setSwipeComplete(true)
            onSwipe()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .animateContentSize()
            .then(
                if (swipeComplete) {
                    Modifier.width(64.dp)
                } else {
                    Modifier.fillMaxWidth()
                }
            )
            .requiredHeight(64.dp),
    ) {
        SwipeIndicator(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .alpha(alpha)
                .offset {
                    IntOffset(swipeableState.offset.value.roundToInt(), 0)
                }
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ ->
                        FractionalThreshold(0.3F)
                    },
                    orientation = Orientation.Horizontal,
                ),
            backgroundColor = backgroundColor,
        )
        Text(
            text = text,
            color = White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alpha)
                .padding(
                    horizontal = 80.dp,
                )
                .offset {
                    IntOffset(swipeableState.offset.value.roundToInt(), 0)
                },
        )
        AnimatedVisibility(
            visible = swipeComplete && !isComplete,
        ) {
            CircularProgressIndicator(
                color = White,
                strokeWidth = 1.dp,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
            )
        }
        AnimatedVisibility(
            visible = isComplete,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Icon(
                imageVector = doneImageVector,
                contentDescription = null,
                tint = White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(44.dp),
            )
        }
    }
}
