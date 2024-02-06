package com.startup.museumapp.ui.commonElements

import android.graphics.BlurMaskFilter
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Preview()
@Composable
fun GradientBackground(modifier: Modifier = Modifier.fillMaxSize()) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    // A surface container using the 'background' color from the theme
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
        ConstraintLayout(
            modifier = modifier
                .blur(2000.dp)
        ) {
            val (topEllipse, centerEllipse, bottomEllipse) = createRefs()
            Box(
                modifier = Modifier
                    .size(width = screenWidth, height = screenWidth)
                    .scale(scaleX = 1.8f, scaleY = (screenHeight.value * 0.7f) / screenWidth.value)
                    .offset(
                        x = 0.dp,
                        y = (-(screenWidth.value - (screenHeight.value * 0.7f)) / 2f + (screenHeight.value * 0.1f)).dp
                    )
                    .background(shape = CircleShape, color = Color(0xFFD39E84))
                    .constrainAs(bottomEllipse) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
            Box(
                modifier = Modifier
                    .size(width = screenWidth, height = screenWidth)
                    .scale(scaleX = 1.5f, scaleY = (screenHeight.value * 0.6f) / screenWidth.value)
                    .offset(
                        x = 0.dp,
                        y = (-(screenWidth.value - (screenHeight.value * 0.6f)) / 2f).dp
                    )
                    .background(shape = CircleShape, color = Color(0xFFF4E0C8))
                    .constrainAs(centerEllipse) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
            Box(
                modifier = Modifier
                    .size(width = screenWidth, height = screenWidth)
                    .scale(scaleX = 1.75f, scaleY = (screenHeight.value * 0.5f) / screenWidth.value)
                    .offset(
                        x = 0.dp,
                        y = (-(screenWidth.value - (screenHeight.value * 0.5f)) / 2f - (screenHeight.value * 0.1f)).dp
                    )
                    .background(shape = CircleShape, color = Color(0x99A5A68F))
                    .constrainAs(topEllipse) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
            )

        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x99A5A68F),
                            Color(0xFFF4E0C8),
                            Color(0xFFD39E84)
                        )
                    )
                )
        )
    }

}

fun Modifier.advanceShadow(
    color: Color = Color.Black,
    borderRadius: Dp = 16.dp,
    blurRadius: Dp = 16.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Float = 1f,
) = drawBehind {
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        val spreadPixel = spread.dp.toPx()
        val leftPixel = (0f - spreadPixel) + offsetX.toPx()
        val topPixel = (0f - spreadPixel) + offsetY.toPx()
        val rightPixel = (this.size.width)
        val bottomPixel = (this.size.height + spreadPixel)

        if (blurRadius != 0.dp) {
            /*
                The feature maskFilter used below to apply the blur effect only works
                with hardware acceleration disabled.
             */
            frameworkPaint.maskFilter =
                (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
        }

        frameworkPaint.color = color.toArgb()
        it.drawRoundRect(
            left = leftPixel,
            top = topPixel,
            right = rightPixel,
            bottom = bottomPixel,
            radiusX = borderRadius.toPx(),
            radiusY = borderRadius.toPx(),
            paint
        )
    }
}

fun Modifier.standardShadow() = shadow(elevation = 30.dp)

@Composable
fun getCardDefaultElevation() = CardDefaults.cardElevation(
    defaultElevation = 20.dp,
    pressedElevation = 10.dp
)

//    fun Modifier.customBlur(): Modifier = composed {
//        graphicsLayer(renderEffect = BlurEffect(300f, 300f))
//    }