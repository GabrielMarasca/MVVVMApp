package com.example.jetpackcompose.presentation.components.util

import android.util.FloatProperty
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.presentation.components.HeartAnimation
import com.example.jetpackcompose.util.DEFAULT_RECIPE_IMAGE
import com.example.jetpackcompose.util.loadPicture
import javax.annotation.PropertyKey

enum class ShimmerAnimationType {
    FADE, TRANSLATE, FADETRANSLATE
}

@Composable
fun ShimmerList(cardHeight: Dp,
                padding: Dp) {
    val shimmerAnimationType by remember { mutableStateOf(ShimmerAnimationType.FADETRANSLATE) }

    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 100f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Restart
        )
    )

    val colorAnim by transition.animateColor(
        initialValue = Color.LightGray.copy(alpha = 1.0f),
        targetValue = Color.LightGray,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = LinearOutSlowInEasing),
            RepeatMode.Restart
        )
    )

    val list = if (shimmerAnimationType != ShimmerAnimationType.TRANSLATE) {
        listOf(colorAnim, colorAnim.copy(alpha = 0.3f))
    } else {
        listOf(Color.LightGray.copy(alpha = 0.2f), Color.LightGray)
    }

    val dpValue = if (shimmerAnimationType != ShimmerAnimationType.FADE) {
        translateAnim.dp
    } else {
        2000.dp
    }

    ShimmerItems(list, dpValue.value, cardHeight, padding)
}

@Composable
fun ShimmerItems(lists: List<Color>,
                floatAnim: Float = 0f,
                cardHeight: Dp,
                padding: Dp) {
    val brush = Brush.horizontalGradient(lists, 0f, floatAnim)

    Column(modifier = Modifier.padding(padding)) {
        Surface(
            shape = MaterialTheme.shapes.small,
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(cardHeight)
                    .background(brush = brush)
            )
        }
        Spacer(modifier = Modifier
            .padding(10.dp))
        Surface(
            shape = MaterialTheme.shapes.small,
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(cardHeight)
                    .background(brush = brush)
            )
        }
    }
}
@Composable
fun ShimmerCard(cardHeight: Dp,
                padding: Dp){
    val shimmerAnimationType by remember { mutableStateOf(ShimmerAnimationType.FADETRANSLATE) }

    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 100f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Restart
        )
    )

    val colorAnim by transition.animateColor(
        initialValue = Color.LightGray.copy(alpha = 1.0f),
        targetValue = Color.LightGray,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = LinearOutSlowInEasing),
            RepeatMode.Restart
        )
    )

    val list = if (shimmerAnimationType != ShimmerAnimationType.TRANSLATE) {
        listOf(colorAnim, colorAnim.copy(alpha = 0.3f))
    } else {
        listOf(Color.LightGray.copy(alpha = 0.2f), Color.LightGray)
    }

    val dpValue = if (shimmerAnimationType != ShimmerAnimationType.FADE) {
        translateAnim.dp
    } else {
        2000.dp
    }

    ShimmerItem(list, dpValue.value, cardHeight, padding)
}
@Composable
fun ShimmerItem(lists: List<Color>,
                floatAnim: Float = 0f,
                cardHeight: Dp,
                padding: Dp) {
    val brush = Brush.horizontalGradient(lists, 0f, floatAnim)
    Column(modifier = Modifier.padding(padding)) {
        Surface(
            shape = MaterialTheme.shapes.small,
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(cardHeight)
                    .background(brush = brush)
            )
        }
        Spacer(modifier = Modifier
            .padding(10.dp))
        Surface(
            shape = MaterialTheme.shapes.small,
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(cardHeight/20)
                    .background(brush = brush)
            )
        }
        Spacer(modifier = Modifier
            .padding(10.dp))
        Surface(
            shape = MaterialTheme.shapes.small,
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(cardHeight/20)
                    .background(brush = brush)
            )
        }
        Spacer(modifier = Modifier
            .padding(10.dp))
        Surface(
            shape = MaterialTheme.shapes.small,
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(cardHeight/20)
                    .background(brush = brush)
            )
        }
        Spacer(modifier = Modifier
            .padding(10.dp))
        Surface(
            shape = MaterialTheme.shapes.small,
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(cardHeight/20)
                    .background(brush = brush)
            )
        }

        Spacer(modifier = Modifier
            .padding(10.dp))
        Surface(
            shape = MaterialTheme.shapes.small,
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(cardHeight/20)
                    .background(brush = brush)
            )
        }
    }
}